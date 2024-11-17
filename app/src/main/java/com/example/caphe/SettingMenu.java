package com.example.caphe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.caphe.Adapter.DrinkAdapter;
import com.example.caphe.DAO.DrinkDAO;
import com.example.caphe.Model.Drinks;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class SettingMenu extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private GridView gridView;
    private DrinkDAO drinkDAO;
    private DrinkAdapter adapter;
    private List<Drinks> drinkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        // Lấy tham chiếu đến GridView
        gridView = findViewById(R.id.recycler_view1);
        if (gridView == null) {
            Log.e("Menu", "GridView recycler_view1 không được tìm thấy trong layout.");
            return; // Ngừng thực hiện nếu GridView không tìm thấy
        }

        // Khởi tạo DAO và lấy danh sách đồ uống từ cơ sở dữ liệu
        drinkDAO = new DrinkDAO(this);
        drinkList = drinkDAO.getAllDrinks();

        // Khởi tạo adapter và gán cho GridView
        adapter = new DrinkAdapter(this, drinkList);
        gridView.setAdapter(adapter);

        // Khởi tạo DrawerLayout và ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Khởi tạo button để mở
        ImageButton menuButton = findViewById(R.id.menu_bt);
        menuButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }

        });

        // Xử lý sự kiện khi người dùng chọn mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(SettingMenu.this, Profile.class));
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(SettingMenu.this, Menu.class));
            }
            else if (id == R.id.nav_statistical) {
                startActivity(new Intent(SettingMenu.this, ThongKe.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Sự kiện khi người dùng nhấn nút để chuyển đến activity InsertDrink
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(SettingMenu.this, InsertDrink.class);
            startActivity(intent);
        });
    }
}
