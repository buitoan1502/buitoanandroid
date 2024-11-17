package com.example.caphe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caphe.Model.Drinks;
import com.example.caphe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {
    private SQLiteDatabase db;
    private CreateDatabase createDatabase;

    public DrinkDAO(Context context) {
        createDatabase = new CreateDatabase(context);
        db = createDatabase.getWritableDatabase();
    }

    // Thêm đồ uống vào cơ sở dữ liệu
    public boolean addDrink(Drinks drink) {
        ContentValues values = new ContentValues();
        values.put("name", drink.getName());
        values.put("price", drink.getPrice());
        values.put("information", drink.getInformation());
        values.put("imageResource", drink.getImageResource());

        long result = db.insert("drinks", null, values);
        return result != -1;
    }

    // Lấy danh sách tất cả đồ uống
    public List<Drinks> getAllDrinks() {
        List<Drinks> drinkList = new ArrayList<>();

        try (Cursor cursor = db.rawQuery("SELECT * FROM drinks", null)) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                    String information = cursor.getString(cursor.getColumnIndexOrThrow("information"));
                    String imageResource = cursor.getString(cursor.getColumnIndexOrThrow("imageResource"));

                    drinkList.add(new Drinks(id, name, price, information, imageResource));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drinkList;
    }

    public List<Drinks> searchDrinksByName(String query) {
        List<Drinks> drinkList = new ArrayList<>();

        try (Cursor cursor = db.rawQuery("SELECT * FROM drinks", null)) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                    String information = cursor.getString(cursor.getColumnIndexOrThrow("information"));
                    String imageResource = cursor.getString(cursor.getColumnIndexOrThrow("imageResource"));  // Lấy chuỗi

                    drinkList.add(new Drinks(id, name, price, information, imageResource));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return drinkList;
    }

    public boolean deleteDrink(int drinkId) {
        db = createDatabase.getWritableDatabase();
        int rowsAffected = db.delete("drinks", "id = ?", new String[]{String.valueOf(drinkId)});
        return rowsAffected > 0;  // Nếu có ít nhất 1 dòng bị xóa, trả về true
    }

    // Phương thức cập nhật đồ uống theo ID
    public boolean updateDrink(int drinkId, Drinks updatedDrink) {
        db = createDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", updatedDrink.getName());
        values.put("price", updatedDrink.getPrice());
        values.put("information", updatedDrink.getInformation());
        values.put("imageResource", updatedDrink.getImageResource());

        // Cập nhật theo ID
        int rowsAffected = db.update("drinks", values, "id = ?", new String[]{String.valueOf(drinkId)});
        return rowsAffected > 0;  // Nếu có ít nhất 1 dòng bị cập nhật, trả về true
    }

    // Đóng kết nối
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
