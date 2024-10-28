package xyz.icefery.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import xyz.icefery.demo.R;
import xyz.icefery.demo.fragment.TabFragment;

public class SwitchFragmentActivity extends AppCompatActivity {

    private Button tab1Btn;
    private Button tab2Btn;
    private Button tab3Btn;

    private TabFragment tab1Fragment;
    private TabFragment tab2Fragment;
    private TabFragment tab3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_switch_fragment);

        // 日志记录当前生命周期
        Log.d("TabFragmentActivity 生命周期", "Create");

        // 获取组件引用
        this.tab1Btn = super.findViewById(R.id.tab1_btn);
        this.tab2Btn = super.findViewById(R.id.tab2_btn);
        this.tab3Btn = super.findViewById(R.id.tab3_btn);

        // 实例化 Fragment
        this.tab1Fragment = new TabFragment("Tab1", R.drawable.mulan);
        this.tab2Fragment = new TabFragment("Tab2", R.drawable.libai);
        this.tab3Fragment = new TabFragment("Tab3", R.drawable.xuance);

        // 获取 FragmentManager
        FragmentManager fm = super.getSupportFragmentManager();

        // 按钮点击事件 | 切换 Fragment
        this.tab1Btn.setOnClickListener(v -> fm.beginTransaction().replace(R.id.fragment_container, this.tab1Fragment).commit());
        this.tab2Btn.setOnClickListener(v -> fm.beginTransaction().replace(R.id.fragment_container, this.tab2Fragment).commit());
        this.tab3Btn.setOnClickListener(v -> fm.beginTransaction().replace(R.id.fragment_container, this.tab3Fragment).commit());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TabFragmentActivity 生命周期", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TabFragmentActivity 生命周期", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TabFragmentActivity 生命周期", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TabFragmentActivity 生命周期", "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TabFragmentActivity 生命周期", "Destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TabFragmentActivity 生命周期", "Restart");
    }
}
