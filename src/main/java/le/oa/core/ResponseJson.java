package le.oa.core;

public class ResponseJson {
    private boolean work = false;
    private String messages;

    public ResponseJson() {
    }

    public ResponseJson(boolean work, String messages) {
        this.work = work;
        this.messages = messages;
    }

    public boolean getWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
