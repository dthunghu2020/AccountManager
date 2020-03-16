package com.example.accountsmanager.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountsmanager.R;
import com.example.accountsmanager.database.DBHelper;
import com.example.accountsmanager.model.Login;

import net.sqlcipher.database.SQLiteDatabase;

public class ChangePinActivity extends AppCompatActivity {

    private TextView txtUserName;
    private EditText edtOldPin,edtNewPin,edtConfirmNewPin;
    private Button buttonDone;

    private Login login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pin_activity);

        SQLiteDatabase.loadLibs(this);

        initView();

        login = DBHelper.getInstance(ChangePinActivity.this).getLoginData();

        txtUserName.setText(login.getLoginName());

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtOldPin.getText().toString().isEmpty()
                    ||edtOldPin.getText().toString().isEmpty()
                    ||edtNewPin.getText().toString().isEmpty()
                    ||edtConfirmNewPin.getText().toString().isEmpty()){
                    Toast.makeText(ChangePinActivity.this, "Must enter all information", Toast.LENGTH_SHORT).show();
                }else {
                    if (!edtOldPin.getText().toString().equals(login.getPIN())){
                        Toast.makeText(ChangePinActivity.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                    }else if (!edtNewPin.getText().toString().equals(edtConfirmNewPin.getText().toString())){
                        Toast.makeText(ChangePinActivity.this, "Confirm New Pin isn't the same", Toast.LENGTH_SHORT).show();
                    }else {
                        DBHelper.getInstance(ChangePinActivity.this).updatePIN(login.getLoginName(),edtNewPin.getText().toString());
                        Toast.makeText(ChangePinActivity.this, "Change PIN Success!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });


    }

    private void initView() {
        txtUserName = findViewById(R.id.txtUserName);
        edtOldPin = findViewById(R.id.edtOldPin);
        edtNewPin = findViewById(R.id.edtNewPin);
        edtConfirmNewPin = findViewById(R.id.edtConfirmNewPin);
        buttonDone = findViewById(R.id.buttonDone);
    }
}
