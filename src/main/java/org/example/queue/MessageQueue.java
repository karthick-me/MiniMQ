package org.example.queue;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Message;

public class MessageQueue {

    private final List<Message> messages;

    public MessageQueue(){
        this.messages = new ArrayList<>();
    }

    public Message get(int offset){
        if(offset < 0 || offset >= messages.size()){
            System.out.println("offset is invalid");
            return null;
        }
        return messages.get(offset);
    }
    public void push(Message message){
        messages.add(message);
    }

    public Message poll(){
        if (messages.isEmpty()) return null;
        return messages.remove(0);

    }

    public boolean isEmpty(){
        return messages.isEmpty();
    }


}