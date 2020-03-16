package com.example.accountsmanager.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsmanager.KEY;
import com.example.accountsmanager.R;
import com.example.accountsmanager.adapter.PartyListAdapter;
import com.example.accountsmanager.database.DBHelper;
import com.example.accountsmanager.model.Party;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AllPartyListActivity extends AppCompatActivity {
    private ImageView imgButtonBack;
    private EditText edtSearch;
    private ImageView imgButtonSearch;
    private TextView txtOverallCredit;
    private TextView txtOverallDebit;
    private RecyclerView rcvPartyList;

    private PartyListAdapter PLAdapter;
    private int REQUEST_CODE_PARTY = 2;

    private List<Party> parties = new ArrayList<>();
    private int totalCre = 0;
    private int totalDe = 0;
    private Party partySave;
    private int positionSave;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_party_list_activity);
        SQLiteDatabase.loadLibs(this);
        initView();

        parties.addAll(DBHelper.getInstance(this).getAllParty());
        PLAdapter = new PartyListAdapter(this, parties);
        rcvPartyList.setLayoutManager(new LinearLayoutManager(this));
        rcvPartyList.setAdapter(PLAdapter);

        imgButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllPartyListActivity.super.onBackPressed();
            }
        });

        imgButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parties.clear();
                parties.addAll(DBHelper.getInstance(AllPartyListActivity.this).searchParty(edtSearch.getText().toString()));
                Toast.makeText(AllPartyListActivity.this, "click" + parties.size(), Toast.LENGTH_SHORT).show();
                PLAdapter.notifyDataSetChanged();
            }
        });

        getAndSetCount();

        PLAdapter.setOnPartyItemClickListener(new PartyListAdapter.OnPartyItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                partySave = parties.get(position);
                positionSave = position;
                Bundle bundle = new Bundle();
                Intent intent = new Intent(AllPartyListActivity.this, PartyDetailActivity.class);
                bundle.putSerializable(KEY.PARTY, partySave);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_PARTY);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void getAndSetCount() {
        totalCre = DBHelper.getInstance(this).totalCre();
        totalDe = DBHelper.getInstance(this).totalDe();
        txtOverallCredit.setText("" + totalCre + " /-");
        txtOverallDebit.setText("" + totalDe + " /-");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PARTY) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                if (data.getBooleanExtra(KEY.DELETE_PARTY, false)) {
                    parties.remove(partySave);
                    PLAdapter.notifyDataSetChanged();
                }
                if (data.getBooleanExtra(KEY.UPDATE_PARTY_ID, false)) {
                    Party partyUpdate = DBHelper.getInstance(this).getOneParty(partySave.getPartyID());
                    parties.set(positionSave, partyUpdate);
                    PLAdapter.notifyItemChanged(positionSave);
                }
                if (data.getBooleanExtra(KEY.UPDATE_COUNT, false)) {
                    getAndSetCount();
                }
            }
        }
    }

    private void initView() {
        imgButtonBack = findViewById(R.id.imgButtonBack);
        edtSearch = findViewById(R.id.edtSearch);
        imgButtonSearch = findViewById(R.id.imgButtonSearch);
        txtOverallCredit = findViewById(R.id.txtOverallCredit);
        txtOverallDebit = findViewById(R.id.txtOverallDebit);
        rcvPartyList = findViewById(R.id.rcvPartyList);
    }
}
