package org.cbenaveen.task.scheduling.handiling;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.cbenaveen.task.scheduling.Config;

import java.util.concurrent.TimeUnit;

final class EverySecondsTaskHandler extends AbstractTaskHandler {
    protected EverySecondsTaskHandler(ProcessorContext processorContext, TimeUnit timeUnit) {
        super(processorContext, Config.EVERY_SECONDS_TASK_STORE, timeUnit);
    }
}
