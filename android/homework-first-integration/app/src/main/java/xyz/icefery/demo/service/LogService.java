package xyz.icefery.demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class LogService extends IntentService {
    public LogService() {
        super("LogService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            Log.d("LogService", "线程 ID" + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}