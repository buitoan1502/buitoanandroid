package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class hoaDon extends AppCompatActivity {
    Button inhoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoadon);

        // Liên kết nút inhoadon với layout
        inhoadon = findViewById(R.id.inhoadon);

        // Xử lý sự kiện khi nhấn vào nút inhoadon
        inhoadon.setOnClickListener(view -> {
            // Hiển thị thông báo
            Toast.makeText(hoaDon.this, "Đã in hóa đơn", Toast.LENGTH_SHORT).show();

            // Chuyển sang lớp ttTienMat
            Intent intent = new Intent(hoaDon.this, ttTienMat.class);
            startActivity(intent);
        });
    }
}
