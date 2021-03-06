package com.example.practica1t.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.practica1t.R;
import com.example.practica1t.common.Location;
import com.example.practica1t.services.GpsService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.example.practica1t.common.Constantes.INTENT_LOCALIZATION_ACTION;
import static com.example.practica1t.common.Constantes.LATITUDE;
import static com.example.practica1t.common.Constantes.LONGITUDE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private double latitude;
    private double longitude;
    private ArrayList<Location> listaLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLocation = new ArrayList();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startService();
        }


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(INTENT_LOCALIZATION_ACTION));


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_localizacion_actual:

                Location location = new Location();
                location.setLatitude(latitude);
                location.setAltitude(longitude);
                listaLocation.add(location);

                Intent locationIntent = new Intent(MainActivity.this, UbicacionActual.class);
                locationIntent.putExtra(LATITUDE, latitude);
                locationIntent.putExtra(LONGITUDE, longitude);
                startActivity(locationIntent);

                Toast.makeText(this, "Estas en Ubicación Actual", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_guardar_ubicacion:
                Intent intentGuardarUbicacion = new Intent(MainActivity.this, BotonGuardarUbicaciones.class);
                startActivity(intentGuardarUbicacion);
                Toast.makeText(this, "Estas en Guardar Ubicación", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_instalaciones_deportivas:
                Intent intentPolideportivos = new Intent(MainActivity.this, ListViewPolideportivos.class);
                startActivity(intentPolideportivos);
                Toast.makeText(this, "Estas en Instalaciones Deportivas", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_piscinas:
                Intent intentPiscinas = new Intent(MainActivity.this, ListViewPiscinas.class);
                startActivity(intentPiscinas);
                Toast.makeText(this, "Estas en Piscinas", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_paginas_interes:
                Intent intentInteres = new Intent(MainActivity.this, PaginasInteres.class);
                startActivity(intentInteres);
                Toast.makeText(this, "Estas en Paginas de interes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_favoritos:
                Intent intentFavoritos = new Intent(MainActivity.this, Favoritos.class);
                startActivity(intentFavoritos);
                Toast.makeText(this, "Estas en Favoritos", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = intent.getDoubleExtra(LATITUDE, 0);
            longitude = intent.getDoubleExtra(LONGITUDE, 0);
        }
    };

    public void startService() {
        Intent mServiceIntent = new Intent(getApplicationContext(), GpsService.class);
        startService(mServiceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permiso GPS concedido", Toast.LENGTH_SHORT).show();
                startService();
            } else {
                Toast.makeText(getApplicationContext(), "Permiso GPS denegado debido a esto la aplicación puede funcionar incorrectamente porfavor reinicia para aplicar permisos de GPS", Toast.LENGTH_LONG).show();
            }
        }
    }
}