package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class randevuKontrolHoca extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String tarih="";
    String hocaAd="";
    ArrayList<Kisi> randevularFb;
    ListView randevularLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_kontrol_hoca);
        Intent ıntent=getIntent();
        tarih=ıntent.getStringExtra("gidentarih");
        hocaAd=ıntent.getStringExtra("gidenHocaAdı");

        randevularLv=(ListView) findViewById(R.id.hocaRandevularıLv);
        randevularFb=new ArrayList<>();
        getdata();

        ArrayAdapter<Kisi> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,randevularFb);
        randevularLv.setAdapter(adapter);

        randevularLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                akademisyenRandevuİptal akademisyenRandevuİptal = new akademisyenRandevuİptal();
                akademisyenRandevuİptal.setKisi(randevularFb.get(position));
                Intent intent2=new Intent(randevuKontrolHoca.this,akademisyenRandevuİptal.class);
                startActivity(intent2);

            }
        });

    }

    //Hoca kendisinden randevu alanları listeliyor
    public void getdata(){
        CollectionReference collectionReference=firebaseFirestore.collection("randevu");
        collectionReference.whereEqualTo("RandevuHoca",hocaAd).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e !=null){
                    Toast.makeText(randevuKontrolHoca.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if (queryDocumentSnapshots !=null){
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data=snapshot.getData();
                        Kisi kisi = new Kisi();
                        kisi.ogrmail=(String) data.get("UserEmail");
                        kisi.ogrSaat=(String) data.get("RandevuSaat");
                        kisi.ogrSebep=(String) data.get("RandevuSebep");
                        kisi.tarih=(String)data.get("RandevuTarih");
                        kisi.docId=snapshot.getId();

                        if (kisi.tarih.equals(tarih)){
                            randevularFb.add(kisi);
                        }

                    }
                    randevularLv.invalidateViews();


                }
            }
        });
    }
}

class Kisi{
    public String ogrmail = "";
    public String ogrSaat = "";
    public String ogrSebep = "";
    public String tarih = "";
    public String docId="";

    @NonNull
    @Override
    public String toString() {
        return ogrmail+" "+ogrSaat;
    }
}