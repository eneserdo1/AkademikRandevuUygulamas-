package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AkademisyenActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String emailKontrol;
    String kişi = "";
    Boolean torf = false;
    protected void onResume() {
        super.onResume();
        this.torf = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akademisyen);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        emailKontrol=firebaseUser.getEmail();
        emailKontrol=(String) firebaseUser.getEmail();
        CollectionReference collectionReference=firebaseFirestore.collection("hocalar");
        collectionReference.whereEqualTo("email",emailKontrol).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot document = task.getResult();
                    if(document != null){
                        List custom = document.getDocuments();
                        if (custom.size() == 0){
                            torf = true;
                        }
                    }
                }
            }
        });

    }
    public void bilgiEkle(View view){
        if (this.torf==true){
            Intent ıntent= new Intent(AkademisyenActivity.this,ProfileActivityHoca.class);
            startActivity(ıntent);
        }else{
            Toast.makeText(getApplicationContext(),"Kullanıcı Bilgileri Kayıtlı",Toast.LENGTH_LONG).show();
        }


    }
    public void randevuListele(View view){
        if (this.torf==false){
            Intent intent2=new Intent(AkademisyenActivity.this,randevuListeleHoca.class);
            startActivity(intent2);
        }else {
            Toast.makeText(getApplicationContext(),"Kullanıcı Bilgilerinizi Ekledikten Sonra Tekrar Deneyiniz",Toast.LENGTH_LONG).show();
        }

    }


}
