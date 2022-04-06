package com.example.mercadillosmadridmapa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void abrirlistamercadillo(View view) {
        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }


}