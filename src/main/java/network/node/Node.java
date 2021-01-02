package network.node;

import com.sun.jmx.remote.internal.ArrayQueue;
import network.msg.Message;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Node implements Runnable {
    private Node next;
    private Logger logger;
    private final ArrayQueue<Message> reserve;
    private final ConcurrentLinkedQueue<Message> queue;
    private boolean work = true;
    private int numExecuted;
    private long workTime;
    int id;
    private final Lock lock = new ReentrantLock();
    public Node (int id, int reserveSize) {
        this.id = id;
        createLogger();
        reserve = new ArrayQueue<>(reserveSize);
        queue = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        long statTime = System.nanoTime();
        logger.info("Thread.id = " + Thread.currentThread().getId() + " : Logger has started in " + statTime);
        do {
            lock.lock();
            if (work) {
                executeMessage();
                lock.unlock();
            } else {
                lock.unlock();
                break;
            }
        } while (true);
        long stoppedTime = System.nanoTime();
        workTime = stoppedTime - statTime;
        logger.info("Thread.id = " + Thread.currentThread().getId() + " : Logger has ended in " + stoppedTime);
    }

    public void setNextNode(Node node) {
        next = node;
    }

    public void turnOffNode() {
        lock.lock();
        work = false;
        lock.unlock();
    }

    private void executeMessage() {
        Message message = queue.poll();
        if (message == null) {
            sendMessageFromReserve();
            return;
        }

        numExecuted++;

        if (message.getId() == this.id) {
            message.setReceivedTime(System.nanoTime());
        } else {
            if (!message.getStartDeliver()) {
                message.setSendTime(System.nanoTime());
            }
            next.addMessageTransfer(message);
        }
    }

    public void sendMessageFromReserve() {
        if (reserve.isEmpty()) {
            return;
        }
        Message message = reserve.remove(0);
        next.addMessageTransfer(message);
    }

    public void addMessageTransfer(Message message) {
        queue.add(message);
    }

    public void addMessageReserve(Message message) {
        reserve.add(message);
    }

    private void createLogger() {
        logger = Logger.getLogger(getClass());
    }

    public int getNumExecuted() {
        return numExecuted;
    }

    public long getWorkTime() {
        return workTime;
    }
}
