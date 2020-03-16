package com.example.accountsmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsmanager.R;
import com.example.accountsmanager.model.Party;

import java.util.List;

public class PartyListAdapter extends RecyclerView.Adapter<PartyListAdapter.PartyListHolder> {

    private List<Party> parties;
    private LayoutInflater layoutInflater;
    private OnPartyItemClickListener onPartyItemClickListener;

    public PartyListAdapter(Context context, List<Party> parties) {
        layoutInflater = LayoutInflater.from(context);
        this.parties = parties;
    }

    @NonNull
    @Override
    public PartyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.party_list_adapter, parent, false);
        return new PartyListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PartyListHolder holder, final int position) {
        Party party = parties.get(position);
        holder.txtName.setText(party.getPartyName());
        holder.txtDate.setText(party.getPartyOpeningDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPartyItemClickListener.OnItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    class PartyListHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDate;

        public PartyListHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }

    public void setOnPartyItemClickListener(OnPartyItemClickListener onPartyItemClickListener){
        this.onPartyItemClickListener = onPartyItemClickListener;
    }

    public interface OnPartyItemClickListener{
        void OnItemClicked(int position);
    }
}
