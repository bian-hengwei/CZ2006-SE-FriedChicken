package com.ntu.medcheck.model;

public interface Notification {
    String entryName = null;
    public void sendNotification();
    public void triggerNotification();
}
