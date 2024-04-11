package com.example.eathquakemonitor.main;

import androidx.lifecycle.LiveData;

import com.example.eathquakemonitor.Earthquake;
import com.example.eathquakemonitor.api.EarthquakeJSONresponse;
import com.example.eathquakemonitor.api.EqApiClient;
import com.example.eathquakemonitor.api.Feature;
import com.example.eathquakemonitor.database.EqDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {


    private final EqDatabase database;

    public interface DownloadStatusListener {
        void downloadSuccess();
        void downloadError(String message);
    }


    public MainRepository(EqDatabase database) {
        this.database = database;
    }

    public LiveData<List<Earthquake>> getEqList() {
        return  database.eqDao().getEarthquake();
    }
    public void downloadAndSaveEarthquakes(DownloadStatusListener downloadStatusListener) {
        EqApiClient.EqService service  = EqApiClient.getInstance().getService();

        //enqueue , manda la llamada de datos a una cola, nos permite hacer la peticion en "otro hilo"
        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONresponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONresponse> call, Response<EarthquakeJSONresponse> response) {
                // List<Earthquake> earthquakeList =  parseEarthquakes( response.body());
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());

                EqDatabase.databaseWeiteExecutor.execute(() -> {
                    database.eqDao().insertAll(earthquakeList);
                });
                downloadStatusListener.downloadSuccess();

            }

            @Override
            public void onFailure(Call<EarthquakeJSONresponse> call, Throwable throwable) {
                downloadStatusListener.downloadError("Thre was an error downloadin, check you internet conexion");

            }
        });

        //  this.eqList.setValue(eqList);
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJSONresponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();

        List<Feature> features = body.getFeatures();
        for (Feature feature: features){
            String id = feature.getId();
            double magnitude = feature.getProperties().getMag();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();

            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();

            Earthquake earthquake = new Earthquake(id, place, magnitude, time, latitude, longitude);
            eqList.add(earthquake);
        }

        return eqList;
    }
}
