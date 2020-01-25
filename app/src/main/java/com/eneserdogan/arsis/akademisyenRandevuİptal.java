package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class akademisyenRandevuİptal extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Kisi kisi = new Kisi();
    TextView txtmail;
    TextView txtsaat;
    TextView txtsebep;
    TextView txttarih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_akademisyen_randevu_iptal);

    txtmail=findViewById(R.id.txtmail1);
    txtsaat=findViewById(R.id.txtsaat1);
    txtsebep=findViewById(R.id.txtsebep1);
    txttarih=findViewById(R.id.txttarih1);

    txtmail.setText(this.kisi.ogrmail);
    txtsebep.setText(this.kisi.ogrSebep);
    txtsaat.setText(this.kisi.ogrSaat);
    txttarih.setText(this.kisi.tarih);

}
    public void setKisi(Kisi gelenKisi){
        this.kisi = gelenKisi;
        /*System.out.println("VERİLER GELDİ");
        System.out.println(gelenKisi.ogrmail);
        System.out.println(gelenKisi.ogrSaat);
        System.out.println(gelenKisi.ogrSebep);*/}
    public void iptalEt(View view){
        firebaseFirestore.collection("randevu").document(this.kisi.docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Randevu İptal Edildi",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(akademisyenRandevuİptal.this,AkademisyenActivity.class);
                        startActivity(intent2);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Randevu İptal Edilemedi",Toast.LENGTH_LONG).show();
                    }
                });

    }
}
