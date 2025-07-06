package org.example.broker;


import org.example.model.Message;
import org.example.queue.MessageQueue;
import org.example.storage.CommitLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BrokerServer {

    private Map<String, MessageQueue> topicQueues;
    private CommitLog commitLog;

    public BrokerServer(){
        this.topicQueues = new HashMap<>();
        this.commitLog = new CommitLog("commit.log");
        loadFromDisk();
    }

    public void receive(Message message){
        commitLog.append(message);
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

    private void loadFromDisk(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("commit.log"));
            reader.lines().forEach(
                    line->{

                        String[] messageArray = line.split("\\|");
                        if(messageArray.length != 4){
                            return;
                        }
                        String id = messageArray[0];
                        String topic = messageArray[1];
                        String content = messageArray[2];
                        long timestamp = Long.parseLong(messageArray[3]);
                        Message message = new Message(id, topic, content, timestamp);

                        if(topicQueues.containsKey(topic)) {
                            topicQueues.get(topic).push(message);
                        }else{
                            MessageQueue messageQueue = new MessageQueue();
                            messageQueue.push(message);
                            topicQueues.put(topic, messageQueue);

                        }
                    });
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage() + ", CLASS : " + this.getClass().getName());
        }
    }
}