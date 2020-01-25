package com.eneserdogan.arsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class RandevuTarih extends AppCompatActivity {
    DatePicker dpSonuc;
    String Sonuc;
    TextView TarihTxt;
    String gelenHoca;
    String gelenBölüm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_tarih);
        Intent intent=getIntent();
        gelenHoca=intent.getStringExtra("gidenHocaAdı");
        gelenBölüm=intent.getStringExtra("gidenBölümAdı");



        dpSonuc=(DatePicker) findViewById(R.id.datepicker);
        TarihTxt=(TextView) findViewById(R.id.txtTarih);
        Calendar SimdikiZaman=Calendar.getInstance();
        final int Güncelyıl=SimdikiZaman.get(Calendar.YEAR);
        final int GüncelAy=SimdikiZaman.get(Calendar.MONTH);
        final int GüncelGün=SimdikiZaman.get(Calendar.DAY_OF_MONTH);

        dpSonuc.init(Güncelyıl, GüncelAy, GüncelGün, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear=monthOfYear+1;
                Sonuc=(String)(dayOfMonth+"-"+monthOfYear+"-"+year);
                TarihTxt.setText(Sonuc);

            }
        });

    }
    public void TarihSec(View view){
        Intent intent =new Intent(RandevuTarih.this,RandevuSaat.class);
        intent.putExtra("gidenBölümAdı2",gelenBölüm);
        intent.putExtra("gidenHocaAdı2",gelenHoca);
        intent.putExtra("gidenTarih",Sonuc);
        startActivity(intent);

    }
}
