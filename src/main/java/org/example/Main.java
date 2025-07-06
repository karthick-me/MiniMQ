package org.example;

import org.example.broker.BrokerServer;
import org.example.consumer.ConsumerClient;
import org.example.producer.ProducerClient;

public class Main {
    public static void main(String[] args) {

        BrokerServer broker = new BrokerServer();

        ProducerClient producer = new ProducerClient(broker);

        ConsumerClient orderConsumer = new ConsumerClient(broker, "order");
        ConsumerClient userConsumer = new ConsumerClient(broker, "user-signup");

        producer.send("user-signup", "User A registered");
        producer.send("order", "Order #1234 placed");
        producer.send("user-signup", "User B registered");

        userConsumer.consume();
        orderConsumer.consume();
        userConsumer.consume();
        userConsumer.consume();
    }
}