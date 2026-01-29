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

        // ID must match XML
        recyclerView = findViewById(R.id.rvTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TEMP test data
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction(
                "user1@gmail.com",
                "user2@gmail.com",
                500,
                "30 Jan 2026"
        ));

        adapter = new TransactionAdapter(
                transactionList,
                "user1@gmail.com"   // logged-in user
        );

        recyclerView.setAdapter(adapter);
    }
}