package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh1);

        // Log để theo dõi việc vào MainActivity
        Log.d("MainActivity", "onCreate: MainActivity is created");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Chuyển sang màn hình đăng nhập
            Intent intent = new Intent(MainActivity.this, Signin.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không trở lại
        }, 2000); // Đợi 2 giây trước khi chuyển
    }
}
