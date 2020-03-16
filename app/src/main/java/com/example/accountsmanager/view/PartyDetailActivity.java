package com.example.accountsmanager.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsmanager.KEY;
import com.example.accountsmanager.R;
import com.example.accountsmanager.adapter.PartyDetailAdapter;
import com.example.accountsmanager.database.DBHelper;
import com.example.accountsmanager.model.Party;
import com.example.accountsmanager.model.Transaction;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PartyDetailActivity extends AppCompatActivity {

    private ImageView imgEditParty, imgDeleteParty;
    private TextView txtPartyName, txtPartyNumber, txtPartyAddress, txtOpeningDate, txtTotalCerDe, txtBalanceAmount;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText edtEnterAmount, edtDate, edtTime, edtNote;
    private Button btnAddTransaction;
    private RecyclerView rcvTransactionList;

    private PartyDetailAdapter PDAdapter;
    private List<Transaction> transactions;
    private Transaction transactionSave;

    private Party party = null;
    private Boolean isCredit = true;
    private int totalCer = 0;
    private int totalDe = 0;
    private int balanceAmount = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_details_activity);

        SQLiteDatabase.loadLibs(this);


        initView();

        final Calendar myCalendar = Calendar.getInstance();

        Intent intent = getIntent();
        party = (Party) intent.getSerializableExtra(KEY.PARTY);
        assert party != null;
        txtPartyName.setText(party.getPartyName());
        txtPartyNumber.setText("Phone: " + party.getPartyPhone());
        txtPartyAddress.setText("Address: " + party.getPartyAddress());
        txtOpeningDate.setText("Opening Date: " + party.getPartyOpeningDate());

        totalCer = DBHelper.getInstance(PartyDetailActivity.this).totalCre();
        totalDe = DBHelper.getInstance(PartyDetailActivity.this).totalDe();
        balanceAmount = DBHelper.getInstance(PartyDetailActivity.this).partyTransactionCount(party.getPartyID());

        loadDataTransaction();

        insertDateTime(myCalendar);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtDate, myCalendar);
            }

        };

        edtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PartyDetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //todo


        transactions = DBHelper.getInstance(this).getAllPartyTransaction(party.getPartyID());
        PDAdapter = new PartyDetailAdapter(this, transactions);
        rcvTransactionList.setLayoutManager(new LinearLayoutManager(this));
        rcvTransactionList.setAdapter(PDAdapter);

        imgEditParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditPartyDialog();
            }
        });

        imgDeleteParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeletePartyDialog();
            }
        });

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEnterAmount.getText().toString().isEmpty()
                        || edtDate.getText().toString().isEmpty()
                        || edtTime.getText().toString().isEmpty()
                        || edtNote.getText().toString().isEmpty()) {
                    Toast.makeText(PartyDetailActivity.this, "Must enter all transaction information!", Toast.LENGTH_SHORT).show();
                } else {
                    String credit = "0";
                    String debit = "0";
                    if (isCredit) {
                        credit = edtEnterAmount.getText().toString();
                        totalCer += Integer.parseInt(credit);
                        balanceAmount += Integer.parseInt(credit);
                    } else {
                        debit = edtEnterAmount.getText().toString();
                        totalDe += Integer.parseInt(debit);
                        balanceAmount -= Integer.parseInt(debit);
                    }
                    DBHelper.getInstance(PartyDetailActivity.this).insertNewTransaction(party.getPartyID(),
                            edtDate.getText().toString(),
                            edtTime.getText().toString(),
                            credit, debit,
                            edtNote.getText().toString());
                    loadDataTransaction();
                    reloadTransaction();
                    Toast.makeText(PartyDetailActivity.this, "Add transaction success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        PDAdapter.setOnPartyItemClickListener(new PartyDetailAdapter.OnDetailItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                openEditTransaction(position);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadDataTransaction() {
        txtTotalCerDe.setText("Total Credit = " + totalCer + ", Total Debit = " + totalDe);
        txtBalanceAmount.setText("Balance Amount: " + balanceAmount + " /-");
    }

    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    private void insertDateTime(Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(sdf.format(myCalendar.getTime()));

        String myFormat2 = "HH:mm";
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
        edtTime.setText(sdf2.format(myCalendar.getTime()));
    }

    private void reloadTransaction() {
        transactions.clear();
        transactions.addAll(DBHelper.getInstance(this).getAllPartyTransaction(party.getPartyID()));
        PDAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void openDeletePartyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_party_dialog);
        final TextView txtDeleteParty = dialog.findViewById(R.id.txtDeleteParty);
        final TextView txtYes = dialog.findViewById(R.id.txtYes);
        final TextView txtNo = dialog.findViewById(R.id.txtNo);

        txtDeleteParty.setText("Do You Want To Delete " + party.getPartyName() + " Permanently ?");

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transactions != null) {
                    DBHelper.getInstance(PartyDetailActivity.this).deleteAllTransaction(party.getPartyID());
                }
                DBHelper.getInstance(PartyDetailActivity.this).deleteParty(party.getPartyID());
                Intent intentResult = new Intent();
                intentResult.putExtra(KEY.DELETE_PARTY, true);
                setResult(RESULT_OK, intentResult);
                Toast.makeText(PartyDetailActivity.this, "Delete Success!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openEditPartyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_party_dialog);

        final EditText edtEditName = dialog.findViewById(R.id.edtEditName);
        final EditText edtEditPhone = dialog.findViewById(R.id.edtEditPhone);
        final EditText edtEditDate = dialog.findViewById(R.id.edtEditDate);
        final EditText edtEditAddress = dialog.findViewById(R.id.edtEditAddress);
        Button btnEditParty = dialog.findViewById(R.id.btnEditParty);

        edtEditName.setText(party.getPartyName());
        edtEditPhone.setText(party.getPartyPhone());
        edtEditDate.setText(party.getPartyOpeningDate());
        edtEditAddress.setText(party.getPartyAddress());

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtEditDate, myCalendar);
            }
        };

        edtEditDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PartyDetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnEditParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEditName.getText().toString().isEmpty()
                        || edtEditPhone.getText().toString().isEmpty()
                        || edtEditDate.getText().toString().isEmpty()
                        || edtEditAddress.getText().toString().isEmpty()) {
                    Toast.makeText(PartyDetailActivity.this, "Must enter all information!", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper.getInstance(PartyDetailActivity.this)
                            .updateParty(party.getPartyID(),
                                    edtEditName.getText().toString(),
                                    edtEditPhone.getText().toString(),
                                    edtEditDate.getText().toString(),
                                    edtEditAddress.getText().toString());
                    txtPartyName.setText(edtEditName.getText().toString());
                    txtPartyNumber.setText(edtEditPhone.getText().toString());
                    txtOpeningDate.setText(edtEditDate.getText().toString());
                    txtPartyAddress.setText(edtEditAddress.getText().toString());
                    Intent intentResult = new Intent();
                    intentResult.putExtra(KEY.UPDATE_PARTY_ID, true);
                    setResult(RESULT_OK, intentResult);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void openEditTransaction(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_transaction_dialog);

        final EditText edtEditCreditAmount = dialog.findViewById(R.id.edtEditCreditAmount);
        final EditText edtEditDebitAmount = dialog.findViewById(R.id.edtEditDebitAmount);
        final EditText edtEditDate = dialog.findViewById(R.id.edtEditDate);
        final EditText edtEditTime = dialog.findViewById(R.id.edtEditTime);
        final EditText edtEditNote = dialog.findViewById(R.id.edtEditNote);
        Button btnEditTransaction = dialog.findViewById(R.id.btnEditTransaction);
        Button btnDeleteTransaction = dialog.findViewById(R.id.btnDeleteTransaction);

        transactionSave = transactions.get(position);

        edtEditCreditAmount.setText(transactionSave.getTransactionCreditAmount());
        edtEditDebitAmount.setText(transactionSave.getTransactionDebitAmount());
        edtEditDate.setText(transactionSave.getTransactionDate());
        edtEditTime.setText(transactionSave.getTransactionTime());
        edtEditNote.setText(transactionSave.getTransactionNote());

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtEditDate, myCalendar);
            }
        };

        edtEditDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PartyDetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnEditTransaction.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (edtEditCreditAmount.getText().toString().isEmpty()
                        || edtEditDebitAmount.getText().toString().isEmpty()
                        || edtEditDate.getText().toString().isEmpty()
                        || edtEditTime.getText().toString().isEmpty()
                        || edtEditNote.getText().toString().isEmpty()) {
                    Toast.makeText(PartyDetailActivity.this, "Must enter all information!", Toast.LENGTH_SHORT).show();
                } else {
                    totalCer -= Integer.parseInt(transactionSave.getTransactionCreditAmount());
                    totalDe -= Integer.parseInt(transactionSave.getTransactionDebitAmount());
                    balanceAmount -= Integer.parseInt(transactionSave.getTransactionCreditAmount());
                    balanceAmount += Integer.parseInt(transactionSave.getTransactionDebitAmount());
                    DBHelper.getInstance(PartyDetailActivity.this).updateTransaction(
                            transactionSave.getTransactionId(),
                            edtEditDate.getText().toString(),
                            edtEditTime.getText().toString(),
                            edtEditCreditAmount.getText().toString(),
                            edtEditDebitAmount.getText().toString(),
                            edtEditNote.getText().toString());
                    Transaction transactionUpdate = DBHelper.getInstance(PartyDetailActivity.this).getOneTransaction(transactionSave.getTransactionId());
                    totalCer += Integer.parseInt(transactionUpdate.getTransactionCreditAmount());
                    totalDe += Integer.parseInt(transactionUpdate.getTransactionDebitAmount());
                    balanceAmount += Integer.parseInt(transactionUpdate.getTransactionCreditAmount());
                    balanceAmount -= Integer.parseInt(transactionUpdate.getTransactionDebitAmount());
                    loadDataTransaction();
                    transactions.set(position, transactionUpdate);
                    PDAdapter.notifyItemChanged(position);
                    Intent intentResult = new Intent();
                    intentResult.putExtra(KEY.UPDATE_COUNT, true);
                    setResult(RESULT_OK, intentResult);
                    dialog.dismiss();
                    Toast.makeText(PartyDetailActivity.this, "Update Transaction Success!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDeleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(PartyDetailActivity.this).deleteOneTransaction(transactionSave.getTransactionId());
                transactions.remove(transactionSave);
                PDAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(PartyDetailActivity.this, "Delete Transaction!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        if (radioButton.getText().equals("Credit (+)")) {
            isCredit = true;
        } else if (radioButton.getText().equals("Debit (-)")) {
            isCredit = false;
        }
    }

    private void initView() {
        imgEditParty = findViewById(R.id.imgEditParty);
        imgDeleteParty = findViewById(R.id.imgDeleteParty);
        txtPartyName = findViewById(R.id.txtPartyName);
        txtPartyNumber = findViewById(R.id.txtPartyNumber);
        txtOpeningDate = findViewById(R.id.txtOpeningDate);
        txtPartyAddress = findViewById(R.id.txtPartyAddress);
        txtTotalCerDe = findViewById(R.id.txtTotalCerDe);
        txtBalanceAmount = findViewById(R.id.txtBalanceAmount);
        radioGroup = findViewById(R.id.radioGroup);
        edtEnterAmount = findViewById(R.id.edtEnterAmount);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
        edtNote = findViewById(R.id.edtNote);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        rcvTransactionList = findViewById(R.id.rcvTransactionList);
    }
}
