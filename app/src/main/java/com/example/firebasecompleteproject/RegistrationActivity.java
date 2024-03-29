package com.example.firebasecompleteproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private Button btnSingUp;
    private Button btnSingIn;

    //Firebase
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        email=findViewById(R.id.email_Singup);
        password=findViewById(R.id.Password_Singup);

        btnSingUp=findViewById(R.id.btn_singUp_Up);
        btnSingIn=findViewById(R.id.btn_singUp_In);


        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail=email.getText().toString().trim();
                String mPassword=password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field...");
                    return;
                }
                if (TextUtils.isEmpty(mPassword)){
                    password.setError("Required Field...");
                    return;
                }
                mDialog.setMessage("Processing....");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Complete",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(),"Registration Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}
