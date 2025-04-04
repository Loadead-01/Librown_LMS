package com.example.librarymanagementsystem;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarymanagementsystem.Database.AccountDatabase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;
    Button btn_login, btn_signup;
    AccountDatabase DB_ACCOUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        DB_ACCOUNT = new AccountDatabase(this);

        btn_login.setOnClickListener(v -> {
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            Dialog loading = new Dialog(this);

            loading.setContentView(R.layout.dialog_loading);
            loading.show();
            if (email.isEmpty()) {
                et_email.setError("Email is required!");
                loading.dismiss();
                return;
            } else if (!Pattern.matches("^[a-zA-Z0-9._]+@cubao\\.sti\\.edu\\.ph$", email)) {
                et_email.setError("Strictly email from STI cubao are allowed!");
                loading.dismiss();
                return;
            }

            if (password.isEmpty()) {
                et_password.setError("Password is empty");
                loading.dismiss();
                return;
            }

            new Handler().postDelayed(() -> {
                DB_ACCOUNT.USER_LOGIN(email, password);
                loading.dismiss();
            }, 2000);

        });

        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });

    }
}