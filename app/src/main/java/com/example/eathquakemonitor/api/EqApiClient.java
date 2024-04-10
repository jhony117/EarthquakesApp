package com.example.eathquakemonitor.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class EqApiClient {

    public interface EqService {
        @GET("all_hour.quakeml")
        Call<EarthquakeJSONresponse> getEarthquakes();
    }

   private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
           .addConverterFactory(MoshiConverterFactory.create())//las cargas de datos lleagn en string
            .build();


    private EqService service;


    private static final EqApiClient ourInsatnce = new EqApiClient();

    public static EqApiClient getInstance() {return ourInsatnce; }

    private EqApiClient() {

    }

    public EqService getService() {
      if(service == null){
         service  = retrofit.create(EqService.class);
      }

        return service;
    }

    //singketon  -> clase de una sola instancia
        //la segunda vez que lo mandemos a llamar , llamara a la instancia

}
