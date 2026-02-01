package com.example.bankingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText, balanceText;
    private Button transferBtn, logoutBtn, historyBtn;
    private AppDatabase db;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MUST be first after super
        setContentView(R.layout.activity_dashboard);

        // Initialize Room Database
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "banking_db"
        ).allowMainThreadQueries().build();

        // Bind Views (IDs must exist in XML)
        welcomeText = findViewById(R.id.welcomeText);
        balanceText = findViewById(R.id.balanceText);
        transferBtn = findViewById(R.id.transferBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        historyBtn = findViewById(R.id.historyBtn); // optional button

        // Get email from Intent
        email = getIntent().getStringExtra("email");
        Log.d("DashboardActivity", "Received email: " + email);

        // Validate session
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Fetch user data
        User user = db.userDao().getUserByEmail(email);
        if (user != null) {
            welcomeText.setText("Welcome, " + user.getName());
            balanceText.setText("Balance: ₹" + user.getBalance());
        } else {
            Toast.makeText(this, "User not found. Please login again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Transfer button
        transferBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, TransferActivity.class);
            i.putExtra("email", email);
            startActivity(i);
        });

        // Transaction history button
        historyBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, TransactionHistoryActivity.class);
            i.putExtra("email", email);
            startActivity(i);
        });

        // Logout button
        logoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}