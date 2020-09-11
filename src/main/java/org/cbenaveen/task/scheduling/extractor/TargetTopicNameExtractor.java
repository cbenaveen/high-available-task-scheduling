package org.cbenaveen.task.scheduling.extractor;

import org.apache.kafka.streams.processor.RecordContext;
import org.apache.kafka.streams.processor.TopicNameExtractor;
import org.cbenaveen.task.scheduling.TaskDefinition;
import org.cbenaveen.task.scheduling.TaskDefinitionKey;

public class TargetTopicNameExtractor implements TopicNameExtractor<TaskDefinitionKey, TaskDefinition> {
    @Override
    public String extract(final TaskDefinitionKey taskDefinitionKey,
                          final TaskDefinition taskDefinition,
                          final RecordContext recordContext) {
        return taskDefinition.getConfig().getOutputTopic().toString();
    }
}
