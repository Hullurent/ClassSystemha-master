package com.kris.classsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username;

    private EditText passwd;

    private Button Login;
    private String loginUrl = "http://202.203.158.158/sso/ssoLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        passwd = findViewById(R.id.password);

    }


    private void clickEvent() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入学号", Toast.LENGTH_SHORT);
                } else if (passwd.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT);
                } else {
                    login();
                }
            }
        });

    }

    /**
     * 登陆sso认证页面
     **/
    private void login() {
        final String id = username.getText().toString();
        final String pw = passwd.getText().toString();

        ExecutorService ThreadPool = Executors.newFixedThreadPool(3);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String passwdMd5 = "1d13c5b7fe30634b5a96ba9724d0aa2b";
                    RequestBody body = new FormBody.Builder()
                            .add("username", id)
                            .add("password", passwdMd5)
                            .build();
                    Request request = new Request.Builder()
                            .url(loginUrl)
                            .addHeader("Host", "202.203.158.158")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .addHeader("Cookie", "JSESSIONID=EA974E56B36757AC9DD913463FB2E32F; JSESSIONID=50BD21555829ECCBE11A4834D14D5759")
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                            .addHeader("Referer", "http://202.203.158.158/sso//login?service=http://202.203.158.106/jwweb/cas_ynmz.aspx")
                            .post(body)
                            .build();

                    Response response = okHttpClient.newCall(request).execute();
                    Document document = Jsoup.parse(response.body().string());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        ThreadPool.execute(runnable);
    }

}
