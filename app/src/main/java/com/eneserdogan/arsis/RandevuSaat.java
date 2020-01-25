package com.eneserdogan.arsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RandevuSaat extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    String gelenTarih;
    String gelenHoca;
    String gelenBölüm;
    ListView Saatlv;
    String randevuSaat;
    private String[] saatler={"10.00-10.30","10.30-11.00","11.00-11.30","11.30-12.00","13.30-14.00","14.00-14.30","14.30-15.00","15.00-15.30",
    "15.30-16.00","16.00-16.30","16.30-17.00"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_saat);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        Intent intent=getIntent();
        gelenBölüm=intent.getStringExtra("gidenBölümAdı2");
        gelenHoca=intent.getStringExtra("gidenHocaAdı2");
        gelenTarih=intent.getStringExtra("gidenTarih");
        System.out.println(gelenTarih);

        Saatlv=(ListView)findViewById(R.id.saatLv);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,saatler);
        Saatlv.setAdapter(adapter);

        Saatlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                randevuSaat=(String)saatler[position];
                Intent intent2=new Intent(RandevuSaat.this,RandevuKontrol.class);
                intent2.putExtra("gidenBölümAdı",gelenBölüm);
                intent2.putExtra("gidenHocaAdı",gelenHoca);
                intent2.putExtra("gidenTarih",gelenTarih);
                intent2.putExtra("gidenSaat",randevuSaat);
                startActivity(intent2);

            }
        });



    }
}
