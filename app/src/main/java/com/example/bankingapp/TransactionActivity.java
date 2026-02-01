package com.example.bankingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    TextView noTransactionsText;
    ListView transactionListView;

    AppDatabase db;
    String userEmail;
    ArrayList<String> transactionDetails;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SET LAYOUT (only once)
        setContentView(R.layout.activity_transaction);

        // Bind views
        noTransactionsText = findViewById(R.id.noTransactionsText);
        transactionListView = findViewById(R.id.transactionListView);

        // Database
        db = AppDatabase.getInstance(this);

        // Get user email
        userEmail = getIntent().getStringExtra("email");

        transactionDetails = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                transactionDetails
        );
        transactionListView.setAdapter(adapter);

        loadTransactions();
    }

    private void loadTransactions() {
        List<Transaction> transactions =
                db.transactionDao().getTransactionsForUser(userEmail);

        if (transactions == null || transactions.isEmpty()) {
            noTransactionsText.setVisibility(TextView.VISIBLE);
            transactionListView.setVisibility(ListView.GONE);
        } else {
            noTransactionsText.setVisibility(TextView.GONE);
            transactionListView.setVisibility(ListView.VISIBLE);

            transactionDetails.clear();

            for (Transaction t : transactions) {
                boolean isSender = t.getFromEmail().equals(userEmail);
                String detail =
                        (isSender ? "Sent to: " : "Received from: ")
                                + (isSender ? t.getToEmail() : t.getFromEmail())
                                + "\nAmount: ₹" + t.getAmount()
                                + "\nDate: " + t.getTimestamp();

                transactionDetails.add(detail);
            }
            adapter.notifyDataSetChanged();
        }
    }
}