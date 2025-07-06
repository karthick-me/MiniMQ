package org.example.storage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class OffsetLog {

    private final File offsetFile;
    private final String filePath = "offsets.log";

    public OffsetLog(){
        this.offsetFile = new File(filePath);
    }

    public Map<String, Map<String, Integer>> loadOffsets(){
        Map<String, Map<String, Integer>> offsets = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(offsetFile));){
            reader.lines().forEach(line -> {
                String[] offsetArray = line.split("\\|");
                if(offsetArray.length != 3){
                    System.out.println("Malformed offset entry: " + line);
                    return;
                }

                String consumerName = offsetArray[0];
                String topic = offsetArray[1];
                int offset = Integer.parseInt(offsetArray[2]);

                if(!offsets.containsKey(consumerName)){
                    offsets.put(consumerName, new HashMap<>());
                }
                offsets.get(consumerName).put(topic, offset);
            });
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
        return offsets;
    }

    public void persistAllOffsets(Map<String, Map<String, Integer>> offsets){

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(offsetFile, false))){
            offsets.forEach((consumerName, topicAndOffset)-> topicAndOffset.forEach((topic, offset) -> {
                String offsetEntry = consumerName + "|" + topic + "|" + offset;
                try {
                    writer.write(offsetEntry);
                    writer.newLine();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }));
            writer.flush();

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }

    }

}
