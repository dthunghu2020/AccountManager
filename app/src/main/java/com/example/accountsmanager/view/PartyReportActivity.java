package com.example.accountsmanager.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsmanager.R;
import com.example.accountsmanager.adapter.PartyReportAdapter;
import com.example.accountsmanager.database.DBHelper;
import com.example.accountsmanager.model.Transaction;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.PropertyPermission;

public class PartyReportActivity extends AppCompatActivity {

    private ImageView imgBack;
    private EditText edtReportDate;
    private Button buttonShow;
    private RecyclerView rcvPartyReport;
    private TextView txtTCA,txtTDA;

    private List<Transaction> transactions = new ArrayList<>();
    private PartyReportAdapter PRAdapter;

    private int countCre = 0;
    private int countDe = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_report_activity);

        SQLiteDatabase.loadLibs(this);

        initView();

        txtTCA.setText(""+countCre+" /-");
    txtTDA .setText(""+countDe+" /-");

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtReportDate,myCalendar);
            }
        };

        edtReportDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PartyReportActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        PRAdapter = new PartyReportAdapter(this,transactions);
        rcvPartyReport.setLayoutManager(new LinearLayoutManager(this));
        rcvPartyReport.setAdapter(PRAdapter);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PartyReportActivity.super.onBackPressed();
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPartyReport();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void reloadPartyReport() {
        transactions.clear();
        transactions.addAll(DBHelper.getInstance(PartyReportActivity.this).getAllReportTransaction(edtReportDate.getText().toString()));
        countCre = DBHelper.getInstance(PartyReportActivity.this).dayTransactionCredit(edtReportDate.getText().toString());
        countDe = DBHelper.getInstance(PartyReportActivity.this).dayTransactionDebit(edtReportDate.getText().toString());
        txtTCA.setText(""+countCre+" /-");
        txtTDA .setText(""+countDe+" /-");

        if(transactions == null){
            Toast.makeText(this, "No transaction math", Toast.LENGTH_SHORT).show();
        }
        PRAdapter.notifyDataSetChanged();
    }

    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        edtReportDate = findViewById(R.id.edtReportDate);
        buttonShow = findViewById(R.id.buttonShow);
        rcvPartyReport = findViewById(R.id.rcvPartyReport);
        txtTCA = findViewById(R.id.txtTCA);
        txtTDA = findViewById(R.id.txtTDA);
    }
}
