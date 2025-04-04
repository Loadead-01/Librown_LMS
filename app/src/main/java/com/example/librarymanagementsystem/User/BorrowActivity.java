package com.example.librarymanagementsystem.User;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarymanagementsystem.Database.BookDatabase;
import com.example.librarymanagementsystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowActivity extends AppCompatActivity {
    ImageView im_cover;
    TextView btn_back, tv_author, tv_title, tv_published, tv_category, tv_name, tv_email;
    Button btn_borrow;
    BookDatabase DB_BOOK;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_borrow);
        im_cover = findViewById(R.id.im_cover);
        tv_author = findViewById(R.id.tv_author);
        tv_title = findViewById(R.id.tv_title);
        tv_published = findViewById(R.id.tv_published);
        tv_category = findViewById(R.id.tv_category);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        btn_borrow = findViewById(R.id.btn_borrow);
        btn_back = findViewById(R.id.btn_back);
        DB_BOOK = new BookDatabase(this);

        btn_back.setOnClickListener(v -> {
            overridePendingTransition(0,0);
            finish();
        });


        String title = getIntent().getStringExtra("TITLE");
        String author = getIntent().getStringExtra("AUTHOR");
        String published = getIntent().getStringExtra("PUBLISHED");
        String category = getIntent().getStringExtra("CATEGORY");
        int image = getIntent().getIntExtra("IMAGE", 1);
        String name = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getString("USER_NAME", "aha");
        String email = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getString("USER_EMAIL", "aha");
        int book_id = getIntent().getIntExtra("BOOK_ID", 1);
        int user_id = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getInt("USER_ID", 1);

        tv_title.setText(title);
        tv_author.setText("Author: " + author);
        tv_published.setText("Published: " + published);
        tv_category.setText("Category: " + category);
        tv_name.setText("Name: " + name);
        tv_email.setText("Email: " + email);
        im_cover.setImageResource(image);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy h:mm:ss a");
        Date currentDate = new Date();
        String date = sdf.format(currentDate);

        btn_borrow.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_borrow_success);
            ProgressBar pb = dialog.findViewById(R.id.pb_loading);
            TextView tv_notif = dialog.findViewById(R.id.tv_notif);

            new Handler().postDelayed(() -> {
                pb.setVisibility(View.GONE);
                tv_notif.setText("ITEM BORROWED SUCCESSFULLY");
                tv_notif.setVisibility(View.VISIBLE);
                DB_BOOK.BORROW_LOG(book_id, user_id, date, "PENDING");
            }, 3000);
            dialog.show();
        });
    }
}