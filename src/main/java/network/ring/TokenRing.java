package network.ring;

import network.msg.Message;
import network.msg.MessageBank;
import network.node.Node;
import network.node.NodeBank;
import statistic.Statistic;

import java.util.ArrayList;
import java.util.concurrent.*;

public class TokenRing {
    private final Statistic statistic = new Statistic();
    private final ArrayList<Node> nodes;
    private final ArrayList<Message> messages;
    private final NodeBank nodeBank;
    private final MessageBank messageBank;

    public TokenRing(int numNodes, int numMessages, int shift) {
        nodes = new ArrayList<>();
        messages = new ArrayList<>();
        generateRing(numNodes, numMessages);
        generateMessages(numMessages, shift);
        nodeBank = new NodeBank(nodes, numMessages);
        messageBank = new MessageBank(messages);
    }

    public void executeRing() {
        long timeStart = System.nanoTime();
        ArrayList<Future<?>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(nodes.size() + 1);
        for (Node node:
                nodes) {
            futures.add(executorService.submit(node));
        }
        Future<?> f = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Node node:
                        nodes) {
                    node.turnOffNode();
                }
            }
        });
        futures.add(f);
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        statistic.setTimePrecessingRing((System.nanoTime() - timeStart));
        nodeBank.calculateNode(statistic);
        messageBank.analyseMessages(statistic);
    }

    private void generateRing(int size, int reserveSize) {
        for (int i = 0; i < size; i++) {
            Node currentNode = new Node(i, reserveSize);
            if (i != 0) {
                nodes.get(i - 1).setNextNode(currentNode);
            }
            nodes.add(currentNode);
        }
        nodes.get(nodes.size() - 1).setNextNode(nodes.get(0));
    }

    private void generateMessages(int size, int shift) {
        Message messageStart = new Message();
        messageStart.setId(0);
        messages.add(messageStart);
        nodes.get(0).addMessageReserve(messageStart);
        for (int i = 1; i < size; i++) {
            Message message = new Message();
            message.setId((shift + i) % nodes.size());
            message.setMessage("id = " + (i % nodes.size()));
            messages.add(message);
            nodes.get(i % nodes.size()).addMessageReserve(message);
        }
    }

    public void saveStatistic() {
        statistic.saveToFile();
        System.out.println(statistic);
    }

}
