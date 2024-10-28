package xyz.icefery.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import xyz.icefery.demo.R;

public class PersistenceByFileActivity extends AppCompatActivity {

    private EditText fileNameInput;
    private EditText fileContentInput;
    private Button writeToFileBtn;
    private Button readFromFileBtn;
    private Button deleteFileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_persistence_by_file);

        // 获取组件引用
        this.fileNameInput = super.findViewById(R.id.file_name_input);
        this.fileContentInput = super.findViewById(R.id.file_content_input);
        this.writeToFileBtn = super.findViewById(R.id.write_to_file_btn);
        this.readFromFileBtn = super.findViewById(R.id.read_from_file_btn);
        this.deleteFileBtn = super.findViewById(R.id.delete_file_btn);

        // 按钮点击事件 | 写入文件
        this.writeToFileBtn.setOnClickListener(v -> {
                // 表单
                String fileName = this.fileNameInput.getText().toString();
                String fileContent = this.fileContentInput.getText().toString();
                try (
                    FileOutputStream os = super.openFileOutput(fileName, Context.MODE_PRIVATE);
                    BufferedWriter w = new BufferedWriter(new OutputStreamWriter(os))
                ) {
                    // 写入文件
                    w.write(fileContent);
                    Toast.makeText(this, "向 " + fileName + " 写入 " + fileContent + " 成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "向 " + fileName + " 写入 " + fileContent + " 失败", Toast.LENGTH_SHORT).show();
                }
            });

        // 按钮点击事件 | 读取文件
        this.readFromFileBtn.setOnClickListener(v -> {
                // 表单
                String fileName = this.fileNameInput.getText().toString();
                try (FileInputStream is = super.openFileInput(fileName); BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
                    // 读取文件 | 读取一行
                    String filecontent = r.readLine();
                    Toast.makeText(this, filecontent, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, fileName + " 不存在", Toast.LENGTH_SHORT).show();
                }
            });

        // 按钮点击事件 | 删除文件
        this.deleteFileBtn.setOnClickListener(v -> {
                // 表单
                String fileName = this.fileNameInput.getText().toString();
                // 删除文件
                boolean success = super.deleteFile(fileName);
                Toast.makeText(this, "删除 " + fileName + " " + (success ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
            });
    }
}
