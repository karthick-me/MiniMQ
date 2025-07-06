package org.example.model;

import java.util.UUID;

public class Message {

    private final String id;
    private final String topic;
    private final String content;
    private final long timestamp;

    public Message(String topic, String content){
        this.topic = topic;
        this.id = UUID.randomUUID().toString();

        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
    public Message(String id, String topic, String content, long timestamp){
        this.id = id;
        this.topic = topic;
        this.content = content;
        this.timestamp = timestamp;
    }
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
