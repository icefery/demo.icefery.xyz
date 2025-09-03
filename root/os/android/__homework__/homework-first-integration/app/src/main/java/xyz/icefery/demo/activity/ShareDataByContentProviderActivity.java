package xyz.icefery.demo.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import xyz.icefery.demo.R;

public class ShareDataByContentProviderActivity extends AppCompatActivity {

    private TextView uriInput;
    private EditText idInput;
    private EditText nameInput;
    private EditText ageInput;
    private EditText columnKInput;
    private EditText columnVInput;
    private Button insertBtn;
    private Button selectByIdBtn;
    private Button selectListBtn;
    private Button updateByIdBtn;
    private Button deleteByIdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_share_data_by_content_provider);

        // 获取组件引用
        this.uriInput = super.findViewById(R.id.uri_input);
        this.idInput = super.findViewById(R.id.id_input);
        this.nameInput = super.findViewById(R.id.name_input);
        this.ageInput = super.findViewById(R.id.age_input);
        this.columnKInput = super.findViewById(R.id.column_k_input);
        this.columnVInput = super.findViewById(R.id.column_v_input);
        this.insertBtn = super.findViewById(R.id.insert_btn);
        this.selectListBtn = super.findViewById(R.id.select_list_btn);
        this.selectByIdBtn = super.findViewById(R.id.select_by_id_btn);
        this.updateByIdBtn = super.findViewById(R.id.update_by_id_btn);
        this.deleteByIdBtn = super.findViewById(R.id.delete_by_id_btn);

        // 按钮点击事件 | 插入
        this.insertBtn.setOnClickListener(v -> {
                // 表单
                String uri = this.uriInput.getText().toString();
                String name = this.nameInput.getText().toString();
                int age = Integer.parseInt(this.ageInput.getText().toString());
                // 插入
                ContentValues valueMap = new ContentValues();
                valueMap.put("name", name);
                valueMap.put("age", age);
                Uri result = super.getContentResolver().insert(Uri.parse(uri), valueMap);
                Toast.makeText(this, "uri=" + result, Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 查询全部
        this.selectListBtn.setOnClickListener(v -> {
                // 表单
                String uri = this.uriInput.getText().toString();
                // 查询
                Cursor cursor = super.getContentResolver().query(Uri.parse(uri), null, null, null, null, null);
                // 遍历查询结果
                List<String> lines = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int age = cursor.getInt(cursor.getColumnIndex("age"));
                    lines.add("id=" + id + "\tname=" + name + "\tage=" + age);
                }
                // 关闭 Cursor
                cursor.close();
                Toast.makeText(this, String.join("\n", lines), Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 查询
        this.selectByIdBtn.setOnClickListener(v -> {
                // 表单
                String uri = this.uriInput.getText().toString();
                int id = Integer.parseInt(this.idInput.getText().toString());
                // 查询
                Cursor cursor = super.getContentResolver().query(Uri.parse(uri), null, "id=?", new String[] { String.valueOf(id) }, null);
                // 取查询结果
                String line = "";
                if (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int age = cursor.getInt(cursor.getColumnIndex("age"));
                    line = "id=" + id + "\tname=" + name + "\tage=" + age;
                }
                // 关闭 Cursor
                cursor.close();
                Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 更新
        this.updateByIdBtn.setOnClickListener(v -> {
                // 表单
                String uri = this.uriInput.getText().toString();
                int id = Integer.parseInt(this.idInput.getText().toString());
                String columnK = this.columnKInput.getText().toString();
                String columnV = this.columnVInput.getText().toString();
                // 更新
                ContentValues valueMap = new ContentValues();
                valueMap.put(columnK, columnV);
                boolean success = super.getContentResolver().update(Uri.parse(uri), valueMap, "id=?", new String[] { String.valueOf(id) }) >= 1;
                Toast.makeText(this, "修改 " + (success ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
            });

        // 按钮点击事件 | 删除
        this.deleteByIdBtn.setOnClickListener(v -> {
                // 表单
                String uri = this.uriInput.getText().toString();
                int id = Integer.parseInt(this.idInput.getText().toString());
                // 删除
                boolean success = super.getContentResolver().delete(Uri.parse(uri), "id=?", new String[] { String.valueOf(id) }) >= 1;
                Toast.makeText(this, "删除 " + (success ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
            });
    }
}
