package com.kris.classsystem.View.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(); // 无需布局渲染
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
