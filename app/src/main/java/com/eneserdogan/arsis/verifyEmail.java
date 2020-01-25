package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class verifyEmail extends AppCompatActivity {
    EditText email;
    EditText password;
    String emailPattern = "[a-zA-Z0-9._-]+@erciyes+\\.+edu+\\.+tr+";//Bu email kalıbına uyan email adreslerine doğrulama maili gönderilir
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        firebaseAuth = FirebaseAuth.getInstance();

    }
    public void btnSend(View view){
        if(email.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Üniversite Mail Adresi Girin",Toast.LENGTH_SHORT).show();
        }else {
            if (email.getText().toString().trim().matches(emailPattern)) {
                //progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(verifyEmail.this, "\n" + "Başarıyla kayıt olundu. Lütfen doğrulama için e-postanızı kontrol edin",
                                                                Toast.LENGTH_LONG).show();
                                                        email.setText("");
                                                        password.setText("");
                                                    }else{
                                                        Toast.makeText(verifyEmail.this,  task.getException().getMessage(),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }else {
                                    Toast.makeText(verifyEmail.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            } else {
                Toast.makeText(getApplicationContext()," Lütfen @erciyes.edu Uzantılı Email Girin ", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
