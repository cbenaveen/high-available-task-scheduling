package org.cbenaveen.task.scheduling.transformers;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;
import org.cbenaveen.task.scheduling.TaskDefinition;
import org.cbenaveen.task.scheduling.TaskDefinitionKey;

public class TaskSchedulingTransformerSupplier implements TransformerSupplier<TaskDefinitionKey, TaskDefinition,
        KeyValue<TaskDefinitionKey, TaskDefinition>> {
    @Override
    public Transformer<TaskDefinitionKey, TaskDefinition, KeyValue<TaskDefinitionKey, TaskDefinition>> get() {
        return new TaskSchedulingTransformer();
    }
}
