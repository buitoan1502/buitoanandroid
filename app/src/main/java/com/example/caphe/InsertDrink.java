package com.example.caphe;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.caphe.DAO.DrinkDAO;
import com.example.caphe.Model.Drinks;

import java.io.File;

public class InsertDrink extends AppCompatActivity {

    private ImageView itemImage;
    private EditText itemName, itemimform, itemPrice;
    private DrinkDAO drinkDAO;

    // Mảng các ID hình ảnh từ drawable
    private int[] imageResources = {
            R.drawable.caffe,
            R.drawable.bacxiu,
            R.drawable.nuocepcherry,
            R.drawable.nuocepmangcut,
            R.drawable.sinhtodau,
            R.drawable.sinhtosaurieng,
            R.drawable.caffeden,
            R.drawable.caffeematcha,
            R.drawable.macdinh
    };

    // Lưu ID hình ảnh đã chọn
    private int selectedImageResource = R.drawable.caffe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_drinks);

        // Ánh xạ các view
        itemImage = findViewById(R.id.itemImage);
        itemName = findViewById(R.id.itemName);
        itemimform = findViewById(R.id.itemimform);
        itemPrice = findViewById(R.id.itemPrice);
        Button insertButton = findViewById(R.id.insert);

        // Khởi tạo DrinkDAO
        drinkDAO = new DrinkDAO(this);

        // Thiết lập ảnh mặc định
        itemImage.setImageResource(selectedImageResource);

        // Cho phép chọn ảnh từ drawable khi nhấn vào ImageView
        itemImage.setOnClickListener(v -> showImagePickerDialog());

        // Xử lý thêm dữ liệu khi nhấn nút Insert
        insertButton.setOnClickListener(v -> insertDrink());

    }

    private void showImagePickerDialog() {
        // Tạo danh sách tên các ảnh để hiển thị trong dialog
        String[] imageNames = {"Cà phê", "Bạc xỉu", "Nước ép cherry", "Nước ép măng cụt",
                "Sinh tố dâu", "Sinh tố sầu riêng", "Cà phê đen",
                "Cà phê matcha", "Mặc định"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hình ảnh")
                .setItems(imageNames, (dialog, which) -> {
                    // Cập nhật hình ảnh đã chọn và hiển thị lên ImageView
                    selectedImageResource = imageResources[which];
                    itemImage.setImageResource(selectedImageResource);
                })
                .show();
    }

    private void insertDrink() {
        String name = itemName.getText().toString();
        String information = itemimform.getText().toString();
        String priceStr = itemPrice.getText().toString();

        // Kiểm tra các trường bắt buộc
        if (name.isEmpty() || information.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int price = Integer.parseInt(priceStr);
        String imageUri = String.valueOf(selectedImageResource);
        Drinks drink = new Drinks(0, name, price, information, imageUri);

        boolean isInserted = drinkDAO.addDrink(drink);
        // Thêm vào database
        if (isInserted) {
            Toast.makeText(this, "Thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi thêm đồ uống", Toast.LENGTH_SHORT).show();
        }
    }

}
