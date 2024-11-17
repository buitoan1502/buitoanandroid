package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caphe.DAO.UserDAO;

public class Signup extends AppCompatActivity {
    private EditText edtUsername, edtDisplayName, edtEmail, edtPhone, edtPassword;
    private Button btnSignUp, btnLogin;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh3);

        userDAO = new UserDAO(this);
        initViews();
        setListeners();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edtten);
        edtDisplayName = findViewById(R.id.edttaikhoan);
        edtEmail = findViewById(R.id.edtemail);
        edtPhone = findViewById(R.id.sdt);
        edtPassword = findViewById(R.id.edtmk);
        btnSignUp = findViewById(R.id.dangnhap);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setListeners() {
        btnSignUp.setOnClickListener(v -> registerUser());
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Signin.class); // Chuyển đến lớp Signin
            startActivity(intent);
        });
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String displayName = edtDisplayName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (areFieldsEmpty(username, displayName, email, phoneNumber, password)) return;

        if (!isEmailValid(email)) {
            showToast("Vui lòng nhập địa chỉ email hợp lệ");
            return;
        }

        if (phoneNumber.length() < 10) {
            showToast("Số điện thoại phải có ít nhất 10 ký tự");
            return;
        }

        try {
            long result = userDAO.addUser(username, displayName, email, phoneNumber, password);
            if (result > 0) {
                showToast("Đăng ký thành công!");
                startActivity(new Intent(Signup.this, Signin.class));
                finish();
            } else {
                showToast("Đăng ký thất bại!");
                Log.e("SignupError", "Không thể thêm người dùng vào cơ sở dữ liệu - Lỗi không xác định");
            }
        } catch (Exception e) {
            showToast("Đăng ký thất bại do lỗi hệ thống!");
            Log.e("SignupError", "Lỗi khi thêm người dùng: " + e.getMessage());
        }
    }

    private boolean areFieldsEmpty(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                showToast("Vui lòng điền tất cả các trường");
                return true;
            }
        }
        return false;
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
