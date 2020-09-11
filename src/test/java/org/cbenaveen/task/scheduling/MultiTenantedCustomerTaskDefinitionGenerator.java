package org.cbenaveen.task.scheduling;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class MultiTenantedCustomerTaskDefinitionGenerator {
    private static final Random RANDOM = new Random();

    // customer id start and end range
    private static final int CUSTOMER_ID_START = 1000;
    private static final int CUSTOMER_ID_END = 10000;

    // list of mock task names
    private static final String[] PREDEFINED_MOCK_TASK_NAME = {"task-generate-random-numbers",
            "poll-google-maps-api", "poll-twitter-api", "poll-github-api", "remove-deleted-users-from-db",
            "purge-unwanted-messages", "compress-s3-images", "remove-duplicate-s3-contents",
            "upload-stats-to-datadog", "regular-api-sanity-checks"};

    public static void main(String[] args) throws InterruptedException {
        KafkaProducer<TaskDefinitionKey, TaskDefinition> kafkaProducer = new KafkaProducer(getProperties());

        for (int customerStartIndex = CUSTOMER_ID_START; customerStartIndex <= CUSTOMER_ID_END; customerStartIndex ++) {
            for (String mockTask: PREDEFINED_MOCK_TASK_NAME) {
                final TaskDefinitionKey taskDefinitionKey = getTaskDefinitionKey(customerStartIndex, mockTask);
                final TaskDefinition taskDefinition = getTaskDefinition(customerStartIndex, mockTask);

                ProducerRecord<TaskDefinitionKey, TaskDefinition> producerRecord = new ProducerRecord(Config.TASK_SCHEDULING_INPUT_TOPIC,
                        taskDefinitionKey ,taskDefinition);
                kafkaProducer.send(producerRecord);
            }
        }

        // wait for 3 seconds before all the records flushed into kafka
        Thread.sleep(3000);

        kafkaProducer.close();
    }

    private static TaskDefinition getTaskDefinition(int customerStartIndex, String mockTask) {
        final TaskFrequencyTimeUnits[] values = TaskFrequencyTimeUnits.values();

        TaskConfiguration taskConfiguration = TaskConfiguration.newBuilder()
                .setOutputTopic(mockTask)
                .build();

        // Task frequency definition triggers the task every 3 sec once
        TaskFrequency taskFrequency = TaskFrequency.newBuilder()
                .setFrequencyTimeUnit(values[RANDOM.nextInt(values.length)])
                .setTime(RANDOM.nextInt(25))
                .build();

        TaskDefinition taskDefinition = TaskDefinition.newBuilder()
                .setTaskName(mockTask)
                .setCustomerId("" + customerStartIndex)
                .setConfig(taskConfiguration)
                .setFrequency(taskFrequency)
                .build();

        return taskDefinition;
    }

    private static TaskDefinitionKey getTaskDefinitionKey(int customerStartIndex, String mockTask) {
        return TaskDefinitionKey.newBuilder().setCustomerId("" + customerStartIndex).setTaskName(mockTask).build();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
        properties.put("schema.registry.url", Config.SCHEMA_REGISTRY_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        return properties;
    }
}
