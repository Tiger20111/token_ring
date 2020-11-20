package network.ring;

import network.msg.Message;
import network.msg.MessageQueue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Node implements Runnable {
    private Node next;
    private Logger logger;
    private MessageQueue reserve;
    private long visitedTime = -1;
    private boolean work = true;
    public Node () throws IOException {
        createLogger();
        reserve = new MessageQueue();
    }

    public void run() {
        while (work) {
            executeMessage();
        }
    }

    void setNextNode(Node node) {
        next = node;
    }

    public void turnOffNode() {
        work = false;
    }

    private void executeMessage() {
        Message message = reserve.getMessage();
        if (message == null) {
            return;
        }
        if (message.getId() == 0) {
            if (visitedTime == -1) {
                visitedTime = System.nanoTime();
            } else {
                System.out.println();
                long currentTime = System.nanoTime();
                logger.info(currentTime + ": Node.id = " + Thread.currentThread().getId() + " time since last message: " + (currentTime - visitedTime));
            }
        }

        if (message.getId() == Thread.currentThread().getId()) {
            logger.info("Thread.id = " + Thread.currentThread().getId() + " has taken message with text: " + message.getMessage());
            return;
        }
        next.setMessage(message);
    }

    public void setMessage(Message message) {
        reserve.addMessage(message);
    }

    private void createLogger() throws IOException {
        FileInputStream ins = new FileInputStream("/Users/tiger/IdeaProjects/token_ring/logs/ring.log");
        LogManager.getLogManager().readConfiguration(ins);
        logger = Logger.getLogger(this.getClass().getName());
    }
}
