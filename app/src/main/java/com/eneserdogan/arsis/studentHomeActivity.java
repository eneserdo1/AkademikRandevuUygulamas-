package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class studentHomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String emailKontrol;
    String kişi = "";
    Boolean torf = false;

    /*@Override
    protected void onResume() {
        super.onResume();
        this.torf = false;
    }*///Listview verilerini güncellemek

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        emailKontrol=(String) firebaseUser.getEmail();
        CollectionReference collectionReference=firebaseFirestore.collection("users");
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
    public void BilgiEkle(View view){
            if (this.torf == false){
                Toast.makeText(getApplicationContext(),"Kullanıcı Kayıtlı",Toast.LENGTH_LONG).show();
            }else{
                Intent ıntent=new Intent(studentHomeActivity.this,ProfileActivity.class);
                startActivity(ıntent);
            }
    }

    public void RandevuAl(View view){
        if (this.torf == false){
            Intent ıntent2=new Intent(studentHomeActivity.this,RandevuBolum.class);
            startActivity(ıntent2);

        }else {
            Toast.makeText(getApplicationContext(),"Kullanıcı Bilgilerinizi Ekledikten Sonra Randevu Alınız",Toast.LENGTH_LONG).show();
        }


    }
    public void RandevuGörüntüle(View view){
        Intent intent3=new Intent(studentHomeActivity.this,RandevuListeleOgrenci.class);
        startActivity(intent3);
    }
}
