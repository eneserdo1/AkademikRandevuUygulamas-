package com.eneserdogan.arsis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RandevuBolum extends AppCompatActivity {
    //Spinnerda listelenecek bölümler
    private String[] bölüm =
            {"Bilgisayar Mühendisliği", "Makine Mühendisliği", "Biyomedikal Mühendisliği",
                    "Gıda Mühendisliği","Elektrik Elektronik Mühendisliği",
                    "Endüstri Mühendisliği", "Çevre Mühendisliği", "Endüstriyel Tasarım Mühendisliği", "Mekatronik Mühendisliği"};


    //ArrayList<String> hocaAdFb;
    String BölümSecim;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ListView BölümLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_bolum);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        final ListView Bölümler=(ListView) findViewById(R.id.BölümLv);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,bölüm);
        Bölümler.setAdapter(adapter);

        Bölümler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BölümSecim=(String)bölüm[position];
                Intent intent= new Intent(RandevuBolum.this,RandevuHoca.class);
                intent.putExtra("bölümİsim",BölümSecim);//Seçilen bölümü RandevuHoca activity'e gönderiyoruz
                startActivity(intent);



            }
        });



    }



}
