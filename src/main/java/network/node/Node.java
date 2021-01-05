package network.node;

import com.sun.jmx.remote.internal.ArrayQueue;
import network.msg.Message;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
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
    private long startTime;
    int id;
    private final Lock sleepLock = new ReentrantLock();
    private final Lock lock = new ReentrantLock();
    Condition condition = sleepLock.newCondition();
    public Node (int id, int reserveSize) {
        this.id = id;
        createLogger();
        reserve = new ArrayQueue<>(reserveSize);
        queue = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        startTime = System.nanoTime();
        logger.info("Thread.id = " + Thread.currentThread().getId() + " : Logger has started in " + startTime);
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
        workTime += stoppedTime - startTime;
        logger.info("Thread.id = " + Thread.currentThread().getId() + " : Logger has ended in " + stoppedTime);
    }

    public void setNextNode(Node node) {
        next = node;
    }

    public void turnOffNode() {
        lock.lock();
        work = false;
        sleepLock.lock();
        condition.signalAll();
        sleepLock.unlock();
        lock.unlock();
    }

    private void executeMessage() {
        Message message = queue.poll();
        if (message == null) {
            sendMessageFromReserve();
            return;
        }

        if (message.getId() == this.id) {
            numExecuted++;
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
            try {
                long stopTime = System.nanoTime();
                workTime += stopTime - startTime;
                sleepLock.lock();
                lock.unlock();
                condition.await();
                lock.lock();
                sleepLock.unlock();
                startTime = System.nanoTime();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        Message message = reserve.remove(0);
        next.addMessageTransfer(message);
    }

    public void addMessageTransfer(Message message) {
        queue.add(message);
        sleepLock.lock();
        condition.signal();
        sleepLock.unlock();
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
