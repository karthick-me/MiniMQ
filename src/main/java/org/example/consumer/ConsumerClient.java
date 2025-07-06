package org.example.consumer;

import org.example.broker.BrokerServer;
import org.example.model.Message;
import org.example.offset.ConsumerOffsetManager;
import org.example.storage.OffsetLog;

public class ConsumerClient {

    private BrokerServer broker;
    private String topic;
    private String consumerName;
    private ConsumerOffsetManager offsetManager;
    private OffsetLog offsetLog;

    public ConsumerClient(BrokerServer broker,
                          String topic,
                          String consumerName,
                          ConsumerOffsetManager offsetManager,
                          OffsetLog offsetLog){
        this.broker = broker;
        this.topic = topic;
        this.consumerName = consumerName;
        this.offsetManager = offsetManager;
        this.offsetLog = offsetLog;
    }

    public void consume(){
        int currentOffset = offsetManager.getOffset(consumerName, topic);
        Message message = broker.getMessageAtOffset(topic, currentOffset);
        if(message == null){
            System.out.println("No messages to consume");
            return;
        }
        offsetManager.commitOffset(consumerName, topic, currentOffset + 1);
        offsetLog.persistAllOffsets(offsetManager.getOffsets());
        System.out.println("Consumed : " + message.getContent() + " from topic " + message.getTopic());

    }

}
