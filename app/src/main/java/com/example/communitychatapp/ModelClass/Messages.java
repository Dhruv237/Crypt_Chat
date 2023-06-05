package com.example.communitychatapp.ModelClass;

public class Messages {
    String msg;
    String sender_id;
    long Timestamp;

    public Messages() {
    }

    public Messages(String msg, String sender_id, long timestamp) {
        this.msg = msg;
        this.sender_id = sender_id;
        Timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }
}
