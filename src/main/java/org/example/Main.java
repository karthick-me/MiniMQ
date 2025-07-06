package org.example;

import org.example.broker.BrokerServer;
import org.example.consumer.ConsumerClient;
import org.example.offset.ConsumerOffsetManager;
import org.example.producer.ProducerClient;
import org.example.storage.OffsetLog;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

        BrokerServer broker = new BrokerServer();
        OffsetLog offsetLog = new OffsetLog();
        Map<String, Map<String, Integer>> loadedOffsets = offsetLog.loadOffsets();

      ConsumerOffsetManager offsetManager = new ConsumerOffsetManager(loadedOffsets);

        ProducerClient producer = new ProducerClient(broker);

        ConsumerClient consumer1 = new ConsumerClient(broker, "user-signup", "consumer-1", offsetManager);
        ConsumerClient consumer2 = new ConsumerClient(broker, "order", "consumer-2", offsetManager);




        // Consuming with offsets
        consumer1.consume(); // Should consume "User A"
        consumer2.consume(); // Should consume "Order #1001"
        consumer1.consume(); // Should consume "User B"
        consumer1.consume();


        consumer1.consume();
        consumer1.consume();
        consumer1.consume();
        consumer2.consume();

    }
}