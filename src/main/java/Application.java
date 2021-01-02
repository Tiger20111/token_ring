import network.ring.TokenRing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class StartParams {
    public int numThreads;
    public int numMessages;
    public int shift;

    StartParams(int numThreads, int numMessages, int shift) {
        this.numThreads = numThreads;
        this.numMessages = numMessages;
        this.shift = shift;
    }
}

public class Application {
    public static void main(String[] args) {
        int numRuns = 1;
        ArrayList<StartParams> arrayStartParams = getStartParams();
        for (int i = 0; i < numRuns; i++) {
            for (StartParams startParams : arrayStartParams) {
                TokenRing tokenRing = new TokenRing(startParams.numThreads, startParams.numMessages, startParams.shift);
                tokenRing.executeRing();
                tokenRing.saveStatistic();
            }
        }
    }
    private static ArrayList<StartParams> getStartParams() {
        ArrayList<StartParams> startParams = new ArrayList<>();
        startParams.add(new StartParams(10, 6000000, 9));
        return startParams;
    }
}
