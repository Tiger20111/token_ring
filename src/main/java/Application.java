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
        for (int i = 0; i < numRuns; i++) {
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
        startParams.add(new StartParams(6, 10_000_000, 5, Arrays.asList(10000)));
        return startParams;
    }
}
