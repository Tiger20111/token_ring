package network.ring;

import network.msg.Message;

import java.io.IOException;
import java.util.ArrayList;

public class TokenRing {
    private ArrayList<Node> nodes;
    private ArrayList<Message> messages;

    public TokenRing(int numNodes, int numMessages, int shift) throws IOException {
        nodes = new ArrayList<>();
        messages = new ArrayList<>();
        generateRing(numNodes);
        generateMessages(numMessages, shift);
    }

    public void executeRing() {
        for (Node node:
             nodes) {
            new Thread(node).start();
        }
    }

    private void generateRing(int size) throws IOException {
        for (int i = 0; i < size; i++) {
            Node currentNode = new Node();
            if (i != size - 1) {
                nodes.get(i - 1).setNextNode(currentNode);
            } else {
                currentNode.setNextNode(nodes.get(0));
            }
            nodes.add(currentNode);
        }
    }

    private void generateMessages(int size, int shift) {
        Message messageStart = new Message();
        messageStart.setId(0);
        messages.add(messageStart);
        nodes.get(0).setMessage(messageStart);
        for (int i = 1; i < size; i++) {
            Message message = new Message();
            message.setId(shift + i);
            message.setMessage("id = " + i);
            messages.add(message);
            nodes.get(0).setMessage(message);
        }
    }

}
