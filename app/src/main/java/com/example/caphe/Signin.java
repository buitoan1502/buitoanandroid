package com.example.caphe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caphe.DAO.UserDAO;

public class Signin extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLoginSubmit, btnSignup;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh2);

        userDAO = new UserDAO(this);
        initViews();
        setListeners();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        btnSignup = findViewById(R.id.btnSignup);
    }

    private void setListeners() {
        btnLoginSubmit.setOnClickListener(this::onLoginClick);
        btnSignup.setOnClickListener(v -> {
            startActivity(new Intent(Signin.this, Signup.class));
        });
    }

    private void onLoginClick(View v) {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Vui lòng nhập tên tài khoản và mật khẩu");
            return;
        }

        if (userDAO.checkLogin(username, password)) {

            showToast("Đăng nhập thành công!");
            // Lấy ID của người dùng (giả sử đã có một phương thức để lấy ID)
            int userId = userDAO.getUserIdByUsername(username); // Tạo một phương thức để lấy userId

            // Lưu ID người dùng vào SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id", userId); // userId là ID của tài khoản đăng nhập
            editor.apply();

            // Chuyển đến activity tiếp theo hoặc thực hiện hành động khác
            Intent intent = new Intent(Signin.this, Menu.class);
            startActivity(intent);
            finish();
        } else {
            showToast("Tên tài khoản hoặc mật khẩu không hợp lệ");
        }

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
