package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPass;
    Button userLogin;
    String emailPattern = "[0-9]+@erciyes+\\.+edu+\\.+tr+";//Bu kalıba uyan doğrulanmış email adreslerini öğrenci menüsüne yönlendirecek
                                                           //uymayanları akademisyen menüsüne yönlendirecek

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.etUserEmail);
        userPass = findViewById(R.id.etUserPass);
        userLogin = findViewById(R.id.btnUserLogin);

        progressBar.setVisibility(View.INVISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(View view){
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                userPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                if (userEmail.getText().toString().trim().matches(emailPattern)){
                                    startActivity(new Intent(LoginActivity.this,studentHomeActivity.class));
                                }else {
                                    startActivity(new Intent(LoginActivity.this,AkademisyenActivity.class));
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Please verify your email address"
                                        , Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage()
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }


}



