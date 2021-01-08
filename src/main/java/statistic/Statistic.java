package statistic;

import java.io.*;

public class Statistic {
    private int numberThread;
    private int numberMessages;
    private int shift;
    private double timePrecessingRing;
    private double throughputRing;
    private double latencyChain;

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

    public void setTimePrecessingRing(double timePrecessingRing) {
        this.timePrecessingRing = timePrecessingRing;
    }

    public void setLatencyChain(double latencyChain) {
        this.latencyChain = latencyChain;
    }

    public void saveToFile(String filePath) {
        recalculateParams();
        String text = "\n" +
                numberThread + " " +
                numberMessages + " " +
                shift + " " +
                timePrecessingRing + " " +
                throughputRing + " " +
                latencyChain;
        File file = new File(filePath);
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
        String dataNames = "numberThread numberMessages shift timePrecessingRing throughputRing latencyChain";
    }

    private void setThroughputRing(double throughputRing) {
        this.throughputRing = throughputRing;
    }


    private void recalculateParams() {
        double sec = ((double) timePrecessingRing) / 1000;
        throughputRing = numberMessages / sec;
        setThroughputRing(throughputRing);
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
                '}';
    }
}
