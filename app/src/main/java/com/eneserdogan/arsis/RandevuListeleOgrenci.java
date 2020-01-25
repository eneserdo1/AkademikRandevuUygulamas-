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

public class RandevuListeleOgrenci extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;
    ArrayList<Randevu> randevus = new ArrayList<>();
    String email;
    ListView RandevuListe;
   // ArrayList<String> randevuArray;
    String randevuSaat;
    String randevuTarih;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_listele_ogrenci);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        email=(String) firebaseUser.getEmail();
        System.out.println("mail"+email);

        RandevuListe=(ListView) findViewById(R.id.RandevuListeLv);
        getdata();

        ArrayAdapter<Randevu> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,randevus);
        RandevuListe.setAdapter(adapter);

        RandevuListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Randevu gidenHoca= randevus.get(position);
                Intent intent2=new Intent(RandevuListeleOgrenci.this,Randevuİptal.class);
                intent2.putExtra("gidenHoca",gidenHoca.hocaAdi);
                intent2.putExtra("gidenTarih",gidenHoca.tarih);
                intent2.putExtra("gidenSaat",gidenHoca.saat);
                intent2.putExtra("id",gidenHoca.ID);
                startActivity(intent2);
            }
        });

    }

    //Öğrenci aldığı randevuları listeliyor
    public void getdata(){
        firebaseFirestore.collection("randevu")
                .whereEqualTo("UserEmail",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Randevu randevu = new Randevu();
                                String HocaAdı = document.getString("RandevuHoca");
                                randevuSaat=document.getString("RandevuSaat");
                                randevuTarih=document.getString("RandevuTarih");
                                randevu.hocaAdi = HocaAdı;
                                randevu.ID = document.getId();
                                randevu.saat = randevuSaat;
                                randevu.tarih = randevuTarih;
                                randevus.add(randevu);
                                System.out.println("hoca "+HocaAdı);
                            }
                            RandevuListe.invalidateViews();
                        } else {
                            Log.w("Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
class Randevu {
    public String ID = "";
    public String hocaAdi = "";
    public String saat = "";
    public String tarih = "";

    @NonNull
    @Override
    public String toString() {
        return hocaAdi;
    }
}

