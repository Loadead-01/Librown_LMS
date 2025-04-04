package com.example.librarymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarymanagementsystem.Database.AccountDatabase;
import com.example.librarymanagementsystem.Database.BookDatabase;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText et_email, et_name, et_password;
    Button btn_login, btn_signup, btn_back;
    AccountDatabase DB_ACCOUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_back = findViewById(R.id.btn_back);

        DB_ACCOUNT = new AccountDatabase(this);

        btn_signup.setOnClickListener(v -> {
            String email = et_email.getText().toString().trim();
            String name = et_name.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (email.isEmpty()) {
                et_email.setError("Email is required");
                return;
            } else if (!Pattern.matches("^[a-zA-Z0-9._]+@cubao\\.sti\\.edu\\.ph",email)) {
                et_email.setError("Strictly only email from STI cubao");
                return;
            }

            if (name.isEmpty()) {
                et_name.setError("Name is required");
                return;
            }

            if (password.isEmpty()) {
                et_password.setError("Password is required");
                return;
            }

            DB_ACCOUNT.CREATE_USER(email,name,password);
        });

        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });

        btn_back.setOnClickListener(v -> {
            finish();
        });
    }
}