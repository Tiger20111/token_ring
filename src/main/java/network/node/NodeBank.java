package network.node;

import statistic.Statistic;

import java.util.ArrayList;

public class NodeBank {
    private int numMessages;
    private final ArrayList<Node> nodes;
    public NodeBank(ArrayList<Node> nodes, int numMessages) {
        this.nodes = nodes;
        this.numMessages = numMessages;
    }

    public void calculateNode(Statistic statistic) {
        int num = 0;
        for (Node node : nodes) {
            num += node.getNumExecuted();
        }
        System.out.println("Average throughput thread: " + num / nodes.size());
    }
}
