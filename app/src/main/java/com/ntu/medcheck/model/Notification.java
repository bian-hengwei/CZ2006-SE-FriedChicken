package com.ntu.medcheck.model;

/**
 * Notification interface
 * contains methods to send notifications
 */
public interface Notification {
    String entryName = null;
    public void sendNotification();
    public void triggerNotification();
}
