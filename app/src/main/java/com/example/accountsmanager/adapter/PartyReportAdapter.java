package com.example.accountsmanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsmanager.R;
import com.example.accountsmanager.model.Transaction;

import java.util.List;

public class PartyReportAdapter extends RecyclerView.Adapter<PartyReportAdapter.PartyReportHolder> {

    private List<Transaction> transactionDataList;
    private LayoutInflater layoutInflater;

    public PartyReportAdapter(Context context, List<Transaction> transactionDataList) {
        layoutInflater = LayoutInflater.from(context);
        this.transactionDataList = transactionDataList;
    }
    @NonNull
    @Override
    public PartyReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.party_report_adapter, parent, false);
        return new PartyReportHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PartyReportHolder holder, int position) {
        Transaction transaction = transactionDataList.get(position);
        holder.txtDateTime.setText(transaction.getTransactionDate()+" "+transaction.getTransactionTime());
        holder.txtCre.setText(transaction.getTransactionCreditAmount());
        holder.txtDe.setText(transaction.getTransactionDebitAmount());
        holder.txtNote.setText(transaction.getTransactionNote());
    }

    @Override
    public int getItemCount() {
        return transactionDataList.size();
    }

    class PartyReportHolder extends RecyclerView.ViewHolder {
        TextView txtDateTime,txtCre,txtDe,txtNote;
        public PartyReportHolder(@NonNull View itemView) {
            super(itemView);
            txtDateTime=itemView.findViewById(R.id.txtDateTime);
            txtCre=itemView.findViewById(R.id.txtCre);
            txtDe=itemView.findViewById(R.id.txtDe);
            txtNote=itemView.findViewById(R.id.txtNote);
        }
    }
}
