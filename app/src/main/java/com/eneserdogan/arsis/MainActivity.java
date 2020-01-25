package com.eneserdogan.arsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText email;
    EditText password;
    Button signup;
    Button login;
    Button forgotPass;
   // String emailPattern = "[a-zA-Z0-9._-]+@erciyes+\\.+edu+\\.+tr+"; //Bu email kalıbına uyan email adreslerine doğrulama maili gönderilir

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signup = findViewById(R.id.btnVerify);
        login = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.btnUserForgottPass);

        progressBar.setVisibility(View.INVISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();


    }
    public void login(View view){
        Intent ıntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(ıntent);
    }
    public void sendVerifyEmail(View view){
        Intent ıntent3=new Intent(MainActivity.this,verifyEmail.class);
        startActivity(ıntent3);

    }

    public void forgotPass(View view){
        Intent ıntent2=new Intent(MainActivity.this,ForgottPasswordActivity.class);
        startActivity(ıntent2);

    }

}
