package com.example.accountsmanager.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountsmanager.R;
import com.example.accountsmanager.database.DBHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPartyInformationActivity extends AppCompatActivity {
    private ImageView imgBack ;
    private EditText edtEnterName ;
    private EditText edtPhoneNumber;
    private EditText edtDate;
    private EditText edtAddress;
    private Button btnAdd;
    String dateTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_party_information_activity);

        SQLiteDatabase.loadLibs(this);
        initView();

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtDate,myCalendar);
            }
        };

        edtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddPartyInformationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPartyInformationActivity.super.onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtEnterName.getText().toString().isEmpty()
                ||edtPhoneNumber.getText().toString().isEmpty()
                ||edtDate.getText().toString().isEmpty()
                ||edtAddress.getText().toString().isEmpty()){
                    Toast.makeText(AddPartyInformationActivity.this, "Must enter all information!", Toast.LENGTH_SHORT).show();
                }else {
                    DBHelper.getInstance(AddPartyInformationActivity.this).insertNewParty(edtEnterName.getText().toString(),
                            edtPhoneNumber.getText().toString(),
                            edtDate.getText().toString(),
                            edtAddress.getText().toString());
                    Toast.makeText(AddPartyInformationActivity.this, "Add party success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //clickCalender(edtDate);
    }

    private void updateLabel(EditText edtDate, Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(sdf.format(myCalendar.getTime()));
    }



    private void initView() {
         imgBack = findViewById(R.id.imgBack);
         edtEnterName = findViewById(R.id.edtEnterName);
         edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
         edtDate = findViewById(R.id.edtDate);
         edtAddress = findViewById(R.id.edtAddress);
         btnAdd =findViewById(R.id.btnAdd);
    }
}
