package com.example.bankingapp;


import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        recyclerView = findViewById(R.id.rvTransactions);
        tvEmpty = findViewById(R.id.tvEmpty); // Add this TextView in your layout for empty state
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get current logged-in user email dynamically
//        String userEmail = "user1@gmail.com"; // Replace this with dynamic session email later
        String userEmail = getIntent().getStringExtra("email");
        AppDatabase db = AppDatabase.getInstance(this);
        TransactionDao dao = db.transactionDao();

        // Run database query in background thread
        new Thread(() -> {
            transactionList = dao.getTransactionsForUser(userEmail);

            runOnUiThread(() -> {

                Log.d("CHECK", "Transactions size: " +
                        (transactionList == null ? "NULL" : transactionList.size()));

                if (transactionList != null && !transactionList.isEmpty()) {
                    adapter = new TransactionAdapter(transactionList, userEmail);
                    recyclerView.setAdapter(adapter);
                    tvEmpty.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            });
        }).start();
    }
}

