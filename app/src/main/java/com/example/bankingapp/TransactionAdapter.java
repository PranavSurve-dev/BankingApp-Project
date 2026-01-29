package com.example.bankingapp;   // ⚠️ CHANGE this to your actual package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter
        extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<Transaction> list;
    private final String currentUserEmail;

    // =======================
    // CONSTRUCTOR
    // =======================
    public TransactionAdapter(List<Transaction> list, String currentUserEmail) {
        this.list = list;
        this.currentUserEmail = currentUserEmail;
    }

    // =======================
    // CREATE VIEW HOLDER
    // =======================
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);

        return new ViewHolder(view);
    }

    // =======================
    // BIND DATA
    // =======================
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        Transaction t = list.get(position);

        boolean isSent = currentUserEmail != null
                && currentUserEmail.equals(t.getFromEmail());

        if (isSent) {
            holder.txtType.setText("SENT ₹" + t.getAmount());
            holder.txtUser.setText("To: " + t.getToEmail());
        } else {
            holder.txtType.setText("RECEIVED ₹" + t.getAmount());
            holder.txtUser.setText("From: " + t.getFromEmail());
        }

        holder.txtDate.setText(t.getTimestamp());
    }

    // =======================
    // ITEM COUNT
    // =======================
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    // =======================
    // VIEW HOLDER CLASS
    // =======================
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        TextView txtUser;
        TextView txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtType = itemView.findViewById(R.id.txtType);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
