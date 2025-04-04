package com.example.librarymanagementsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.librarymanagementsystem.Admin.AdminDashboard;
import com.example.librarymanagementsystem.User.UserDashboard;

public class AccountDatabase extends SQLiteOpenHelper {
    Context context;
    private static final String DB_NAME = "account.db";
    private static final int DB_VERSION = 4;
    public AccountDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static final String TABLE_USER_ACCOUNT = "user_account";
    public static final String COLUMN_USER_ID = "user_ID";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String QUERY_USER = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USER_ACCOUNT + "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_EMAIL + " VARCHAR(255), " +
            COLUMN_USER_NAME + " VARCHAR(255), " +
            COLUMN_USER_PASSWORD + " VARCHAR(255))";
    private static final String DROP_USER = "DROP TABLE IF EXISTS " + TABLE_USER_ACCOUNT;
    private static final String TABLE_ADMIN_ACCOUNT = "admin_account";
    private static final String COLUMN_ADMIN_ID = "admin_id";
    public static final String COLUMN_ADMIN_EMAIL = "admin_email";
    public static final String COLUMN_ADMIN_NAME = "admin_name";
    public static final String COLUMN_ADMIN_PASSWORD = "admin_password";
    private static final String QUERY_ADMIN = "CREATE TABLE IF NOT EXISTS " +
            TABLE_ADMIN_ACCOUNT + "(" +
            COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ADMIN_EMAIL + " VARCHAR(255), " +
            COLUMN_ADMIN_NAME + " VARCHAR(255), " +
            COLUMN_ADMIN_PASSWORD + " VARCHAR(255))";
    private static final String DROP_ADMIN = "DROP TABLE IF EXISTS " + TABLE_ADMIN_ACCOUNT;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_USER);
        db.execSQL(QUERY_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER);
        db.execSQL(DROP_ADMIN);
        onCreate(db);
    }

    public void CREATE_USER(String email, String name, String password) {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT " + COLUMN_USER_EMAIL + " FROM " + TABLE_USER_ACCOUNT + " WHERE " + COLUMN_USER_EMAIL + " = ?", new String[]{email});
        if (!cursor.moveToFirst()) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_USER_EMAIL, email);
            cv.put(COLUMN_USER_NAME, name);
            cv.put(COLUMN_USER_PASSWORD, password);
            long result = db.insert(TABLE_USER_ACCOUNT, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Account Creation Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Account Already Exists", Toast.LENGTH_SHORT).show();
        }
    }

    public void USER_LOGIN(String p_email, String p_password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER_ACCOUNT + " WHERE "+ COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?", new String[]{p_email, p_password});
        if (cursor.moveToFirst()) {
            int user_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            //String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD));
            context.getSharedPreferences("USER_SESSION", context.MODE_PRIVATE).edit()
                    .putInt("USER_ID", user_id)
                    .putString("USER_EMAIL", email)
                    .putString("USER_NAME", name)
                    .putBoolean("LOGIN_SESSION", true)
                    .apply();
            Intent intent = new Intent(context, UserDashboard.class);
            context.startActivity(intent);
        } else {
            ADMIN_LOGIN(p_email, p_password);
        }
        cursor.close();
    }

    public void CREATE_ADMIN(String email, String name, String password) {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT " + COLUMN_ADMIN_EMAIL + " FROM " + TABLE_ADMIN_ACCOUNT + " WHERE " + COLUMN_ADMIN_EMAIL + " = ?", new String[]{email} );
        if (!cursor.moveToFirst()) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ADMIN_EMAIL, email);
            cv.put(COLUMN_ADMIN_NAME, name);
            cv.put(COLUMN_ADMIN_PASSWORD, password);
            long result = db.insert(TABLE_ADMIN_ACCOUNT, null, cv);
            if (result == -1) {
                //Toast.makeText(context, "Account Creation Failed", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
    }

    public void ADMIN_LOGIN(String p_email, String p_password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADMIN_ACCOUNT + " WHERE "+ COLUMN_ADMIN_EMAIL + " = ? AND " + COLUMN_ADMIN_PASSWORD + " = ?", new String[]{p_email, p_password});
        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_EMAIL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_NAME));
            //String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD));
            context.getSharedPreferences("ADMIN_SESSION", context.MODE_PRIVATE).edit()
                    .putString("ADMIN_EMAIL", email)
                    .putString("ADMIN_NAME", name)
                    .putBoolean("LOGIN_SESSION", true)
                    .apply();
            Intent intent = new Intent(context, AdminDashboard.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No user found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
