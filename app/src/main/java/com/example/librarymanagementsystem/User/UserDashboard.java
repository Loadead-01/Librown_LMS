package com.example.librarymanagementsystem.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystem.Database.BookDatabase;
import com.example.librarymanagementsystem.MainActivity;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.User.Fragment.BorrowHistory;
import com.example.librarymanagementsystem.User.Fragment.MainDashboard;

public class UserDashboard extends AppCompatActivity {
    TextView btn_logout, btn_history, btn_home;
    RecyclerView rv_newbook;
    BookDatabase DB_BOOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dashboard);
        btn_logout = findViewById(R.id.btn_logout);
        btn_history = findViewById(R.id.btn_history);
        btn_home = findViewById(R.id.btn_home);
        
        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            getSharedPreferences("USER_SESSION", MODE_PRIVATE).edit().clear().apply();
            startActivity(intent);
            overridePendingTransition(0,0);
        });

        btn_home.setOnClickListener(v -> {
            loadFragment(new MainDashboard());
        });

        btn_history.setOnClickListener(v -> {
            loadFragment(new BorrowHistory());
        });

        loadFragment(new MainDashboard());

    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}