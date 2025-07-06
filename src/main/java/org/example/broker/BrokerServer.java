package org.example.broker;


import org.example.model.Message;
import org.example.queue.MessageQueue;

import java.util.HashMap;
import java.util.Map;

public class BrokerServer {

    private Map<String, MessageQueue> topicQueues;

    public BrokerServer(){
        this.topicQueues = new HashMap<>();
    }

    public void receive(Message message){
        if(topicQueues.containsKey(message.getTopic())){
            MessageQueue messageQueue = topicQueues.get(message.getTopic());
            messageQueue.push(message);
            return;
        }
        MessageQueue newMessageQueue = new MessageQueue();
        newMessageQueue.push(message);
        topicQueues.put(message.getTopic(), newMessageQueue);
    }

    public Message deliver(String topic){
       return topicQueues.containsKey(topic)
               ? topicQueues.get(topic).poll()
               : null;
    }
}