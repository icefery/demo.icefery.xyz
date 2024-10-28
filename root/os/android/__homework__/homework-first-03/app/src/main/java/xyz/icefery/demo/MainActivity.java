package xyz.icefery.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        super.findViewById(R.id.submit_btn).setOnClickListener(v -> {
            // 表单
            Student student = new Student(
                ((EditText) super.findViewById(R.id.code_input)).getText().toString(),
                ((EditText) super.findViewById(R.id.name_input)).getText().toString(),
                ((EditText) super.findViewById(R.id.pass_input)).getText().toString(),
                ((EditText) super.findViewById(R.id.mail_input)).getText().toString()
            );
            // Intent
            Intent intent = new Intent(super.getApplicationContext(), ShowActivity.class);
            intent.putExtra(ShowActivity.INTENT_EXTRA_KEY, student);
            // Activity
            super.startActivity(intent);
        });
    }
}
