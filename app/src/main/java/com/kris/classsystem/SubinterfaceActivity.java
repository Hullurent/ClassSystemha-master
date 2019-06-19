package com.kris.classsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubinterfaceActivity extends AppCompatActivity {

    private Button mEnglishlevel;
    private Button mStandard;
    private Button mComputer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subinterface);
        mEnglishlevel = (Button) findViewById(R.id.Englishlevel);
        mEnglishlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri data = Uri.parse("http://cet-bm.neea.cn");
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
}
