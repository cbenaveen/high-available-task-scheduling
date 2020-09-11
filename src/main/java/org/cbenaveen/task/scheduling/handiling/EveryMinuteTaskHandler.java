package org.cbenaveen.task.scheduling.handiling;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.cbenaveen.task.scheduling.Config;

import java.util.concurrent.TimeUnit;

final class EveryMinuteTaskHandler extends AbstractTaskHandler {
    protected EveryMinuteTaskHandler(ProcessorContext processorContext, TimeUnit timeUnit) {
        super(processorContext, Config.EVERY_MIN_TASK_STORE, timeUnit);
    }
}
