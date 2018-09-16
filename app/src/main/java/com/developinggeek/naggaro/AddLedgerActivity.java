package com.developinggeek.naggaro;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddLedgerActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText edtName,edtId,edtArea,edtCrop;
    private Button submit;
    private DatabaseReference ledgerDatabase,cropDatabase;
    private double supply = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ledger);

        mAuth = FirebaseAuth.getInstance();
        ledgerDatabase = FirebaseDatabase.getInstance().getReference().child("Legder");
        cropDatabase = FirebaseDatabase.getInstance().getReference().child("Crops").child("Rice");

        edtName = (EditText)findViewById(R.id.ledger_edt_name);
        edtId = (EditText)findViewById(R.id.ledger_edt_aadhar);
        edtArea = (EditText)findViewById(R.id.ledger_edt_area);
        edtCrop = (EditText)findViewById(R.id.ledger_edt_crop);
        submit = (Button) findViewById(R.id.ledger_btn_add);

        cropDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                supply = Float.parseFloat(dataSnapshot.child("supply").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        String area = edtArea.getText().toString();
        String crop = edtCrop.getText().toString();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(aadhar) && !TextUtils.isEmpty(area) &&
                !TextUtils.isEmpty(crop))
        {
            supply = supply + (Double.parseDouble(area)*3500);

            DatabaseReference add = ledgerDatabase.child(mAuth.getCurrentUser().getUid());
            add.child("name").setValue(name);
            add.child("aadhar").setValue(aadhar);
            add.child("area").setValue(area);
            add.child("crop").setValue(crop);
        }
        else
        {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }

        cropDatabase.child("supply").setValue(supply);

    }

}
