package org.cbenaveen.task.scheduling.handiling;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.cbenaveen.task.scheduling.TaskDefinition;
import org.cbenaveen.task.scheduling.TaskDefinitionKey;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractTaskHandler implements TaskHandler, Punctuator {
    private final ProcessorContext processorContext;
    private KeyValueStore<TaskDefinitionKey, TaskDefinition> taskDefinitionStore;
    private final TimeUnit timeUnit;

    @SuppressWarnings("unchecked")
    protected AbstractTaskHandler(final ProcessorContext processorContext,
                                  final String storeName, final TimeUnit timeUnit) {
        this.processorContext = processorContext;
        log.info("Looking for the store name {}", storeName);
        this.taskDefinitionStore = (KeyValueStore<TaskDefinitionKey, TaskDefinition>) processorContext
                .getStateStore(storeName);

        this.timeUnit = timeUnit;

        this.processorContext.schedule(getDuration(timeUnit), PunctuationType.WALL_CLOCK_TIME, this);
    }

    protected static Duration getDuration(TimeUnit timeUnit) {
        Duration duration = null;
        switch (timeUnit) {
            case MILLISECONDS:
                duration = Duration.ofMillis(1);
                break;

            case SECONDS:
                duration = Duration.ofSeconds(1);
                break;

            case MINUTES:
                duration = Duration.ofMinutes(1);
                break;

            case HOURS:
                duration = Duration.ofHours(1);
                break;

            case DAYS:
                duration = Duration.ofDays(1);
                break;
        }

        return duration;
    }

    @Override
    public TimeUnit handlingDuration() {
        return timeUnit;
    }

    @Override
    public void add(TaskDefinitionKey taskDefinitionKey, TaskDefinition taskDefinition) {
        taskDefinitionStore.put(taskDefinitionKey, taskDefinition);
    }

    @Override
    public TaskDefinition get(TaskDefinitionKey taskDefinitionKey) {
        return taskDefinitionStore.get(taskDefinitionKey);
    }

    @Override
    public TaskDefinition delete(TaskDefinitionKey taskDefinitionKey) {
        return taskDefinitionStore.delete(taskDefinitionKey);
    }

    @Override
    public void punctuate(long l) {
        final KeyValueIterator<TaskDefinitionKey, TaskDefinition> all = taskDefinitionStore.all();
        while (all.hasNext()) {
            final KeyValue<TaskDefinitionKey, TaskDefinition> next = all.next();
            log.info("{} - Triggering task Key: {}, Value: {}", timeUnit, next.key, next.value);
            this.processorContext.forward(next.key, next.value);
        }

        all.close();
    }
}

