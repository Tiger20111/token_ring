package network.msg;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
    private ConcurrentLinkedQueue<Message> queue;
    public MessageQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public void addMessage(Message message) {
        queue.add(message);
    }
    public Message getMessage() {
        return queue.poll();
    }
}
