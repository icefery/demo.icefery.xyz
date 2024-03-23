package xyz.icefery.demo.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import xyz.icefery.demo.R;
import xyz.icefery.demo.util.DemoDBHelper;

import java.util.ArrayList;
import java.util.List;

public class PersistenceBySQLiteActivity extends AppCompatActivity {
    private DemoDBHelper demoDbHelper;

    private EditText tableInput;
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
        super.setContentView(R.layout.activity_persistence_by_sqlite);

        // 获取组件引用
        this.tableInput = super.findViewById(R.id.table_input);
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

        // 初始化 SQLiteOpenHelper
        this.demoDbHelper = new DemoDBHelper(super.getApplicationContext());

        // 获取 DB
        SQLiteDatabase db = this.demoDbHelper.getWritableDatabase();

        // 按钮点击事件 | 插入
        this.insertBtn.setOnClickListener(v -> {
            // 表单
            String table = this.tableInput.getText().toString();
            String name = this.nameInput.getText().toString();
            String age = this.ageInput.getText().toString();
            // 插入 | INSERT INTO student(name, age) VALUES(#{name}, #{age})
            ContentValues valueMap = new ContentValues();
            valueMap.put("name", name);
            valueMap.put("age", Integer.parseInt(age));
            long id = db.insert(table, null, valueMap);
            Toast.makeText(this, "id=" + id, Toast.LENGTH_SHORT).show();
        });

        // 按钮点击事件 | 查询全部
        this.selectListBtn.setOnClickListener(v -> {
            // 表单
            String table = this.tableInput.getText().toString();
            // 查询 | SELECT * FROM student
            Cursor cursor = db.query(table, null, null, null, null, null, null);
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
            String table = this.tableInput.getText().toString();
            String id = this.idInput.getText().toString();
            // 查询 | SELECT * FROM student WHERE id=#{id}
            Cursor cursor = db.query(table, null, "id=?", new String[]{id}, null, null, null);
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
            String table = this.tableInput.getText().toString();
            String id = this.idInput.getText().toString();
            String columnK = this.columnKInput.getText().toString();
            String columnV = this.columnVInput.getText().toString();
            // 更新 | UPDATE student SET #{columnK}=#{columnV} WHERE id=#{id}
            ContentValues valueMap = new ContentValues();
            valueMap.put(columnK, columnV);
            boolean success = db.update(table, valueMap, "id=?", new String[]{id}) >= 1;
            Toast.makeText(this, "修改 " + (success ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
        });

        // 按钮点击事件 | 删除
        this.deleteByIdBtn.setOnClickListener(v -> {
            // 表单
            String table = this.tableInput.getText().toString();
            int id = Integer.parseInt(this.idInput.getText().toString());
            // 删除 | DELETE FROM student WHERE id=#{id}
            boolean success = db.delete(table, "id=?", new String[]{String.valueOf(id)}) >= 1;
            Toast.makeText(this, "删除 " + (success ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
        });
    }
}