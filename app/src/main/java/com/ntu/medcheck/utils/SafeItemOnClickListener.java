package com.ntu.medcheck.utils;

import android.view.View;
import android.widget.AdapterView;

public abstract class SafeItemOnClickListener implements AdapterView.OnItemClickListener {

    private static long lastClickMs = 0;
    private static long TOO_SOON_DURATION_MS = 700;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long nowMs = System.currentTimeMillis();
        if (lastClickMs != 0 && (nowMs - lastClickMs) < TOO_SOON_DURATION_MS){
            return;
        }
        lastClickMs = nowMs;
        onOneClick(parent, view, position, id);

    }

    public abstract void onOneClick(AdapterView<?> parent, View view, int position, long id);
}
