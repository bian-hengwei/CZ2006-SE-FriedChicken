package com.ntu.medcheck.utils;

import android.view.View;

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
        onOneClick(v);
    }

    public abstract void onOneClick(View v);

}
