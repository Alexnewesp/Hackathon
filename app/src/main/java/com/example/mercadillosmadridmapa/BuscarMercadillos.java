package com.example.mercadillosmadridmapa;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

//import com.google.gson.Gson;

import com.example.mercadillosmadridmapa.dto.Mercadillos;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BuscarMercadillos extends AsyncTaskLoader<Mercadillos> {

      private String distrito;
    private final static String URL_API_MERCADILLOS_MADRID = "https://datos.madrid.es/egob/catalogo/202105-0-mercadillos.json?distrito_nombre=";

    public BuscarMercadillos(@NonNull Context context, String distrito) {
        super(context);
              this.distrito = distrito;
      //  Log.d(MainActivity.ETIQUETA_LOG, "BusquedaEventos constructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public Mercadillos loadInBackground() {
        //Object resultadoCanciones = null;
        Mercadillos mercadillos =null;
        //String info_eventos = null;
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStreamReader inputStreamReader = null;
        // Gson gson = null;

        try {
            // display districts
              distrito = URLEncoder.encode(distrito, "UTF-8");
              url = new URL(URL_API_MERCADILLOS_MADRID + distrito);

        //    Log.d(MainActivity.ETIQUETA_LOG, "Llamando a " + URL_API_MERCADILLOS_MADRID);
        //    url = new URL(URL_API_MERCADILLOS_MADRID);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
         //       Log.d(MainActivity.ETIQUETA_LOG, "Respuesta OK ");
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                // gson = new Gson();
                //resultadoCanciones = gson.fromJson(inputStreamReader, ResultadoCanciones.class);


                StringBuilder stringBuilder = new StringBuilder();

                try (BufferedReader br = new BufferedReader(inputStreamReader))

                {

                    ObjectMapper mapper = new ObjectMapper();
                    mercadillos = mapper.readValue(inputStreamReader, Mercadillos.class);
          //          Log.d(MainActivity.ETIQUETA_LOG, "RX n mercadillos = " + mercadillos.getlista_mercadillos().size());

                } catch (Exception e) {
                    e.printStackTrace();
      //              Log.e(MainActivity.ETIQUETA_LOG, "error ", e);
                }


            } else {
      //          Log.d(MainActivity.ETIQUETA_LOG, "Respuesta FALLO ");
            }


        } catch (Throwable fallo) {
     //       Log.e(MainActivity.ETIQUETA_LOG, "Se ha producido un fallo ", fallo);
        } finally {

            try {
                inputStreamReader.close();
                httpURLConnection.disconnect();
            } catch (IOException ioException) {
      //          Log.e(MainActivity.ETIQUETA_LOG, "Fallo al liberar recursos ", ioException);
            }


        }


        return mercadillos;
    }
}