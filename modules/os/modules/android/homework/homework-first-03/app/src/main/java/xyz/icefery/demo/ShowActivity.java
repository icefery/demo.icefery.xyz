package xyz.icefery.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_KEY = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_show);
        Intent intent = super.getIntent();
        if (intent != null) {
            Student student = (Student) intent.getSerializableExtra(INTENT_EXTRA_KEY);
            ((EditText) super.findViewById(R.id.code_text)).setText(student.code);
            ((EditText) super.findViewById(R.id.name_text)).setText(student.name);
            ((EditText) super.findViewById(R.id.pass_text)).setText(student.pass);
            ((EditText) super.findViewById(R.id.mail_text)).setText(student.mail);
        }
    }
}