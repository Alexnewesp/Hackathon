package com.example.mercadillosmadridmapa;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import com.example.mercadillosmadridmapa.dto.ResultList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BuscarConApi extends AsyncTaskLoader<ResultList> {

    private String typeOfLocation, endpoint;
    private final static String URL_API_MERCADILLOS_MADRID = "https://datos.madrid.es/egob/catalogo/";

    public BuscarConApi(@NonNull Context context, String typeOfLocation) {
        super(context);
              this.typeOfLocation = typeOfLocation;
      //  Log.d(MainActivity.ETIQUETA_LOG, "BusquedaEventos constructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public ResultList loadInBackground() {
        //Object resultadoCanciones = null;
        ResultList resultList =null;


        if (typeOfLocation.equals("CENTROS CULTURALES")){
            endpoint = "200304-0-centros-culturales.json";

        } else if (typeOfLocation.equals("WiFi GRATIS")){
            endpoint = "216619-0-wifi-municipal.json";

        } else if (typeOfLocation.equals("MUSEOS")){
            endpoint = "201132-0-museos.json";

        } else if (typeOfLocation.equals("MERCADILLOS")){
          endpoint = "202105-0-mercadillos.json";

        } else if (typeOfLocation.equals("PARQUES")){
            endpoint = "200761-0-parques-jardines.json";

        } else if (typeOfLocation.equals("BIBLIOTECAS")){
            endpoint = "201747-0-bibliobuses-bibliotecas.json";

        } else if (typeOfLocation.equals("PISCINAS")){
            endpoint = "210227-0-piscinas-publicas.json";

        } else if (typeOfLocation.equals("TEATROS")){
            endpoint = "208862-7650046-ocio_salas.json";

        } else if (typeOfLocation.equals("CINES")){
            endpoint = "208862-7650164-ocio_salas.json";

        } else if (typeOfLocation.equals("SALAS DE CONCIERTOS")){
            endpoint = "208862-7650180-ocio_salas.json";

        }


        //String info_eventos = null;
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStreamReader inputStreamReader = null;
        // Gson gson = null;

        try {
            // display districts
            endpoint = URLEncoder.encode(endpoint, "UTF-8");
              url = new URL(URL_API_MERCADILLOS_MADRID + endpoint);

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
                    resultList = mapper.readValue(inputStreamReader, ResultList.class);
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


        return resultList;
    }
}