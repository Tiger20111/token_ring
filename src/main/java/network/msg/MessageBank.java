package network.msg;

import statistic.Statistic;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MessageBank {
    private final ArrayList<Message> messages;
    private int numMessages;
    public MessageBank(ArrayList<Message> messages) {
        this.messages = messages;
        numMessages = messages.size();
    }

    public void analyseMessages(Statistic statistic) {
        double time = 0;
        long milliseconds = 1000000;
        for (Message message : messages) {
            long duration = message.getReceivedTime() - message.getSendTime();
            double durationInMs = (double) duration / milliseconds;
            time += durationInMs;
        }
        statistic.setLatencyChain(time / messages.size());
    }

}
