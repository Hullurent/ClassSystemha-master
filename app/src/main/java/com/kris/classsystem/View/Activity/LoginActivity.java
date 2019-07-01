package com.kris.classsystem.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kris.classsystem.Handler.Handle_login;
import com.kris.classsystem.R;


public class LoginActivity extends AppCompatActivity {
    private EditText username;

    private EditText passwd;

    private Button Login;

    private Handle_login handle_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        passwd = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        clickEvent();
    }


    private void clickEvent() {
        Login.setOnClickListener(v -> {
            if (username.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入学号", Toast.LENGTH_SHORT).show();
            } else if (passwd.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else {
                handle_login.activity_sync();
            }
        });

    }



}
