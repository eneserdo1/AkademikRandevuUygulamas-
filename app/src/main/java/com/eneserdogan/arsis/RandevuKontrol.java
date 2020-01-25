package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RandevuKontrol extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    String kullanıcıEmail;

    String gelenBölüm;
    String gelenHoca;
    String gelenTarih;
    String gelenSaat;
    TextView bölümtxt;
    TextView hocatxt;
    TextView tarihtxt;
    TextView saattxt;
    EditText sebepEt;
    String userSebep;
    String kontrolTarih;
    String kontrolSaat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_kontrol);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        kullanıcıEmail=(String) firebaseUser.getEmail();

        bölümtxt=(TextView) findViewById(R.id.txtbölüm);
        hocatxt=(TextView)findViewById(R.id.txthoca);
        tarihtxt=(TextView)findViewById(R.id.txttarih1);
        saattxt=(TextView)findViewById(R.id.txtsaat1);
        sebepEt=findViewById(R.id.etSebep);

        Intent intent=getIntent();
        gelenBölüm=intent.getStringExtra("gidenBölümAdı");
        gelenHoca=intent.getStringExtra("gidenHocaAdı");
        gelenTarih=intent.getStringExtra("gidenTarih");
        gelenSaat=intent.getStringExtra("gidenSaat");

        System.out.println("bölüm="+gelenBölüm);
        System.out.println("hoca="+gelenHoca);
        System.out.println("tarih="+gelenTarih);
        System.out.println("saat="+gelenSaat);

        bölümtxt.setText(gelenBölüm);
        hocatxt.setText(gelenHoca);
        tarihtxt.setText(gelenTarih);
        saattxt.setText(gelenSaat);
        getdata();





    }
    public void randevuKayıt(View view){
        //Randevu saati ve tarihi kontrol ediliyor
        if (gelenTarih.equals(kontrolTarih) && gelenSaat.equals(kontrolSaat)){
            Toast.makeText(getApplicationContext(),"Randevu Saati Dolu Lütfen Başka Saat Seçiniz ",Toast.LENGTH_LONG).show();
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            userSebep=sebepEt.getText().toString();

            // yeni randevu oluşturuluyor
            Map<String, Object> user = new HashMap<>();
            user.put("UserEmail",kullanıcıEmail);
            user.put("RandevuBölüm",gelenBölüm);
            user.put("RandevuHoca",gelenHoca);
            user.put("RandevuTarih",gelenTarih);
            user.put("RandevuSaat",gelenSaat);
            user.put("RandevuSebep",userSebep);



            //randevu ekleniyor
            db.collection("randevu")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Intent ıntent=new Intent(RandevuKontrol.this,studentHomeActivity.class);
                            startActivity(ıntent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
        }


    }

    public void getdata(){
        firebaseFirestore.collection("randevu")
                .whereEqualTo("RandevuHoca",gelenHoca)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                kontrolTarih = document.getString("RandevuTarih");
                                kontrolSaat = document.getString("RandevuSaat");
                            }
                        } else {
                            Log.w("Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
