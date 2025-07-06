package org.example.offset;

import java.util.HashMap;
import java.util.Map;

public class ConsumerOffsetManager {

    private Map<String, Map<String, Integer>> offSet;

    public ConsumerOffsetManager(){
        this(null);
    }

    public ConsumerOffsetManager(Map<String, Map<String, Integer>> initialOffsets){
        if(initialOffsets != null){
            this.offSet = initialOffsets;
        }else{
            this.offSet = new HashMap<>();
        }
    }

    public int getOffset(String consumer, String topic){
        if(offSet.containsKey(consumer)){
            return offSet.get(consumer).getOrDefault(topic, 0);
        }
        return 0;
    }
    public Map<String, Map<String, Integer>> getOffsets() {
        return offSet;
    }

    public void commitOffset(String consumer, String topic, int newOffset){
        if(offSet.containsKey(consumer)){
            offSet.get(consumer).put(topic, newOffset);
        }else{
            Map<String, Integer> topicOffset = new HashMap<>();
            topicOffset.put(topic, newOffset);
            offSet.put(consumer, topicOffset);
        }
    }
}
