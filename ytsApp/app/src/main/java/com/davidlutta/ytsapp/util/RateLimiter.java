package com.davidlutta.ytsapp.util;

import android.os.SystemClock;
import android.util.ArrayMap;

import java.util.concurrent.TimeUnit;

public class RateLimiter<KEY> {
    private final long timeout;
    private ArrayMap<KEY, Long> timeStamps = new ArrayMap<>();

    public RateLimiter(long timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(KEY key) {
        Long lastFetched = timeStamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timeStamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timeStamps.put(key, now);
            return true;
        }
        return false;
    }

    private long now() {
        return SystemClock.uptimeMillis();
    }

    private synchronized void reset(KEY key) {
        timeStamps.remove(key);
    }
}
