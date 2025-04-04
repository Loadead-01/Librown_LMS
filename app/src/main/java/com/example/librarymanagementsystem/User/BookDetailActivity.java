package com.example.librarymanagementsystem.User;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarymanagementsystem.R;

public class BookDetailActivity extends AppCompatActivity {
    ImageView im_cover;
    TextView btn_back, tv_title, tv_author, tv_published, tv_availability, tv_description, tv_category;
    Button btn_borrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);
        im_cover = findViewById(R.id.im_cover);
        tv_title = findViewById(R.id.tv_title);
        tv_author = findViewById(R.id.tv_author);
        tv_published = findViewById(R.id.tv_published);
        tv_availability = findViewById(R.id.tv_availability);
        tv_description = findViewById(R.id.tv_description);
        btn_borrow = findViewById(R.id.btn_borrow);
        tv_category = findViewById(R.id.tv_category);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(v -> {
            overridePendingTransition(0,0);

            finish();
        });

        int image = getIntent().getIntExtra("IMAGE", 0);
        String title = getIntent().getStringExtra("TITLE");
        String author = getIntent().getStringExtra("AUTHOR");
        String published = getIntent().getStringExtra("PUBLISHED");
        int availability = getIntent().getIntExtra("AVAILABILITY", 1);
        String description = getIntent().getStringExtra("DESCRIPTION");
        int book_id = getIntent().getIntExtra("BOOK_ID", 0);
        String category = getIntent().getStringExtra("CATEGORY");

        im_cover.setImageResource(image);
        tv_title.setText(title);
        tv_author.setText("Author: " + author);
        tv_published.setText("Published: " + published);
        if (availability == 0) {
            tv_availability.setText("AVAILABLE");
            tv_availability.setTextColor(Color.GREEN);

        } else {
            tv_availability.setText("NOT AVAILABLE");
            tv_availability.setTextColor(Color.RED);
            btn_borrow.setClickable(false);
            btn_borrow.setAlpha(0);
            btn_borrow.setVisibility(View.GONE);
        }
        tv_description.setText(description);
        tv_category.setText(category);

        btn_borrow.setOnClickListener(v -> {
            Intent intent = new Intent(this, BorrowActivity.class);
            intent.putExtra("IMAGE", image);
            intent.putExtra("TITLE", title);
            intent.putExtra("AUTHOR", author);
            intent.putExtra("PUBLISHED", published);
            intent.putExtra("AVAILABILITY", availability);
            intent.putExtra("BOOK_ID", book_id);
            intent.putExtra("CATEGORY", category);
            intent.putExtra("DESCRIPTION", description);
            overridePendingTransition(0,0);
            startActivity(intent);
        });
    }
}