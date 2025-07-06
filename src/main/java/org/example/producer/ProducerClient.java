package org.example.producer;

import org.example.broker.BrokerServer;
import org.example.model.Message;

public class ProducerClient {

    private BrokerServer brokerServer;

    public ProducerClient(BrokerServer brokerServer){
        this.brokerServer = brokerServer;
    }

    public void send(String topic, String content){
        Message newMessage = new Message(topic, content);
        brokerServer.receive(newMessage);
    }
}