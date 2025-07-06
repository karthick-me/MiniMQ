package org.example.queue;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.example.model.Message;

public class MessageQueue {

    private final ConcurrentLinkedQueue<Message> queue;

    public MessageQueue(){
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public void push(Message message){
        queue.add(message);
    }

    public Message poll(){
        return queue.poll();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }


}