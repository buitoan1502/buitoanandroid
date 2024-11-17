package com.example.caphe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caphe.Model.User;
import com.example.caphe.Database.CreateDatabase;

public class UserDAO {
    private SQLiteDatabase db;
    private CreateDatabase createDatabase;

    public UserDAO(Context context) {
        createDatabase = new CreateDatabase(context);
        db = createDatabase.getWritableDatabase();
    }

    public long addUser(String username, String displayName, String email, String phoneNumber, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("displayName", displayName);
        values.put("email", email);
        values.put("phoneNumber", phoneNumber);
        values.put("password", password);
        return db.insert("users", null, values);
    }

    public boolean checkLogin(String username, String password) {
        String[] columns = {"username", "password"};
        String selection = "username=? AND password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public User getUserById(int userId) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE id=?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(1); // Giả sử username ở cột thứ 1
            String displayName = cursor.getString(2);
            String email = cursor.getString(3);
            String phoneNumber = cursor.getString(4);
            String password = cursor.getString(5);
            cursor.close();
            return new User(userId, username, displayName, email, phoneNumber, password);
        }
        return null;
    }

    public int getUserIdByUsername(String username) {
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username=?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(0); // Giả sử ID ở cột đầu tiên
            cursor.close();
            return userId;
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }


}
