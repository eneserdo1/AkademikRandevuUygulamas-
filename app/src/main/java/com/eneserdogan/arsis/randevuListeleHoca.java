package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Map;

public class randevuListeleHoca extends AppCompatActivity {
    DatePicker dpListele;
    String Sonuc="";
    String hocaMail="";
    String kişi="";

    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_listele_hoca);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        hocaMail=(String) firebaseUser.getEmail();
        hocaAdıCek();

        dpListele=(DatePicker)findViewById(R.id.datepicker);
        Calendar SimdikiZaman=Calendar.getInstance();
        final int Güncelyıl=SimdikiZaman.get(Calendar.YEAR);
        final int GüncelAy=SimdikiZaman.get(Calendar.MONTH);
        final int GüncelGün=SimdikiZaman.get(Calendar.DAY_OF_MONTH);

        dpListele.init(Güncelyıl, GüncelAy, GüncelGün, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear=monthOfYear+1;
                Sonuc=(String)(dayOfMonth+"-"+monthOfYear+"-"+year);
            }
        });
    }
    public void görüntüle(View view){
        Intent intent=new Intent(randevuListeleHoca.this,randevuKontrolHoca.class);
        intent.putExtra("gidentarih",Sonuc);
        intent.putExtra("gidenHocaAdı",kişi);
        startActivity(intent);

    }
    /*public void hocaAdıCek(){
        CollectionReference collectionReference=firebaseFirestore.collection("hocalar");
        collectionReference.whereEqualTo("email",hocaMail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e !=null){
                    Toast.makeText(randevuListeleHoca.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if (queryDocumentSnapshots !=null){
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data=snapshot.getData();
                        String hocaÜnvan=(String) data.get("ünvan");
                        String hocaAd=(String) data.get("ad");
                        String hocaSoyad=(String) data.get("soyad");
                        //String bölümm=(String)data.get("bölüm");

                        kişi=(String) (hocaÜnvan+" "+hocaAd+" "+hocaSoyad);


                    }

                }
            }
        });
    }*/
    public void hocaAdıCek(){
        firebaseFirestore.collection("hocalar")
                .whereEqualTo("email",hocaMail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String hocaÜnvan = document.getString("ünvan");
                                String hocaAd = document.getString("ad");
                                String hocaSoyad = document.getString("soyad");

                                kişi=(String) (hocaÜnvan+" "+hocaAd+" "+hocaSoyad);
                                System.out.println("hoca "+kişi);


                            }
                        } else {
                            Log.w("Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
