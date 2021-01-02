package statistic;

import java.io.*;

public class Statistic {
    private long milliseconds = 1_000_000;
    private int numberThread;
    private int numberMessages;
    private int shift;
    private long timePrecessingRing;
    private double throughputRing;
    private long latencyChain;
    private double throughputChain;
    private long minTimeWorkThread;
    private long maxTimeWorkThread;
    private double percentWork;

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

    public void setThroughputRing(int throughputRing) {
        this.throughputRing = throughputRing;
    }

    public void setTimePrecessingRing(long timePrecessingRing) {
        this.timePrecessingRing = timePrecessingRing / milliseconds;
    }

    public void setThroughputChain(double throughputChain) {
        this.throughputChain = throughputChain;
    }

    public void setLatencyChain(long latencyChain) {
        this.latencyChain = latencyChain;
    }

    public void setMinTimeWorkThread(long minTimeWorkThread) {
        this.minTimeWorkThread = minTimeWorkThread / milliseconds;
    }

    public void setMaxTimeWorkThread(long maxTimeWorkThread) {
        this.maxTimeWorkThread = maxTimeWorkThread / milliseconds;
    }

    public void setPercentWork(double percentWork) {
        this.percentWork = percentWork;
    }

    public void saveToFile() {
        recalculateParams();
        String text = "\n" +
                numberThread + " " +
                numberMessages + " " +
                shift + " " +
                timePrecessingRing + " " +
                throughputRing + " " +
                throughputChain + " " +
                latencyChain + " " +
                minTimeWorkThread + " " +
                maxTimeWorkThread + " " +
                percentWork;
        File file = new File("/Users/a17644602/IdeaProjects/token_ring/data/data.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String dataNames = "numberThread numberMessages shift timePrecessingRing throughputRing throughputChain latencyChain minTimeWorkThread maxTimeWorkThread percentWork";
    }

    private void recalculateParams() {
        throughputRing = throughputRing / (double) (timePrecessingRing / 1000);
        setThroughputChain(throughputRing / numberThread);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "numberThread = " + numberThread +
                ", numberMessages = " + numberMessages +
                ", shift = " + shift +
                ", timePrecessingRing(milliseconds) = " + timePrecessingRing +
                ", throughputRing(1sec) = " + throughputRing +
                ", latencyChain(milliseconds) = " + latencyChain +
                ", throughputChain(1sec) = " + throughputChain +
                ", minTimeWorkThread(milliseconds) = " + minTimeWorkThread +
                ", maxTimeWorkThread(milliseconds) = " + maxTimeWorkThread +
                ", percentWork=" + percentWork +
                '}';
    }
}
