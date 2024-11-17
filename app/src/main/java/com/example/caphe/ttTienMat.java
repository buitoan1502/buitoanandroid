package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ttTienMat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tt_tien_mat);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Chuyển sang màn hình đăng nhập
            Intent intent = new Intent(ttTienMat.this, Menu.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không trở lại
        },3000);
    }
}