package xyz.icefery.demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import xyz.icefery.demo.activity.*;
import xyz.icefery.demo.receiver.GlobalBroadcastReceiver;
import xyz.icefery.demo.service.LogService;

public class MainActivity extends AppCompatActivity {
    private GlobalBroadcastReceiver globalBroadcastReceiver;

    private Button startSwitchFragmentActivityBtn;
    private Button startPersistenceByFileActivityBtn;
    private Button startPersistenceBySharedPreferencesActivityBtn;
    private Button startPersistenceBySQLiteActivityBtn;
    private Button startShareDataByContentProviderActivityBtn;
    private Button sendCustomBroadcastBtn;
    private Button startLogServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        // 日志记录当前线程 ID
        Log.d("MainActivity", "线程 ID ==> " + Thread.currentThread().getId());

        // 获取组件引用
        this.startSwitchFragmentActivityBtn = super.findViewById(R.id.start_switch_fragment_activity_btn);
        this.startPersistenceByFileActivityBtn = super.findViewById(R.id.start_persistence_by_file_activity_btn);
        this.startPersistenceBySharedPreferencesActivityBtn = super.findViewById(R.id.start_persistence_by_shared_preferences_activity_btn);
        this.startPersistenceBySQLiteActivityBtn = super.findViewById(R.id.start_persistence_by_sqlite_activity_btn);
        this.startShareDataByContentProviderActivityBtn = super.findViewById(R.id.start_share_data_by_content_provider_btn);
        this.sendCustomBroadcastBtn = super.findViewById(R.id.send_custom_broadcast_btn);
        this.startLogServiceBtn = super.findViewById(R.id.start_log_service_btn);

        // 初始化广播接收器
        this.globalBroadcastReceiver = new GlobalBroadcastReceiver();

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalBroadcastReceiver.CUSTOM_ACTION);
        super.registerReceiver(this.globalBroadcastReceiver, intentFilter);

        // 按钮点击事件 | 启动 SwitchFragmentActivity
        this.startSwitchFragmentActivityBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SwitchFragmentActivity.class);
            super.startActivity(intent);
        });

        // 按钮点击事件 | 启动 PersistenceByFileActivity
        this.startPersistenceByFileActivityBtn.setOnClickListener(v -> super.startActivity(new Intent(this, PersistenceByFileActivity.class)));

        // 按钮点击事件 | 启动 PersistenceBySPActivity
        this.startPersistenceBySharedPreferencesActivityBtn.setOnClickListener(v -> super.startActivity(new Intent(this, PersistenceBySharedPreferencesActivity.class)));

        // 按钮点击事件 | 启动 PersistenceBySQLiteActivity
        this.startPersistenceBySQLiteActivityBtn.setOnClickListener(v -> super.startActivity(new Intent(this, PersistenceBySQLiteActivity.class)));

        // 按钮点击事件 | 启动 ShareDataByCPActivity
        this.startShareDataByContentProviderActivityBtn.setOnClickListener(v -> super.startActivity(new Intent(this, ShareDataByContentProviderActivity.class)));

        // 按钮点击事件 | 发送自定义广播
        this.sendCustomBroadcastBtn.setOnClickListener(v -> super.sendBroadcast(new Intent(GlobalBroadcastReceiver.CUSTOM_ACTION)));

        // 按钮点击事件 | 启动 LogService
        this.startLogServiceBtn.setOnClickListener(v -> super.startService(new Intent(this, LogService.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播
        super.unregisterReceiver(this.globalBroadcastReceiver);
    }
}