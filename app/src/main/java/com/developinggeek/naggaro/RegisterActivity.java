package com.developinggeek.naggaro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText edtId,edtPass,edtName;
    private TextView login;
    private Button register;
    private ProgressDialog mProgress;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        edtId = (EditText)findViewById(R.id.register_edt_id);
        edtPass = (EditText)findViewById(R.id.register_edt_pass);
        register = (Button) findViewById(R.id.register_btn_register);
        login = (TextView) findViewById(R.id.register_btn_login);
        edtName = (EditText)findViewById(R.id.register_edt_name);

        mProgress = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this , LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser()
    {
        final String id = edtId.getText().toString();
        final String name = edtName.getText().toString();
        final String pass = edtPass.getText().toString();

        if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(name))
        {
            mProgress.setTitle("Creating account...");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        String userId = mAuth.getCurrentUser().getUid();

                        DatabaseReference user = usersDatabase.child(userId);
                        user.child("name").setValue(name);
                        user.child("id").setValue(id);
                        user.child("uid").setValue(userId);

                        mProgress.dismiss();

                        Intent mainIntent = new Intent(RegisterActivity.this , HomeActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

}
