package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caphe.Adapter.OrderAdapter;
import com.example.caphe.Model.Drinks;

import java.util.ArrayList;

public class Order extends AppCompatActivity {

    private RadioGroup radioGroupPayment;
    private ListView listViewOrders;
    private ArrayList<Drinks> orderList;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Ánh xạ các view
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        Button btnPay = findViewById(R.id.btnPay);
//        listViewOrders = findViewById(R.id.list_item);

//        // Khởi tạo danh sách và adapter
//        orderList = new ArrayList<>();
//        orderAdapter = new OrderAdapter(this, orderList);
//        listViewOrders.setAdapter(orderAdapter);
//
//        // Lấy dữ liệu đồ uống từ Intent
//        Intent intent = getIntent();
//        String drinkName = intent.getStringExtra("name");
//        if (drinkName != null) {
//            // Tạo đồ uống mới với tên đã nhận và thêm vào danh sách
//            Drinks newDrink = new Drinks(drinkName);
//            orderList.add(newDrink);
//            orderAdapter.notifyDataSetChanged();
//        }

        // Thiết lập sự kiện cho nút Pay
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupPayment.getCheckedRadioButtonId();
                Intent intent;

                // Kiểm tra xem RadioButton nào được chọn
                if (selectedId == R.id.tienmat) {
                    intent = new Intent(Order.this, hoaDon.class);
                } else if (selectedId == R.id.chuyenkhoan) {
                    intent = new Intent(Order.this, ttChuyenKhoan.class);
                } else {
                    // Hiển thị thông báo nếu không chọn phương thức thanh toán
                    Toast.makeText(Order.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Bắt đầu activity tương ứng
                startActivity(intent);
            }
        });
    }
}
