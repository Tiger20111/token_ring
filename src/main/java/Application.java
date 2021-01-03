import network.ring.TokenRing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StartParams {
    public int numThreads;
    public int numMessages;
    public int shift;
    List<Integer> timeWork;

    StartParams(int numThreads, int numMessages, int shift, List<Integer> timeWork) {
        this.numThreads = numThreads;
        this.numMessages = numMessages;
        this.shift = shift;
        this.timeWork = timeWork;
    }
}

public class Application {
    public static void main(String[] args) {
        int numRuns = 100;
        ArrayList<StartParams> arrayStartParams = getStartParams();
        for (int i = 1; i < numRuns; i++) {
            System.out.println("Epoch: " + i + "/" + numRuns);
            for (StartParams startParams : arrayStartParams) {
                for (int timeWork : startParams.timeWork) {
                    TokenRing tokenRing = new TokenRing(startParams.numThreads, startParams.numMessages, startParams.shift);
                    tokenRing.executeRing(timeWork);
                    tokenRing.saveStatistic();
                }
            }
        }
    }
    private static ArrayList<StartParams> getStartParams() {
        ArrayList<StartParams> startParams = new ArrayList<>();
        startParams.add(new StartParams(9, 10_000_000, 4, Arrays.asList(1000, 2000, 4000, 6000)));
        startParams.add(new StartParams(9, 10_000_000, 8, Arrays.asList(1000, 2000, 4000, 6000)));
        startParams.add(new StartParams(12, 10_000_000, 4, Arrays.asList(1000, 2000, 4000, 6000)));
        startParams.add(new StartParams(12, 10_000_000, 8, Arrays.asList(1000, 2000, 4000, 6000)));
        return startParams;
    }
}
