package com.ntu.medcheck.model;

public class Setting {
    private String language = "english";
    private int fontSize = 3;
    private boolean vibration = true;
    private boolean allowNotification = true;

    // singleton
    private static Setting settingInstance = new Setting();
    private Setting() {

    }
    public static Setting getSettingInstance() {
        return settingInstance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean getVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public boolean getAllowNotification() {
        return allowNotification;
    }

    public void setAllowNotification(boolean allowNotification) {
        this.allowNotification = allowNotification;
    }
}
