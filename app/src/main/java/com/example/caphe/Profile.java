package com.example.caphe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.caphe.DAO.UserDAO;
import com.example.caphe.Model.User;
import android.widget.Button;
public class Profile extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneNumberEditText, passwordEditText;
    private UserDAO userDAO;
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các EditText từ XML
        nameEditText = findViewById(R.id.NameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        logoutButton = findViewById(R.id.logoutButton);
        // Khởi tạo UserDAO
        userDAO = new UserDAO(this);

        // Tải dữ liệu người dùng
        loadUserData();
        // Thiết lập sự kiện nhấn nút Log out
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin đăng nhập trong SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_id"); // Xóa user_id
                editor.apply();

                // Chuyển hướng về lớp Login
                Intent intent = new Intent(Profile.this, Signin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack activity trước đó
                startActivity(intent);
                finish();
            }
        });
    }


    private void loadUserData() {
        // Lấy ID người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // Lấy userId, mặc định là -1 nếu không tìm thấy

        if (userId != -1) {
            // Lấy thông tin người dùng theo ID
            User user = userDAO.getUserById(userId); // Sử dụng phương thức này để lấy user theo ID

            if (user != null) {
                // Đặt dữ liệu vào các EditText
                nameEditText.setText(user.getDisplayName());
                emailEditText.setText(user.getEmail());
                phoneNumberEditText.setText(user.getPhoneNumber());
                passwordEditText.setText(user.getPassword());
            }
        }
    }
}
