package com.dcvg.du_an_mau.screen;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dcvg.du_an_mau.R;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("My Data", MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    String password = sharedPreferences.getString("password", "");
                    if (username.isEmpty() && password.isEmpty()) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                }
            }
        };
        th.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}