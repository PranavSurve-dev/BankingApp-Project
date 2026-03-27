package com.example.bankingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TransactionAdapter adapter;
    List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);


        recyclerView = findViewById(R.id.rvTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        TransactionDao dao = db.transactionDao();

// Get current logged-in user email (IMPORTANT)
        String userEmail = "user1@gmail.com"; // replace with dynamic email

        transactionList = dao.getTransactionsForUser(userEmail);

        adapter = new TransactionAdapter(transactionList, "user1@gmail.com");
        recyclerView.setAdapter(adapter);
    }

}

//
//package com.example.bankingapp;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class TransactionHistoryActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private TransactionAdapter adapter;
//    private List<Transaction> transactionList;
//    private TextView tvEmpty;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_transaction_history);
//
//        recyclerView = findViewById(R.id.rvTransactions);
//        tvEmpty = findViewById(R.id.tvEmpty); // Add this TextView in your layout for empty state
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Get current logged-in user email dynamically
//        String userEmail = "user1@gmail.com"; // Replace this with dynamic session email later
//
//        AppDatabase db = AppDatabase.getInstance(this);
//        TransactionDao dao = db.transactionDao();
//
//        // Run database query in background thread
//        new Thread(() -> {
//            transactionList = dao.getTransactionsForUser(userEmail);
//
//            runOnUiThread(() -> {
//                if (transactionList != null && !transactionList.isEmpty()) {
//                    adapter = new TransactionAdapter(transactionList, userEmail);
//                    recyclerView.setAdapter(adapter);
//                    tvEmpty.setVisibility(View.GONE);
//                } else {
//                    recyclerView.setVisibility(View.GONE);
//                    tvEmpty.setVisibility(View.VISIBLE);
//                }
//            });
//        }).start();
//    }
//}

