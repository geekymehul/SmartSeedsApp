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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText edtId,edtPass;
    private Button login,officerLogin;
    private TextView register;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtId = (EditText)findViewById(R.id.login_edt_id);
        edtPass = (EditText)findViewById(R.id.login_edt_pass);
        register = (TextView)findViewById(R.id.login_btn_register);
        login = (Button)findViewById(R.id.login_btn_login);
        officerLogin = (Button)findViewById(R.id.officer_login);

        mProgress = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(LoginActivity.this , RegisterActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginUser();
            }
        });

    }

    private void loginUser()
    {
        String id = edtId.getText().toString();
        String pass = edtPass.getText().toString();

        if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(pass))
        {
            mProgress.setTitle("logging you in...");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            mAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Could not sign in...", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                    else
                    {
                        mProgress.dismiss();

                        Intent loginIntent = new Intent(LoginActivity.this , HomeActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);

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

