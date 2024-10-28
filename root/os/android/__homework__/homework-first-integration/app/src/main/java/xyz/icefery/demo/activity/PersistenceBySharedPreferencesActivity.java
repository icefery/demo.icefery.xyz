package xyz.icefery.demo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import xyz.icefery.demo.R;

public class PersistenceBySharedPreferencesActivity extends AppCompatActivity {

    private EditText spNameInput;
    private EditText spKInput;
    private EditText spVInput;
    private Button saveSPKVBtn;
    private Button queryKBtn;
    private Button removeSPKBtn;
    private Button deleteSPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_persistence_by_shared_preferences);

        // 获取组件引用
        this.spNameInput = super.findViewById(R.id.student_name_input);
        this.spKInput = super.findViewById(R.id.sp_k_input);
        this.spVInput = super.findViewById(R.id.id_input);
        this.saveSPKVBtn = super.findViewById(R.id.save_sp_kv_btn);
        this.queryKBtn = super.findViewById(R.id.query_sp_k_btn);
        this.removeSPKBtn = super.findViewById(R.id.remove_sp_k_btn);
        this.deleteSPBtn = super.findViewById(R.id.delete_sp_btn);

        // 按钮点击事件 | 保存
        this.saveSPKVBtn.setOnClickListener(v -> {
                // 表单
                String spName = this.spNameInput.getText().toString();
                String spK = this.spKInput.getText().toString();
                String spV = this.spVInput.getText().toString();
                // 获取 SharedPreferences
                SharedPreferences sp = super.getSharedPreferences(spName, Context.MODE_PRIVATE);
                // 保存
                sp.edit().putString(spK, spV).apply();
                Toast.makeText(this, "已向 " + spName + " 保存 " + spK + "=" + spV, Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 查询
        this.queryKBtn.setOnClickListener(v -> {
                // 表单
                String spName = this.spNameInput.getText().toString();
                String spK = this.spKInput.getText().toString();
                // 获取 SharedPreferences
                SharedPreferences sp = super.getSharedPreferences(spName, Context.MODE_PRIVATE);
                // 查询
                String spV = sp.getString(spK, null);
                Toast.makeText(this, spK + "=" + spV, Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 移除
        this.removeSPKBtn.setOnClickListener(v -> {
                // 表单
                String spName = this.spNameInput.getText().toString();
                String spK = this.spKInput.getText().toString();
                // 获取 SharedPreferences
                SharedPreferences sp = super.getSharedPreferences(spName, Context.MODE_PRIVATE);
                // 移除
                sp.edit().remove(spK).apply();
                Toast.makeText(this, "已从 " + spName + " 移除 " + spK, Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 删除
        this.deleteSPBtn.setOnClickListener(v -> {
                // 表单
                String spName = this.spNameInput.getText().toString();
                // 删除 SharedPreferences
                boolean success = super.deleteSharedPreferences(spName);
                Toast.makeText(this, "删除 " + spName + " " + (success ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
            });
    }
}
