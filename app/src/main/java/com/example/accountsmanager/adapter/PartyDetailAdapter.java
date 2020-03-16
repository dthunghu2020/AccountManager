package com.example.accountsmanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsmanager.R;
import com.example.accountsmanager.model.Transaction;

import java.util.List;

public class PartyDetailAdapter extends RecyclerView.Adapter<PartyDetailAdapter.PartyDetailsViewHolder> {

    private List<Transaction> transactionDataList;
    private LayoutInflater layoutInflater;

    private OnDetailItemClickListener onDetailItemClickListener;

    public PartyDetailAdapter(Context context, List<Transaction> transactionDataList) {
        layoutInflater = LayoutInflater.from(context);
        this.transactionDataList = transactionDataList;
    }

    @NonNull
    @Override
    public PartyDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.party_detals_adapter, parent, false);
        return new PartyDetailsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PartyDetailsViewHolder holder, int position) {
        Transaction transactionData = transactionDataList.get(position);
        holder.txtDate.setText(transactionData.getTransactionDate()+" "+transactionData.getTransactionTime());
        holder.txtCredit.setText(transactionData.getTransactionCreditAmount());
        holder.txtDebit.setText(transactionData.getTransactionDebitAmount());
        holder.txtNote.setText(transactionData.getTransactionNote());
        holder.imgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetailItemClickListener.OnItemClicked(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionDataList.size();
    }

    class PartyDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDate;
        private TextView txtCredit;
        private TextView txtDebit;
        private TextView txtNote;
        private ImageView imgButtonEdit;

        public PartyDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCredit = itemView.findViewById(R.id.txtCredit);
            txtDebit = itemView.findViewById(R.id.txtDebit);
            txtNote = itemView.findViewById(R.id.txtNote);
            imgButtonEdit = itemView.findViewById(R.id.imgButtonEdit);
        }
    }
    public void setOnPartyItemClickListener(OnDetailItemClickListener onDetailItemClickListener){
        this.onDetailItemClickListener = onDetailItemClickListener;
    }

    public interface OnDetailItemClickListener{
        void OnItemClicked(int position);
    }
}
