package org.cbenaveen.task.scheduling;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class SingleTaskDefinitionProducer {

    public static final String POLL_API = "poll-api";
    public static final String VALUE = "10600";

    public static void main(String[] args) throws InterruptedException {
        TaskDefinitionKey taskDefinitionKey = TaskDefinitionKey.newBuilder()
                .setTaskName(POLL_API)
                .setCustomerId(VALUE)
                .build();
        log.info("Created task definition key {}", taskDefinitionKey);

        TaskConfiguration taskConfiguration = TaskConfiguration.newBuilder()
                .setOutputTopic("test")
                .build();
        log.info("Created task configuration key {}", taskConfiguration);

        // Task frequency definition triggers the task every 3 sec once
        TaskFrequency taskFrequency = TaskFrequency.newBuilder()
                .setFrequencyTimeUnit(TaskFrequencyTimeUnits.MINUTES)
                .setTime(3)
                .build();
        log.info("Created task frequency key {}", taskFrequency);

        TaskDefinition taskDefinition = TaskDefinition.newBuilder()
                .setTaskName(POLL_API)
                .setCustomerId(VALUE)
                .setConfig(taskConfiguration)
                .setFrequency(taskFrequency)
                .build();
        log.info("Created task definition key {}", taskDefinition);

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
        properties.put("schema.registry.url", Config.SCHEMA_REGISTRY_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        KafkaProducer<TaskDefinitionKey, TaskDefinition> kafkaProducer = new KafkaProducer(properties);
        ProducerRecord<TaskDefinitionKey, TaskDefinition> producerRecord = new ProducerRecord(Config.TASK_SCHEDULING_INPUT_TOPIC,
                taskDefinitionKey ,taskDefinition);

        final CountDownLatch latch = new CountDownLatch(1);
        kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
            if (Objects.nonNull(e)) {
                log.error("Exception occurred while pushing the task definition", e);
            } else {
                log.info("Task Definition record pushed successfully. Received Record Metadata is {}",
                        recordMetadata);
            }

            latch.countDown();
        });

        latch.await();

        kafkaProducer.close();
    }
}
