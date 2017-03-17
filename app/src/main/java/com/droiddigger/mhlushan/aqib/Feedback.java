package com.droiddigger.mhlushan.aqib;

/**
 * Created by mihodihasan on 3/31/17.
 */

public class Feedback {
    String feedback,sender,receiver;

    public Feedback(String feedback, String sender, String receiver) {
        this.feedback = feedback;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
