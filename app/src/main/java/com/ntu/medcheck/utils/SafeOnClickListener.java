package com.ntu.medcheck.utils;

import android.view.View;

import java.text.ParseException;

public abstract class SafeOnClickListener implements View.OnClickListener {
    private static long lastClickMs = 0;
    private static long TOO_SOON_DURATION_MS = 700;
    /**
     * Override onOneClick() instead.
     */
    @Override
    public final void onClick(View v) {
        long nowMs = System.currentTimeMillis();
        if (lastClickMs != 0 && (nowMs - lastClickMs) < TOO_SOON_DURATION_MS){
            return;
        }
        lastClickMs = nowMs;
        try {
            onOneClick(v);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public abstract void onOneClick(View v) throws ParseException;
}
