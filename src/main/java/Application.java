import network.ring.TokenRing;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class StartParams {
    public int numThreads;
    public int numMessages;
    public int shift;

    StartParams(int numThreads, int numMessages, int shift) {
        this.numThreads = numThreads;
        this.numMessages = numMessages;
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "StartParams{" +
                "numThreads=" + numThreads +
                ", numMessages=" + numMessages +
                ", shift=" + shift +
                '}';
    }
}

public class Application {
    public static void main(String[] args) {
        int numRuns = 10;
        int numWarming = 10;

        String filePath = "/Users/a17644602/IdeaProjects/token_ring/data/";

        ArrayList<StartParams> arrayStartParams = getStartParams();
        for (StartParams startParams : arrayStartParams) {
            System.out.println("Params: " + startParams);
            ExecutorService executorService = Executors.newFixedThreadPool(startParams.numThreads);

            for (int i = 0; i < numWarming; i++) {
                System.out.println("Warm: " + i + "/" + numWarming);
                TokenRing tokenRing = new TokenRing(startParams.numThreads, startParams.numMessages, startParams.shift);
                tokenRing.executeRing(executorService);
                System.gc();
            }

            for (int i = 0; i < numRuns; i++) {
                System.out.println("Epoch: " + i + "/" + numRuns);
                TokenRing tokenRing = new TokenRing(startParams.numThreads, startParams.numMessages, startParams.shift);
                tokenRing.executeRing(executorService);
                tokenRing.saveStatistic(filePath + "test.txt");
                System.gc();
            }
            executorService.shutdown();
        }

    }
    private static ArrayList<StartParams> getStartParams() {
        ArrayList<StartParams> startParams = new ArrayList<>();
        int start = 6;
        int n = 100;
        for (int i = start; i < n; i++) {
            startParams.add(new StartParams(i, 10_000_000, i - 1));
        }

        return startParams;
    }
}
