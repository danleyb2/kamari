package com.example.eva.kamari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView chose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chose = (TextView)findViewById(R.id.textView);
    }
    public void clicked(View v){
        Intent intent = new Intent(this, SinglePlayer.class);
        startActivity(intent);
    }
}
