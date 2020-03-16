package com.example.accountsmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.accountsmanager.KEY;
import com.example.accountsmanager.R;

public class MenuActivity extends AppCompatActivity {
    private CardView btnAddPartyCustomer;
    private CardView btnPartyTransaction;
    private CardView btnDateWiseReport;
    private CardView btnChangePIN;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        initView();
        clickButton();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentResult = new Intent();
        setResult(RESULT_CANCELED, intentResult);
    }

    private void clickButton() {
        btnAddPartyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AddPartyInformationActivity.class);
                startActivity(intent);
            }
        });

        btnPartyTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AllPartyListActivity.class);
                startActivity(intent);
            }
        });

        btnDateWiseReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, PartyReportActivity.class);
                startActivity(intent);
            }
        });

        btnChangePIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ChangePinActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        btnAddPartyCustomer = findViewById(R.id.btnAddPartyCustomer);
        btnPartyTransaction = findViewById(R.id.btnPartyTransaction);
        btnDateWiseReport = findViewById(R.id.btnDateWiseReport);
        btnChangePIN = findViewById(R.id.btnChangePIN);
    }
}
