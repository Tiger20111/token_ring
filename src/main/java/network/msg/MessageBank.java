package network.msg;

import statistic.Statistic;

import java.util.ArrayList;

public class MessageBank {
    private final ArrayList<Message> messages;
    private int numMessages;
    public MessageBank(ArrayList<Message> messages) {
        this.messages = messages;
        numMessages = messages.size();
    }

    public void analyseMessages(Statistic statistic) {
        long sec = 1_000_000;
        long time = 0;
        long numReceivedMessage = 0;
        for (Message message : messages) {
            if (message.getReceivedTime() != 0) {
                time += (message.getReceivedTime() - message.getSendTime()) / sec;
                numReceivedMessage++;
            }
        }
        statistic.setPercentWork((double) numReceivedMessage / messages.size());
        statistic.setLatencyChain(time / numReceivedMessage);
        System.out.println("Total received/all: " + numReceivedMessage + "/" + messages.size());
    }

}
