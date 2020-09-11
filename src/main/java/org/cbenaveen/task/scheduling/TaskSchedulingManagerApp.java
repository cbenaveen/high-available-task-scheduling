package org.cbenaveen.task.scheduling;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TaskSchedulingManagerApp {

    private KafkaStreams kafkaStreams;

    private TaskSchedulingManagerApp() {
        TaskSchedulingStreamTopology topology = new TaskSchedulingStreamTopology();
        kafkaStreams = new KafkaStreams(topology.buildTaskStreamingTopology(), getStreamsProperties());
    }

    private Properties getStreamsProperties() {
        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, Config.APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_SERVER);
//        properties.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "16");

        return properties;
    }

    public void startSchedulingStream() {
        kafkaStreams.start();
    }

    public void stopSchedulingStream() {
        kafkaStreams.close();
    }

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        TaskSchedulingManagerApp taskSchedulingManagerApp = new TaskSchedulingManagerApp();
        taskSchedulingManagerApp.startSchedulingStream();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            taskSchedulingManagerApp.stopSchedulingStream();
            latch.countDown();
        }));

        latch.await();
    }
}
