package com.example.accountsmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountsmanager.KEY;
import com.example.accountsmanager.R;
import com.example.accountsmanager.database.DBHelper;
import com.example.accountsmanager.model.Login;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEnterPin;
    private EditText edtConfirmPin;
    private EditText edtEnterText;
    private TextView txtConFirmPin;
    private TextView txtEnterText;
    private Button buttonDone;
    private Login login = null;
    private int REQUEST_CODE_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        SQLiteDatabase.loadLibs(this);

        login = DBHelper.getInstance(this).getLoginData();

        initView();
        if(login !=null){
            invisibleView();
        }
        buttonClick();
    }

    private void buttonClick() {

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login != null) {
                    if(edtEnterPin.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this, "Must enter PIN", Toast.LENGTH_SHORT).show();
                    }else {
                        if(edtEnterPin.getText().toString().equals(login.getPIN())){
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    if (edtEnterPin.getText().toString().isEmpty()
                            || edtConfirmPin.getText().toString().isEmpty()
                            || edtEnterText.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Must Enter All Information", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!edtEnterPin.getText().toString().equals(edtConfirmPin.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "PIN isn't the same", Toast.LENGTH_SHORT).show();
                        } else {
                            DBHelper.getInstance(LoginActivity.this).insertLoginData(edtEnterPin.getText().toString(), edtEnterText.getText().toString());
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivityForResult(intent,REQUEST_CODE_LOGIN);
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_CANCELED) {
                invisibleView();
            }
            }
        }

    private void invisibleView() {
        txtConFirmPin.setVisibility(View.GONE);
        txtEnterText.setVisibility(View.GONE);
        edtConfirmPin.setVisibility(View.GONE);
        edtEnterText.setVisibility(View.GONE);
    }
    private void initView() {
        edtEnterPin = findViewById(R.id.edtEnterPin);
        edtConfirmPin = findViewById(R.id.edtConfirmPin);
        edtEnterText = findViewById(R.id.edtEnterText);
        buttonDone = findViewById(R.id.buttonDone);
        txtConFirmPin = findViewById(R.id.txtConFirmPin);
        txtEnterText = findViewById(R.id.txtEnterText);
    }
}
