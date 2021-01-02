package network.msg;

public class Message {
    private String message;
    private long id;
    private long sendTime = 0;
    private long receivedTime = 0;
    private boolean startDeliver;

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSendTime() {
        return sendTime;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public boolean getStartDeliver() {
        return startDeliver;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
        this.startDeliver = true;
    }
}
