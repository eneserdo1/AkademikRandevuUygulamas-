package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileActivityHoca extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    EditText name;
    EditText surname;
    Spinner bolum;
    Spinner unvan;
    String unvanSec;
    String bolumSec;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hoca);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userEmail=(String) firebaseUser.getEmail();

        name=findViewById(R.id.etAdHoca);
        surname=findViewById(R.id.etSoyadHoca);
        bolum=findViewById(R.id.bolumSpinner);
        unvan=findViewById(R.id.unvanSpinner);

        //unvanlar listesindeki itemler adaptere tanımlanıyor
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.ünvanlar,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unvan.setAdapter(adapter);
        unvan.setOnItemSelectedListener(this);

        //bölüm listesinin itemleri adaptere tanımlanıyor
        ArrayAdapter<CharSequence>adapter2=ArrayAdapter.createFromResource(this,R.array.bölüm,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bolum.setAdapter(adapter2);
        bolum.setOnItemSelectedListener(this);

    }
    //hoca bilgileri uploaddata'ya gönderiliyor
    public void kaydetHoca(View view){
        String userName=name.getText().toString().trim();
        String userSurname=surname.getText().toString().trim();
        String userBölüm=bolumSec;
        String userUnvan=unvanSec;
        uploadData(userName,userSurname,userBölüm,userUnvan,userEmail);

    }
    private void uploadData(String userName, String userSurname, String userBölüm, String userUnvan,String userEmail) {
        String id= UUID.randomUUID().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Kullanıcı oluşturuluyor
        Map<String, Object> user = new HashMap<>();
        user.put("id",id);
        user.put("ad",userName);
        user.put("soyad",userSurname);
        user.put("ünvan",userUnvan);
        user.put("bölüm",userBölüm);
        user.put("email",userEmail);


        //Kullanıcı hocalar dosyasına ekleniyor
        db.collection("hocalar")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent ıntent=new Intent(ProfileActivityHoca.this,AkademisyenActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        unvanSec=unvan.getSelectedItem().toString();//Unvan spinnerından veri değişkene aktarılıyor
        bolumSec=bolum.getSelectedItem().toString();//Bölüm spinnerından veri değişkene aktarılıyor

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
