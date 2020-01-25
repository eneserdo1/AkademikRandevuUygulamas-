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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class RandevuHoca extends AppCompatActivity {
    String kişi;
    String bölümAd;
    ArrayList<String> hocaAdFb;
    String hocaSecim;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ListView HocaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_hoca);

        Intent intent=getIntent();
        bölümAd=intent.getStringExtra("bölümİsim");//Bir önceki activityden gelen veriyi alıyoruz

        HocaList=(ListView) findViewById(R.id.HocaLv);
        hocaAdFb=new ArrayList<>();

        getData(); //Fonksiyonu çağırarak bir önceki activityden gelen bölüm isimi ile o bölümün hocalarını firebaseden çekiyoruz
        System.out.println("bölüm " + bölümAd);

        //Doldurduğumuz hocaAdFb arraylistini spinnera set ediyoruz
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,hocaAdFb);
        HocaList.setAdapter(adapter);

        HocaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hocaSecim=(String) hocaAdFb.get(position);
                System.out.println("hoca "+hocaSecim);
                Intent intent2=new Intent(RandevuHoca.this,RandevuTarih.class);
                intent2.putExtra("gidenBölümAdı",bölümAd);
                intent2.putExtra("gidenHocaAdı",hocaSecim);
                startActivity(intent2);

            }
        });

    }
    public void getData(){
        //hocaAdFb.clear();
        CollectionReference collectionReference=firebaseFirestore.collection("hocalar");
        collectionReference.whereEqualTo("bölüm",bölümAd).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e !=null){
                    Toast.makeText(RandevuHoca.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if (queryDocumentSnapshots !=null){
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data=snapshot.getData();
                        String hocaÜnvan=(String) data.get("ünvan");
                        String hocaAd=(String) data.get("ad");
                        String hocaSoyad=(String) data.get("soyad");
                       //String bölümm=(String)data.get("bölüm");

                        kişi=(String) (hocaÜnvan+" "+hocaAd+" "+hocaSoyad);
                        System.out.println(kişi);
                        hocaAdFb.add(kişi);//Çekilen hocaların isimlerini hocaAdFb arraylistine atıyoruz


                    }
                    HocaList.invalidateViews();

                }
            }
        });

    }

}
