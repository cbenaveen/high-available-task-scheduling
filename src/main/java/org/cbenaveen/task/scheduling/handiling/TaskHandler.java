package org.cbenaveen.task.scheduling.handiling;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.cbenaveen.task.scheduling.TaskDefinition;
import org.cbenaveen.task.scheduling.TaskDefinitionKey;

import java.util.concurrent.TimeUnit;

public interface TaskHandler {
    void add(final TaskDefinitionKey taskDefinitionKey, final TaskDefinition taskDefinition);

    TaskDefinition get(final TaskDefinitionKey taskDefinitionKey);

    TaskDefinition delete(final TaskDefinitionKey taskDefinitionKey);

    TimeUnit handlingDuration();

    static TaskHandler taskHandler(final ProcessorContext processorContext,
                                   final TimeUnit timeUnit) {
        TaskHandler taskHandler = null;
        switch (timeUnit) {
            case MILLISECONDS:
                taskHandler = new EveryMilliSecondsTaskHandler(processorContext, timeUnit);
                break;

            case SECONDS:
                taskHandler = new EverySecondsTaskHandler(processorContext, timeUnit);
                break;

            case MINUTES:
                taskHandler = new EveryMinuteTaskHandler(processorContext, timeUnit);
                break;

            case HOURS:
                taskHandler = new EveryHourTaskHandler(processorContext, timeUnit);
                break;

            case DAYS:
                taskHandler = new EveryDayTaskHandler(processorContext, timeUnit);
                break;
        }

        return taskHandler;
    }
}
