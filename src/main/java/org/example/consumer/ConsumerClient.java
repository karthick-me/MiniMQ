package org.example.consumer;

import org.example.broker.BrokerServer;
import org.example.model.Message;

public class ConsumerClient {

    private BrokerServer brokerServer;
    private String topic;

    public ConsumerClient(BrokerServer brokerServer, String topic){
        this.brokerServer = brokerServer;
        this.topic = topic;
    }

    public void consume(){
        Message message = brokerServer.deliver(topic);
        if(message == null){
            System.out.println("No messages to consume");
            return;
        }
        System.out.println("Consumed : " + message.getContent() + " from topic " + message.getTopic());
    }
}
