package org.cbenaveen.task.scheduling.handiling;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.cbenaveen.task.scheduling.TaskDefinition;
import org.cbenaveen.task.scheduling.TaskDefinitionKey;
import org.cbenaveen.task.scheduling.TaskFrequency;
import org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TaskManager {
    private final ProcessorContext processorContext;

    private final ConcurrentHashMap<TimeUnit, TaskHandler> taskHandlers;

    public TaskManager(final ProcessorContext processorContext) {
        this.processorContext = processorContext;

        this.taskHandlers = new ConcurrentHashMap();

        this.taskHandlers.put(TimeUnit.MILLISECONDS, TaskHandler.taskHandler(processorContext, TimeUnit.MILLISECONDS));
        this.taskHandlers.put(TimeUnit.SECONDS, TaskHandler.taskHandler(processorContext, TimeUnit.SECONDS));
        this.taskHandlers.put(TimeUnit.MINUTES, TaskHandler.taskHandler(processorContext, TimeUnit.MINUTES));
        this.taskHandlers.put(TimeUnit.HOURS, TaskHandler.taskHandler(processorContext, TimeUnit.HOURS));
        this.taskHandlers.put(TimeUnit.DAYS, TaskHandler.taskHandler(processorContext, TimeUnit.DAYS));
    }

    public KeyValue<TaskDefinitionKey, TaskDefinition> handle(final TaskDefinitionKey taskDefinitionKey,
                           final TaskDefinition taskDefinition) {
        final TaskFrequencyTimeUnits frequencyTimeUnit = taskDefinition.getFrequency().getFrequencyTimeUnit();

        /**
         * Check all handler to see if the key is present.
         * If its present compare the previous frequency with current one.
         * if both are same, then update it.
         *
         * If the new and old frequency are not same, remove from the old store and add the same to
         * new and correct store.
         */
        for (TaskHandler taskHandler: taskHandlers.values()) {
            TaskDefinition prvTaskDefinition = taskHandler.get(taskDefinitionKey);

            if (Objects.nonNull(prvTaskDefinition)) {
                if (taskDefinition.getFrequency().getFrequencyTimeUnit() == prvTaskDefinition.getFrequency().getFrequencyTimeUnit()) {
                    /**
                     * There is no change in the frequency but some other params might have been changed.
                     * Hence we need to update the same in the same store
                     */
                    taskHandler.add(taskDefinitionKey, taskDefinition);
                    return KeyValue.pair(taskDefinitionKey, taskDefinition);
                } else {
                    /**
                     * Looks like the frequency changed along with some data.
                     * Need to remove the same from the current store and allow the same to be updated
                     */
                    taskHandler.delete(taskDefinitionKey);
                    break;
                }
            }
        }

        switch (frequencyTimeUnit) {
            case DAYS:
            case WEEKS:
                this.taskHandlers.get(TimeUnit.DAYS).add(taskDefinitionKey, taskDefinition);
                break;

            case MILLISECONDS:
                this.taskHandlers.get(TimeUnit.MILLISECONDS).add(taskDefinitionKey, taskDefinition);
                break;

            case SECONDS:
                this.taskHandlers.get(TimeUnit.SECONDS).add(taskDefinitionKey, taskDefinition);
                break;

            case MINUTES:
                this.taskHandlers.get(TimeUnit.MINUTES).add(taskDefinitionKey, taskDefinition);
                break;
        }

        return KeyValue.pair(taskDefinitionKey, taskDefinition);
    }
}
