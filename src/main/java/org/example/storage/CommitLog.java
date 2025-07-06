package org.example.storage;

import org.example.model.Message;

import java.io.*;

public class CommitLog {

    private File logFile;
    private BufferedWriter writer;

    public CommitLog(String fileName){
        try{
            logFile = new File(fileName);
            writer = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage() + ", CLASS : " + this.getClass().getName());
        }
    }

    public void append(Message message){
        try{
            String logEntry = message.getId() + "|" +
                    message.getTopic() + "|" +
                    message.getContent() + "|" +
                    message.getTimestamp();
            writer.write(logEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage() + ", CLASS : " + this.getClass().getName());
        }
    }

}
