package com.developinggeek.naggaro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLedgerActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText edtName,edtId,edtAddr,edtArea,edtCrop;
    private Button submit;
    private DatabaseReference ledgerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ledger);

        mAuth = FirebaseAuth.getInstance();
        ledgerDatabase = FirebaseDatabase.getInstance().getReference().child("Legder");

        edtName = (EditText)findViewById(R.id.ledger_edt_name);
        edtId = (EditText)findViewById(R.id.ledger_edt_aadhar);
        edtAddr = (EditText)findViewById(R.id.ledger_edt_address);
        edtArea = (EditText)findViewById(R.id.ledger_edt_area);
        edtCrop = (EditText)findViewById(R.id.ledger_edt_crop);
        submit = (Button) findViewById(R.id.ledger_btn_add);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecord();
            }
        });
    }

    private void addRecord()
    {
        String name = edtName.getText().toString();
        String aadhar = edtId.getText().toString();
        String address = edtAddr.getText().toString();
        String area = edtArea.getText().toString();
        String crop = edtCrop.getText().toString();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(aadhar) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(area) &&
                !TextUtils.isEmpty(crop))
        {
            DatabaseReference add = ledgerDatabase.child(mAuth.getCurrentUser().getUid());
            add.child("name").setValue(name);
            add.child("aadhar").setValue(aadhar);
            add.child("address").setValue(address);
            add.child("area").setValue(area);
            add.child("crop").setValue(crop);
        }
        else
        {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }
    }

}
