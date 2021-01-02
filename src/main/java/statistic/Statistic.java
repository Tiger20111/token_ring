package statistic;

import java.io.*;

public class Statistic {
    private long sec = 1_000_000_000;
    private int numberThread;
    private int numberMessages;
    private int shift;
    private long timePrecessingRing;
    private int throughputThread;
    private long latencyChain;
    private int throughputChain;

    public Statistic() {
    }

    public void setNumberMessages(int numberMessages) {
        this.numberMessages = numberMessages;
    }

    public void setNumberThread(int numberThread) {
        this.numberThread = numberThread;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public void setThroughputThread(int throughputThread) {
        this.throughputThread = throughputThread;
    }

    public void setTimePrecessingRing(long timePrecessingRing) {
        this.timePrecessingRing = timePrecessingRing / sec;
    }

    public void setThroughputChain(int throughputChain) {
        this.throughputChain = throughputChain;
    }

    public void setLatencyChain(long latencyChain) {
        this.latencyChain = latencyChain;
    }

    public void saveToFile() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("/Users/a17644602/IdeaProjects/token_ring/data/data.txt"), "utf-8"))) {
            writer.write(numberThread + " " +
                    numberMessages + " " +
                    shift + " " +
                    timePrecessingRing + " " +
                    throughputThread + " " +
                    throughputChain + " " +
                    latencyChain

            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "numberThread=" + numberThread +
                ", numberMessages=" + numberMessages +
                ", shift=" + shift +
                ", timePrecessingRing=" + timePrecessingRing +
                ", throughputThread=" + throughputThread +
                '}';
    }
}
