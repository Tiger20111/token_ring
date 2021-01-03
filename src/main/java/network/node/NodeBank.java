package network.node;

import statistic.Statistic;

import java.util.ArrayList;

public class NodeBank {
    private final ArrayList<Node> nodes;
    public NodeBank(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void calculateNode(Statistic statistic) {
        long milliseconds = 1_000_000;
        long min = -1;
        long max = -1;
        long     numExecuted = 0;
        int averageWorkTime = 0;
        for (Node node : nodes) {
            long nodeTime = node.getWorkTime() / milliseconds;
            if (min == -1 || nodeTime < min) {
                min = nodeTime;
            }
            if (max == -1 || nodeTime > max) {
                max = nodeTime;
            }
            numExecuted += node.getNumExecuted();
            averageWorkTime += nodeTime;
        }
        statistic.setThroughputRing(numExecuted / nodes.size());
        statistic.setMinTimeWorkThread(min);
        statistic.setMaxTimeWorkThread(max);
        statistic.setTimePrecessingRing(averageWorkTime / nodes.size());
    }
}
