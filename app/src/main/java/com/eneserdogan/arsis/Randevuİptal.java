package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Randevuİptal extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    String gelenHoca="";
    String gelenTarih="";
    String gelenSaat="";
    String gelenid="";
    TextView adTxt;
    TextView tarihTxt;
    TextView saatTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_iptal);


        Intent intent=getIntent();
        gelenHoca=intent.getStringExtra("gidenHoca");
        gelenSaat=intent.getStringExtra("gidenSaat");
        gelenTarih=intent.getStringExtra("gidenTarih");
        gelenid=intent.getStringExtra("id");
        System.out.println(gelenid);
        System.out.println(gelenHoca);

        adTxt=findViewById(R.id.txt4);
        tarihTxt=findViewById(R.id.txt5);
        saatTxt=findViewById(R.id.txt6);

        adTxt.setText(gelenHoca);
        tarihTxt.setText(gelenTarih);
        saatTxt.setText(gelenSaat);
        System.out.println("id="+gelenid);


    }
    public void iptalet(View view){
        //id'ye göre randevuyu siliyoruz
        firebaseFirestore.collection("randevu").document(gelenid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Randevu İptal Edildi",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(Randevuİptal.this,studentHomeActivity.class);
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
