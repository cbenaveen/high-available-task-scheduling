package org.cbenaveen.task.scheduling;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.*;
import org.cbenaveen.task.scheduling.extractor.TargetTopicNameExtractor;
import org.cbenaveen.task.scheduling.transformers.TaskSchedulingTransformerSupplier;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class TaskSchedulingStreamTopology {

    public Topology buildTaskStreamingTopology() {
        // create a Stream Builder instance
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        // add necessary stores to the stream builder
        createVariousTaskDefinitionStateStore(streamsBuilder);

        // Created Serde to consume the from topics
        final Consumed<TaskDefinitionKey, TaskDefinition> consumed = Consumed.with(getTaskDefinitionKeySpecificAvroSerde(),
                getTaskDefinitionSpecificAvroSerde());

        // Create the stream to consume the tasks/tasks definitions from the tasks topics
        final KStream<TaskDefinitionKey, TaskDefinition> keyTaskDefinitionKStream = streamsBuilder
                .stream(Config.TASK_SCHEDULING_INPUT_TOPIC, consumed);

        // add processors using DSL and PAPI
        keyTaskDefinitionKStream
                .peek((taskDefinitionKey, taskDefinition) -> log.info("Received Task Key {}, Definition {}", taskDefinitionKey, taskDefinition))
                .transform(new TaskSchedulingTransformerSupplier(), Config.TASK_STORES)
                .to(new TargetTopicNameExtractor(), Produced.with(getTaskDefinitionKeySpecificAvroSerde(), getTaskDefinitionSpecificAvroSerde()));
        ;

        // build and return the Topology
        return streamsBuilder.build();
    }

    private static SpecificAvroSerde<TaskDefinitionKey> getTaskDefinitionKeySpecificAvroSerde() {
        final SpecificAvroSerde<TaskDefinitionKey> changeEventSpecificAvroSerde = new SpecificAvroSerde<>();
        final Map<String, String> serdeConfig =
                Collections.singletonMap(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                        Config.SCHEMA_REGISTRY_SERVER);
        changeEventSpecificAvroSerde.configure(serdeConfig, true);
        return changeEventSpecificAvroSerde;
    }

    private static SpecificAvroSerde<TaskDefinition> getTaskDefinitionSpecificAvroSerde() {
        final SpecificAvroSerde<TaskDefinition> changeEventSpecificAvroSerde = new SpecificAvroSerde<>();
        final Map<String, String> serdeConfig =
                Collections.singletonMap(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                        Config.SCHEMA_REGISTRY_SERVER);
        changeEventSpecificAvroSerde.configure(serdeConfig, false);
        return changeEventSpecificAvroSerde;
    }

    /**
     * Helper method creates various Task Stores and add them to the Stream Builder object.
     *
     * @param streamsBuilder
     */
    private void createVariousTaskDefinitionStateStore(final StreamsBuilder streamsBuilder) {
        for (String storeName : Config.TASK_STORES) {
            // Create persistence store
            final KeyValueBytesStoreSupplier keyValueBytesStoreSupplier = Stores.persistentKeyValueStore(storeName);

            // create the stiore builder
            final StoreBuilder<KeyValueStore<TaskDefinitionKey, TaskDefinition>> storeBuilder = Stores.keyValueStoreBuilder(
                    keyValueBytesStoreSupplier, getTaskDefinitionKeySpecificAvroSerde(), getTaskDefinitionSpecificAvroSerde());

            // add the store to the stream builder
            streamsBuilder.addStateStore(storeBuilder);

            log.info("Created KeyValue Store with name {}", storeName);
        }
    }
}
