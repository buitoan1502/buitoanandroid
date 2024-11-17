package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ttChuyenKhoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_chuyen_khoan);

        // Ánh xạ Button với ID "pay"
        Button payButton = findViewById(R.id.pay);

        // Thiết lập sự kiện chuyển Activity khi nhấn nút
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ttChuyenKhoan.this, hoaDon.class);
                startActivity(intent);
            }
        });
    }
}
