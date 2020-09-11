package org.cbenaveen.task.scheduling.handiling;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.cbenaveen.task.scheduling.Config;

import java.util.concurrent.TimeUnit;

public class EveryDayTaskHandler extends AbstractTaskHandler {
    protected EveryDayTaskHandler(ProcessorContext processorContext, TimeUnit timeUnit) {
        super(processorContext, Config.EVERY_24HR_TASK_STORE, timeUnit);
    }
}
