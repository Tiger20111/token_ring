package network.ring;

import network.msg.Message;
import network.msg.MessageBank;
import network.node.Node;
import network.node.NodeBank;
import statistic.Statistic;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TokenRing {
    private final Statistic statistic = new Statistic();
    private final ArrayList<Node> nodes;
    private final ArrayList<Message> messages;
    private final NodeBank nodeBank;
    private final MessageBank messageBank;

    public TokenRing(int numNodes, int numMessages, int shift) {
        if (numMessages % numNodes != 0) {
            numMessages += (numNodes - (numMessages % numNodes));
        }
        nodes = new ArrayList<>();
        messages = new ArrayList<>();
        generateRing(numNodes, numMessages, shift);
        generateMessages(numMessages, shift);
        nodeBank = new NodeBank(nodes);
        messageBank = new MessageBank(messages);
        setStartParamsStatistic(numNodes, numMessages, shift);
    }

    public void executeRing(ExecutorService executorService) {
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (Node node:
                nodes) {
            futures.add(executorService.submit(node));
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        nodeBank.calculateNode(statistic);
        messageBank.analyseMessages(statistic);
    }

    private void generateRing(int size, int reserveSize, int shift) {
        for (int i = 0; i < size; i++) {
            Node currentNode = new Node(i, reserveSize / size, shift);
            if (i != 0) {
                nodes.get(i - 1).setNextNode(currentNode);
            }
            nodes.add(currentNode);
        }
        nodes.get(nodes.size() - 1).setNextNode(nodes.get(0));
    }

    private void generateMessages(int size, int shift) {
        for (int i = 0; i < size; i++) {
            Message message = new Message();
            message.setId((shift + i) % nodes.size());
            message.setMessage("id = " + (i % nodes.size()));
            messages.add(message);
            nodes.get(i % nodes.size()).addMessageReserve(message);
        }
    }

    private void setStartParamsStatistic(int numNodes, int numMessages, int shift) {
        statistic.setShift(shift);
        statistic.setNumberThread(numNodes);
        statistic.setNumberMessages(numMessages);
    }

    public void saveStatistic(String filePath) {
        statistic.saveToFile(filePath);
        System.out.println(statistic);
    }

}
