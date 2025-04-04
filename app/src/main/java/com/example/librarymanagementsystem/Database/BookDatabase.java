package com.example.librarymanagementsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.librarymanagementsystem.RecyclerView.Model.BookModel;
import com.example.librarymanagementsystem.RecyclerView.Model.BorrowModel;

import java.util.ArrayList;
import java.util.List;

public class BookDatabase extends SQLiteOpenHelper {
    Context context;
    private static final String DB_NAME = "book.db";
    private static final int DB_VERSION = 4;
    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_BOOK_ID = "book_id";
    public static final String COLUMN_BOOK_TITLE = "title";
    public static final String COLUMN_BOOK_CATEGORY = "category";

    public static final String COLUMN_BOOK_AUTHOR = "author";
    public static final String COLUMN_BOOK_DESCRIPTION = "description";
    public static final String COLUMN_BOOK_PUBLISHED = "published_date";
    public static final String COLUMN_BOOK_COVER = "cover";
    public static final String COLUMN_BOOK_AVAILABILITY = "availability";
    private static final String QUERY_BOOK = "CREATE TABLE IF NOT EXISTS " +
            TABLE_BOOK + "( " +
            COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BOOK_TITLE + " VARCHAR(255), " +
            COLUMN_BOOK_CATEGORY + " VARCHAR(255), " +
            COLUMN_BOOK_AUTHOR + " VARCHAR(255), " +
            COLUMN_BOOK_PUBLISHED + " VARCHAR(255), " +
            COLUMN_BOOK_DESCRIPTION + " TEXT, " +
            COLUMN_BOOK_COVER + " INTEGER, " +
            COLUMN_BOOK_AVAILABILITY + " INTEGER)";
    private static final String DROP_BOOK = "DROP TABLE IF EXISTS " + TABLE_BOOK;

    public static final String TABLE_BORROW = "borrow";
    public static final String COLUMN_BORROW_ID = "borrow_id";
    public static final String COLUMN_BORROW_USERID = "user_id";
    public static final String COLUMN_BORROW_DATE = "borrow_date";
    public static final String COLUMN_BORROW_STATUS = "status";

    private static final String QUERY_BORROW = "CREATE TABLE IF NOT EXISTS " +
            TABLE_BORROW + "(" +
            COLUMN_BORROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BOOK_ID + " INTEGER, " +
            COLUMN_BORROW_USERID + " INTEGER, " +
            COLUMN_BORROW_DATE + " VARCHAR(255), " +
            COLUMN_BORROW_STATUS + " VARCHAR(255))";
    private static final String DROP_BORROW = "DROP TABLE IF EXISTS " + TABLE_BORROW;

    private static BookDatabase instance;
    private static final Object LOCK = new Object();
    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {  // Ensures only one instance is created
                if (instance == null) {
                    instance = new BookDatabase(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
    public BookDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_BOOK);
        db.execSQL(QUERY_BORROW);
        if (db.isOpen()) {
            Log.d("DB_STATUS", "Database is open.");
        } else {
            Log.d("DB_STATUS", "Database failed to open.");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_BOOK);
        db.execSQL(DROP_BORROW);
        onCreate(db);
    }

    public void BORROW_LOG(int book_id, int user_id, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_ID, book_id);
        cv.put(COLUMN_BORROW_USERID, user_id);
        cv.put(COLUMN_BORROW_DATE, date);
        cv.put(COLUMN_BORROW_STATUS, status);
        long result = db.insert(TABLE_BORROW, null, cv);
        if (result != -1) {
           // Toast.makeText(context, "Log succesffully", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Log failed", Toast.LENGTH_SHORT).show();
        }
    }

    public List<BorrowModel> BORROW_HISTORY(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String QUERY_HISTORY = "SELECT * FROM " +
                TABLE_BORROW +
                " INNER JOIN " + TABLE_BOOK + " ON " + TABLE_BOOK+"."+ COLUMN_BOOK_ID + " = " + TABLE_BORROW+"."+ COLUMN_BOOK_ID +
                " WHERE " + COLUMN_BORROW_USERID + " = ? ";
        Cursor cursor = db.rawQuery(QUERY_HISTORY, new String[]{String.valueOf(user_id)});
        List<BorrowModel> borrow = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int borrow_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BORROW_ID));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_COVER));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_TITLE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROW_DATE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROW_STATUS));
                borrow.add(new BorrowModel(borrow_id, image, title, date, status));
            } while (cursor.moveToNext());
        }
        return borrow;
    }
    public void ADD_BOOK(String title, String category, String author, String published, String description, int cover, int availability) {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT " + COLUMN_BOOK_TITLE + " FROM " + TABLE_BOOK + " WHERE " + COLUMN_BOOK_TITLE + " = ?", new String[]{title});
        if (!cursor.moveToFirst()) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_BOOK_TITLE, title);
            cv.put(COLUMN_BOOK_CATEGORY, category);
            cv.put(COLUMN_BOOK_AUTHOR, author);
            cv.put(COLUMN_BOOK_PUBLISHED, published);
            cv.put(COLUMN_BOOK_DESCRIPTION, description);
            cv.put(COLUMN_BOOK_COVER, cover);
            cv.put(COLUMN_BOOK_AVAILABILITY, availability);
            long result = db.insert(TABLE_BOOK, null, cv);
            if (result == -1) {
              ////  Toast.makeText(context, "FAILED TO ADD BOOk", Toast.LENGTH_SHORT).show();
            } else {
              //  Toast.makeText(context, "BOOK ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Toast.makeText(context, "Book already exists", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    public List<BookModel> READ_NEW_BOOK() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "  + TABLE_BOOK + " ORDER BY " + COLUMN_BOOK_ID + " DESC LIMIT 5", null);
        List<BookModel> new_book = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int book_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_TITLE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_CATEGORY));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_AUTHOR));
                String published = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PUBLISHED));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_DESCRIPTION));
                int cover = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_COVER));
                int availability = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_AVAILABILITY));
                new_book.add(new BookModel(book_id, author, category, title, published, description, cover, availability));
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(context, "No book found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return  new_book;
    }


}
