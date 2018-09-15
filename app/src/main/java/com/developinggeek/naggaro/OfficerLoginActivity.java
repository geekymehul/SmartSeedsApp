package com.developinggeek.naggaro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OfficerLoginActivity extends AppCompatActivity
{
    private EditText edtId,edtPass;
    private Button btnLogin;
    private DatabaseReference officers;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_login);

        mAuth = FirebaseAuth.getInstance();

        edtId = (EditText)findViewById(R.id.officer_edt_id);
        edtPass = (EditText)findViewById(R.id.officer_edt_pass);
        btnLogin = (Button)findViewById(R.id.officer_btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final String id = edtId.getText().toString();
                final String pass = edtPass.getText().toString();

                mAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent mainIntent = new Intent(OfficerLoginActivity.this , VerifyActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }
                        else
                        {
                            Toast.makeText(OfficerLoginActivity.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
