package com.example.librarymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarymanagementsystem.Database.AccountDatabase;
import com.example.librarymanagementsystem.Database.BookDatabase;
import com.example.librarymanagementsystem.User.UserDashboard;

public class MainActivity extends AppCompatActivity {
    Button btn_login, btn_signup;
    AccountDatabase DB_ACCOUNT;
    BookDatabase DB_BOOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Boolean user_session = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getBoolean("LOGIN_SESSION", false);

        if (user_session) {
            Intent intent = new Intent(this, UserDashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();

        }

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        DB_ACCOUNT = new AccountDatabase(this);
        DB_BOOK = new BookDatabase(this);

        DB_ACCOUNT.CREATE_ADMIN("barcinilla@cubao.sti.edu.ph", "John Paul Barcinilla","qwertyui");
        DB_BOOK.ADD_BOOK("MATH", "programming","BARCI", "Janary 18 2009", "AW GAWDUO AUODGSDSUOGDWOSUODSGAODU", R.drawable.test, 0);
        DB_BOOK.ADD_BOOK("CLEAN CODE", "programming", "Robert Cecil Martin", "2008", "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn't have to be that way.", R.drawable.book1, 0);
        DB_BOOK.ADD_BOOK("Code: The Hidden", "programming", "Kelalin", "1999", "Code: The Hidden Language of Computer Hardware and Software is a book by Charles Petzold that seeks to teach how personal computers work at a hardware and software level.", R.drawable.book2, 0);
        DB_BOOK.ADD_BOOK("The Pragmatic Programmer", "programming", "Dave Thomas", "1999", "The Pragmatic Programmer: From Journeyman to Master is a book about computer programming and software engineering, written by Andrew Hunt and David Thomas and published in October 1999. It is used as a textbook in related university courses", R.drawable.book3, 0);


        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });

        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });
    }
}