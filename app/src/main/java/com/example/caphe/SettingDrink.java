package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caphe.DAO.DrinkDAO;
import com.example.caphe.Model.Drinks;

public class SettingDrink extends AppCompatActivity {

    private EditText itemName, itemDescription, itemPrice;
    private Button btnDelete, btnUpdate;
    private int drinkId;  // Thay đổi kiểu dữ liệu thành int để lưu ID đồ uống

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_drink);

        // Ánh xạ các thành phần UI
        itemName = findViewById(R.id.itemName);
        itemDescription = findViewById(R.id.itemimform);
        itemPrice = findViewById(R.id.itemPrice);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        drinkId = intent.getIntExtra("id", -1);  // Lấy ID đồ uống (kiểu int)
        String drinkName = intent.getStringExtra("name");
        String drinkDescription = intent.getStringExtra("information");
        int price = intent.getIntExtra("price", 0);

        // Hiển thị thông tin đồ uống vào các View
        itemName.setText(drinkName);
        itemDescription.setText(drinkDescription);
        itemPrice.setText(String.valueOf(price));

        // Xử lý khi nhấn nút "Delete"
        btnDelete.setOnClickListener(v -> deleteDrinkFromDatabase(drinkId));

        // Xử lý khi nhấn nút "Update"
        btnUpdate.setOnClickListener(v -> updateDrinkInDatabase(drinkId));
    }

    private void deleteDrinkFromDatabase(int drinkId) {
        // Thực hiện xóa đồ uống khỏi cơ sở dữ liệu dựa trên ID
        DrinkDAO drinkDAO = new DrinkDAO(this);
        boolean isDeleted = drinkDAO.deleteDrink(drinkId); // Xóa theo ID đồ uống
        if (isDeleted) {
            Toast.makeText(this, "Đồ uống đã được xóa!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại activity trước
        } else {
            Toast.makeText(this, "Lỗi khi xóa đồ uống!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDrinkInDatabase(int drinkId) {
        // Lấy dữ liệu mới từ UI
        String newName = itemName.getText().toString();
        String newDescription = itemDescription.getText().toString();
        String newPrice = itemPrice.getText().toString();

        // Chuyển đổi newPrice từ String sang int
        int price = 0;
        try {
            price = Integer.parseInt(newPrice);  // Chuyển đổi giá trị thành int
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Nếu gặp lỗi thì in ra lỗi và giá sẽ là 0
        }

        // Tạo đối tượng Drinks mới với tên mới
        DrinkDAO drinkDAO = new DrinkDAO(this);
        Drinks updatedDrink = new Drinks(drinkId, newName, price, newDescription, null);  // Cập nhật thông tin đồ uống

        // Cập nhật thông tin đồ uống vào cơ sở dữ liệu dựa trên ID
        boolean isUpdated = drinkDAO.updateDrink(drinkId, updatedDrink); // Cập nhật theo ID đồ uống

        if (isUpdated) {
            Toast.makeText(this, "Thông tin đồ uống đã được cập nhật!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại activity trước
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật đồ uống!", Toast.LENGTH_SHORT).show();
        }
    }
}
