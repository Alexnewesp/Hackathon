package com.example.mercadillosmadridmapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mercadillosmadridmapa.dto.PointOfInterest;
import com.example.mercadillosmadridmapa.dto.ResultList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mercadillosmadridmapa.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<ResultList>, AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String typeOfLocation;
    private String descAddress = "";
    private String descServices = "";
    private String descHorario = "";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView nresultados, labDescription;
    private View scrollView;
    private Button btnAdd, btnSer, btnHor;

    private PointOfInterest[] listaPointOfInterest = null;

    private Spinner spinner;
    private String[] opciones = {
            "MERCADILLOS",
            "CENTROS CULTURALES",
            "WiFi GRATIS",
            "MUSEOS",
            "PARQUES",
            "BIBLIOTECAS",
            "PISCINAS",
            "TEATROS",
            "CINES",
            "SALAS DE CONCIERTOS"
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        this.labDescription = findViewById(R.id.labDescription);
        this.scrollView = findViewById(R.id.scrollView);
        this.btnAdd = findViewById(R.id.buttonAdd);
        this.btnSer = findViewById(R.id.btnSer);
        this.btnHor = findViewById(R.id.btnHor);
        this.progressBar = findViewById(R.id.pbc);
        this.recyclerView = findViewById(R.id.rvc);
        this.nresultados = findViewById(R.id.nresultados);

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner, opciones);
       ((ArrayAdapter)spinnerAdapter).setDropDownViewResource(R.layout.spinner);
        this.spinner = findViewById(R.id.spinnerv);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void addressSelected(View view){
       btnAdd.setTypeface(Typeface.DEFAULT_BOLD);
       btnAdd.setPaintFlags(btnAdd.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        btnSer.setTypeface(Typeface.DEFAULT);
        btnSer.setPaintFlags(0);
        btnHor.setTypeface(Typeface.DEFAULT);
        btnHor.setPaintFlags(0);
        labDescription.setText(descAddress);
    }
    public void servicesSelected(View view){
        btnSer.setTypeface(Typeface.DEFAULT_BOLD);
        btnSer.setPaintFlags(btnSer.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        btnAdd.setTypeface(Typeface.DEFAULT);
        btnAdd.setPaintFlags(0);
        btnHor.setTypeface(Typeface.DEFAULT);
        btnHor.setPaintFlags(0);
        labDescription.setText(descServices);
    }
    public void horarioSelected(View view){
        btnHor.setTypeface(Typeface.DEFAULT_BOLD);
        btnHor.setPaintFlags(btnHor.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        btnSer.setTypeface(Typeface.DEFAULT);
        btnSer.setPaintFlags(0);
        btnAdd.setTypeface(Typeface.DEFAULT);
        btnAdd.setPaintFlags(0);
        labDescription.setText(descHorario);
    }

    public void closeInfo(View view){
        scrollView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(15.0f);
        mMap.getMinZoomLevel();
        mMap.setOnMarkerClickListener(this);
    }



    @NonNull
    @Override
    public Loader<ResultList> onCreateLoader(int id, @Nullable Bundle args) {

        BuscarConApi buscarConApi = null;
        buscarConApi = new BuscarConApi(this , typeOfLocation);
        return buscarConApi;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLoadFinished(@NonNull Loader<ResultList> loader, ResultList resultList) {

       listaPointOfInterest = resultList.getLista_resultados().toArray(new PointOfInterest[0]);

       if (listaPointOfInterest != null){

           mMap.clear();
           btnAdd.setTypeface(Typeface.DEFAULT);
           btnAdd.setPaintFlags(0);
           btnSer.setTypeface(Typeface.DEFAULT);
           btnSer.setPaintFlags(0);
           btnHor.setTypeface(Typeface.DEFAULT);
           btnHor.setPaintFlags(0);
           labDescription.setText("");
           //labTitle.setText("Info");

           for (int i=0; i < listaPointOfInterest.length ; i++){

               try {
                   LatLng flag = new LatLng(listaPointOfInterest[i].getLocation().getLatitude(), listaPointOfInterest[i].getLocation().getLongitude());
                   mMap.addMarker(new MarkerOptions().position(flag).title(listaPointOfInterest[i].getTitle()));
                   mMap.moveCamera(CameraUpdateFactory.newLatLng(flag));
               } catch(Exception e){

               }
           }

       }

        this.progressBar.setVisibility(View.INVISIBLE);

        // return results
     //   Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ResultList> loader) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      //  Log.d(MainActivity.ETIQUETA_LOG, "SÍ HAY INTERNET");
        this.progressBar.setVisibility(View.VISIBLE);

      //  Log.d(MainActivity.ETIQUETA_LOG, "Opción nueva seleccionada en el spinner");
        TextView textView = (TextView)view;
        typeOfLocation = textView.getText().toString();
     //   Log.d(MainActivity.ETIQUETA_LOG, "Opción tocada " + textView.getText().toString());
        if (RedUtil.hayInternet(this)) {

            //CON AsyncTaskLoader
            LoaderManager lm = LoaderManager.getInstance(this);
            lm.restartLoader(37, null, this);

        } else {
      //      Log.d(MainActivity.ETIQUETA_LOG, "NO HAY INTERNET");
            Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //SERÍA INVOCADA CUANDO CAMBIA EL ADAPTER Y UNA OPCIÓN SELECCIONADA DEJA DE ESTAR DISPONIBLE
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        String Title = marker.getTitle();
        for (int i=0; i<listaPointOfInterest.length; i++){
            String Title2 = listaPointOfInterest[i].getTitle();
            if (Title.equals(Title2)){

               if (listaPointOfInterest[i].getOrganization().getServices() != null){
                    descServices = listaPointOfInterest[i].getOrganization().getServices();
               }
               if (listaPointOfInterest[i].getOrganization().getSchedule() != null){
                    descHorario = listaPointOfInterest[i].getOrganization().getSchedule();
               }
               if (listaPointOfInterest[i].getAddress().getStreetAddress() != null){
                    descAddress = (listaPointOfInterest[i].getAddress().getStreetAddress() + "\n " + listaPointOfInterest[i].getAddress().getPostalCode());

                   btnAdd.setTypeface(Typeface.DEFAULT_BOLD);
                   btnAdd.setPaintFlags(btnAdd.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                   btnSer.setTypeface(Typeface.DEFAULT);
                   btnSer.setPaintFlags(0);
                   btnHor.setTypeface(Typeface.DEFAULT);
                   btnHor.setPaintFlags(0);
                   labDescription.setText(descAddress);
               }


            }

        }

        return false;
    }
}