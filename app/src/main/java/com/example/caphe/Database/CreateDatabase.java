package com.example.caphe.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_DISPLAY_NAME = "displayName";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";


    public static final String TABLE_DRINKS = "drinks";
    public static final String COLUMN_DRINK_ID = "id";
    public static final String COLUMN_DRINK_NAME = "name";
    public static final String COLUMN_DRINK_PRICE = "price";
    public static final String COLUMN_DRINK_INFORMATION = "information";
    public static final String COLUMN_DRINK_IMAGE_RESOURCE = "imageResource";
    public CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_DISPLAY_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USERS);

        String CREATE_TABLE_DRINKS = "CREATE TABLE " + TABLE_DRINKS + "("
                + COLUMN_DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DRINK_NAME + " TEXT,"
                + COLUMN_DRINK_PRICE + " INTEGER,"
                + COLUMN_DRINK_INFORMATION + " TEXT,"
                + COLUMN_DRINK_IMAGE_RESOURCE + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_DRINKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nâng cấp cơ sở dữ liệu từ phiên bản cũ lên phiên bản mới
        if (oldVersion < 2) {  // Kiểm tra phiên bản cũ
            // Thêm cột imageResource vào bảng drinks
            String ALTER_TABLE_DRINKS = "ALTER TABLE " + TABLE_DRINKS + " ADD COLUMN " + COLUMN_DRINK_IMAGE_RESOURCE + " TEXT";
            db.execSQL(ALTER_TABLE_DRINKS);
        }
    }
}
