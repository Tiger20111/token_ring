package network.node;

import statistic.Statistic;

import java.util.ArrayList;

public class NodeBank {
    private final ArrayList<Node> nodes;
    public NodeBank(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void calculateNode(Statistic statistic) {
        long min = -1;
        long max = -1;
        int numExecuted = 0;
        for (Node node : nodes) {
            if (min == -1 || node.getWorkTime() < min) {
                min = node.getWorkTime();
            }
            if (max == -1 || node.getWorkTime() > max) {
                max = node.getWorkTime();
            }
            numExecuted += node.getNumExecuted();
        }
        statistic.setThroughputRing(numExecuted / nodes.size());
        statistic.setMinTimeWorkThread(min);
        statistic.setMaxTimeWorkThread(max);
    }
}
