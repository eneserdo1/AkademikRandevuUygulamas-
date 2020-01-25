package com.eneserdogan.arsis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String sınıfsec;
    String bölümsec;
    TextView userEmail;
    Button saveUser;
    EditText name;
    EditText surname;
    Spinner bölüm;
    Spinner sınıfListe;
    String usermailkayıt;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userEmail = findViewById(R.id.tvUserEmail);
        saveUser = findViewById(R.id.btnKaydet);
        name = findViewById(R.id.etAd);
        surname = findViewById(R.id.etSoyad);
        bölüm = findViewById(R.id.etBölüm);
        sınıfListe = (Spinner) findViewById(R.id.spinner);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        usermailkayıt=firebaseUser.getEmail();

        //Strings.xml dosyasındaki itemler spinnera set ediliyor
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.sınıf,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sınıfListe.setAdapter(adapter);
        sınıfListe.setOnItemSelectedListener(this);

        //Strings.xml dosyasındaki itemler spinnera set ediliyor
        ArrayAdapter<CharSequence>adapter2=ArrayAdapter.createFromResource(this,R.array.bölüm,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bölüm.setAdapter(adapter2);
        bölüm.setOnItemSelectedListener(this);




    }

    public void kaydet(View view){
        //Kişi bilgileri degişkenlere aktarılıp uploadData fonksiyonuna gönderiliyor
        String userName=name.getText().toString().trim();
        String userSurname=surname.getText().toString().trim();
        String userBölüm=bölümsec;
        String userSınıf=sınıfsec;
        String userEmail=usermailkayıt;

        uploadData(userName,userSurname,userBölüm,userSınıf,userEmail);


    }

    private void uploadData(String userName, String userSurname, String userBölüm, String userSınıf,String userEmail) {
        String id= UUID.randomUUID().toString();//random ıd
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Kullanıcı oluşturuluyor
        Map<String, Object> user = new HashMap<>();
        user.put("id",id);
        user.put("ad",userName);
        user.put("soyad",userSurname);
        user.put("bölüm",userBölüm);
        user.put("sınıf",userSınıf);
        user.put("email",userEmail);



        // Yeni belge ekleniyor
        db.collection("users")
                .document(id).set(user);

        Toast.makeText(ProfileActivity.this, "Kayıt Başarılı",
                                Toast.LENGTH_LONG).show();
        Intent intent2=new Intent(ProfileActivity.this,studentHomeActivity.class);
        startActivity(intent2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sınıfsec=sınıfListe.getSelectedItem().toString();//Sınıf spinnerından seçilen ıtem alınıyor
        bölümsec=bölüm.getSelectedItem().toString();//Bölüm spinnerından seçilen ıtem alınıyor

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

