package com.example.caphe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.caphe.Adapter.DrinkAdapter;
import com.example.caphe.DAO.DrinkDAO;
import com.example.caphe.Model.Drinks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Menu extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private GridView gridView;
    private DrinkDAO drinkDAO;
    private DrinkAdapter adapter;
    private List<Drinks> drinkList;

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Khởi tạo Drawer và các thành phần khác
        initializeDrawer();

        // Khởi tạo GridView và Adapter
        initializeGridView();

        // Thiết lập chức năng tìm kiếm
        initializeSearchFunctionality();
        FloatingActionButton fabCart = findViewById(R.id.fab_cart);
        fabCart.setOnTouchListener(new View.OnTouchListener()

        {
            private float downX, downY;
            private int lastAction;
            public boolean onTouch (View view, MotionEvent event){
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        // Lưu vị trí ban đầu của touch
                        downX = event.getRawX();
                        downY = event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Tính toán sự di chuyển và cập nhật vị trí của nút
                        float moveX = event.getRawX();
                        float moveY = event.getRawY();
                        view.setX(view.getX() + (moveX - downX));
                        view.setY(view.getY() + (moveY - downY));
                        downX = moveX;
                        downY = moveY;
                        lastAction = MotionEvent.ACTION_MOVE;
                        return true;

                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(Menu.this, Order.class);
                        startActivity(intent);
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    // Khởi tạo DrawerLayout và NavigationView
    private void initializeDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ImageButton menuButton = findViewById(R.id.menu_bt);
        menuButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(Menu.this, Profile.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(Menu.this, SettingMenu.class));
            } else if (id == R.id.nav_statistical) {
                startActivity(new Intent(Menu.this, ThongKe.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // Khởi tạo GridView và tải danh sách đồ uống từ cơ sở dữ liệu
    private void initializeGridView() {
        gridView = findViewById(R.id.recycler_view2);
        if (gridView == null) {
            Log.e("Menu", "GridView recycler_view2 không được tìm thấy trong layout.");
            return;
        }

        drinkDAO = new DrinkDAO(this);
        drinkList = drinkDAO.getAllDrinks();

        if (drinkList != null && !drinkList.isEmpty()) {
            adapter = new DrinkAdapter(this, drinkList);
            gridView.setAdapter(adapter);
        } else {
            Log.w("Menu", "Không có đồ uống nào để hiển thị.");
            Toast.makeText(this, "Hiện không có đồ uống nào trong danh sách.", Toast.LENGTH_SHORT).show();
        }
    }

    // Khởi tạo chức năng tìm kiếm
    private void initializeSearchFunctionality() {
        EditText searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = searchEditText.getText().toString().trim();
                performSearch(query);
                return true;
            }
            return false;
        });
    }

    // Phương thức thực hiện hành động tìm kiếm
    private void performSearch(String query) {
        if (query.isEmpty()) {
            // Nếu không có từ khóa, hiển thị lại toàn bộ danh sách
            adapter.updateData(drinkDAO.getAllDrinks());
            Toast.makeText(this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
        } else {
            List<Drinks> filteredDrinks = drinkDAO.searchDrinksByName(query);
            if (!filteredDrinks.isEmpty()) {
                adapter.updateData(filteredDrinks);
            } else {
                Toast.makeText(this, "Không tìm thấy kết quả nào cho: " + query, Toast.LENGTH_SHORT).show();
                adapter.updateData(drinkList); // Hiển thị lại danh sách ban đầu
            }
        }
    }


}
