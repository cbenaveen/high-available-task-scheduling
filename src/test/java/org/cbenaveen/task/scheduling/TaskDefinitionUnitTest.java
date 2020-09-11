package org.cbenaveen.task.scheduling;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TaskDefinitionUnitTest {
    @Test
    public void testTaskFrequencyCreation() {
        // Task frequency definition triggers the task every 3 min once
        TaskFrequency taskFrequency = TaskFrequency.newBuilder()
                .setFrequencyTimeUnit(TaskFrequencyTimeUnits.MINUTES)
                .setTime(3)
                .build();
    }

    @Test
    public void taskDefinitionCreation() {
        // Task frequency definition triggers the task every 3 sec once
        TaskFrequency taskFrequency = TaskFrequency.newBuilder()
                .setFrequencyTimeUnit(TaskFrequencyTimeUnits.SECONDS)
                .setTime(3)
                .build();

        TaskDefinition taskDefinition = TaskDefinition.newBuilder()
                .setTaskName("poll-api")
                .setCustomerId("10000")
                .setConfig(getTaskConfiguration())
                .setFrequency(taskFrequency)
                .build();
    }

    @Test
    public void taskDefinitionCreationWithConfigAndData() {
        // Task frequency definition triggers the task every 3 sec once
        TaskFrequency taskFrequency = TaskFrequency.newBuilder()
                .setFrequencyTimeUnit(TaskFrequencyTimeUnits.SECONDS)
                .setTime(3)
                .build();

        Map<CharSequence, CharSequence> configData = new HashMap();
        configData.put("customerId", "10000");

        Map<CharSequence, CharSequence> data = new HashMap();
        configData.put("customerId", "10000");

        TaskDefinition taskDefinition = TaskDefinition.newBuilder()
                .setTaskName("poll-api")
                .setCustomerId("10000")
                .setConfig(getTaskConfiguration())
                .setData(data)
                .setFrequency(taskFrequency)
                .build();
    }

    private TaskConfiguration getTaskConfiguration() {
        TaskConfiguration taskConfiguration = TaskConfiguration.newBuilder()
                .setOutputTopic("test")
                .build();

        return taskConfiguration;
    }

    @Test
    public void taskDefinitionCreationWithoutFrequency() {
        TaskDefinition taskDefinition = TaskDefinition.newBuilder()
                .setTaskName("poll-api")
                .setConfig(getTaskConfiguration())
                .setCustomerId("10000")
                .build();
    }
}
