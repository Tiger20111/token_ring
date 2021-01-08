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
    private final ArrayQueue<Message> reserve;
    private final ConcurrentLinkedQueue<Message> queue;
    private int numExecuted = 0;
    private int allMessage;
    private long workTime;
    private long startTime;
    int id;
    private final Lock sleepLock = new ReentrantLock();
    Condition condition = sleepLock.newCondition();
    public Node (int id, int reserveSize, int shift) {
        this.id = id;
        allMessage = reserveSize * (shift);
        reserve = new ArrayQueue<>(reserveSize);
        queue = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        startTime = System.nanoTime();
        while (!reserve.isEmpty() || numExecuted < allMessage) {
            executeMessage();
            if (numExecuted > allMessage) {
                System.out.println();
            }
        }
        workTime += System.nanoTime() - startTime;
    }

    public void setNextNode(Node node) {
        next = node;
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
//            try {
//                long stopTime = System.nanoTime();
//                workTime += stopTime - startTime;
//                sleepLock.lock();
//                condition.await();
//                sleepLock.unlock();
//                startTime = System.nanoTime();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return;
        }
        Message message = reserve.remove(0);
        next.addMessageTransfer(message);
    }

    public void addMessageTransfer(Message message) {
        queue.add(message);
//        sleepLock.lock();
//        condition.signal();
//        sleepLock.unlock();
    }

    public void addMessageReserve(Message message) {
        reserve.add(message);
    }

    public long getWorkTime() {
        return workTime;
    }
}
