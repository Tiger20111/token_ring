package network.node;

import statistic.Statistic;

import java.util.ArrayList;

public class NodeBank {
    private final ArrayList<Node> nodes;
    public NodeBank(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void calculateNode(Statistic statistic) {
        double averageWorkTime = 0;
        long milliseconds = 1000000;
        for (Node node : nodes) {
            double durationInMs = (double) node.getWorkTime() / milliseconds;
            averageWorkTime += durationInMs;
        }
        statistic.setTimePrecessingRing(averageWorkTime / nodes.size());
    }
}
