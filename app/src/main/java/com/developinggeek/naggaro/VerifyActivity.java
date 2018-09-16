package com.developinggeek.naggaro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VerifyActivity extends AppCompatActivity
{
    private EditText edtUid;
    private Button search,verify;
    private TextView name,aadhar,txtName,txtAadhar;
    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        edtUid = (EditText)findViewById(R.id.verify_edt_uid);
        search = (Button)findViewById(R.id.verify_btn_search);
        verify = (Button)findViewById(R.id.verify_verify);
        name = (TextView)findViewById(R.id.verify_name);
        aadhar = (TextView)findViewById(R.id.verify_aadhar);
        txtAadhar = (TextView)findViewById(R.id.verify_text_aadhar);
        txtName = (TextView)findViewById(R.id.verify_text_name);

        name.setVisibility(View.GONE);
        aadhar.setVisibility(View.GONE);
        txtName.setVisibility(View.GONE);
        txtAadhar.setVisibility(View.GONE);
        verify.setVisibility(View.GONE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String uid = edtUid.getText().toString();
                if(!TextUtils.isEmpty(uid))
                {
                    DatabaseReference user = userDatabase.child(uid);
                    user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name.setVisibility(View.VISIBLE);
                            aadhar.setVisibility(View.VISIBLE);
                            txtName.setVisibility(View.VISIBLE);
                            txtAadhar.setVisibility(View.VISIBLE);
                            verify.setVisibility(View.VISIBLE);

                            aadhar.setText(dataSnapshot.child("id").getValue().toString());
                            name.setText(dataSnapshot.child("name").getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = edtUid.getText().toString();
                DatabaseReference user = userDatabase.child(uid);
                user.child("verified").setValue("true");
            }
        });

    }
}
