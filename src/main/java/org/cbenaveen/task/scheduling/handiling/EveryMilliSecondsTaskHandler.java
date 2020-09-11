package org.cbenaveen.task.scheduling.handiling;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.cbenaveen.task.scheduling.Config;
import org.cbenaveen.task.scheduling.TaskDefinitionKey;

import java.util.concurrent.TimeUnit;

final class EveryMilliSecondsTaskHandler extends AbstractTaskHandler {
    EveryMilliSecondsTaskHandler(ProcessorContext processorContext, TimeUnit timeUnit) {
        super(processorContext, Config.EVERY_MILLISECONDS_TASK_STORE, timeUnit);
    }

}
