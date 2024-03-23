package xyz.icefery.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class GlobalBroadcastReceiver extends BroadcastReceiver {
    public static final String CUSTOM_ACTION = "xyz.icefery.demo.action.CUSTOM_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                Log.d("GlobalReceiver", "收到系统开机广播");
                break;
            case CUSTOM_ACTION:
                Log.d("GlobalReceiver", "收到自定义广播");
                Toast.makeText(context, "收到自定义广播", Toast.LENGTH_LONG).show();
                break;
            default:
                Log.d("GlobalReceiver", "收到其它广播");
                Toast.makeText(context, "收到其它广播", Toast.LENGTH_LONG).show();
        }
    }
}