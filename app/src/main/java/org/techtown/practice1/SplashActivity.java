package org.techtown.practice1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.logging.LogRecord;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        
        handler.postDelayed(new Runnable(){
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                
                finish();
            }
        }, 1000);
    }
}