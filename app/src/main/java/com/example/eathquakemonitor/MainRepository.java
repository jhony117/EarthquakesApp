package com.example.eathquakemonitor;

import com.example.eathquakemonitor.api.EarthquakeJSONresponse;
import com.example.eathquakemonitor.api.EqApiClient;
import com.example.eathquakemonitor.api.Feature;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public interface DownloadEqsListener {
        void onEqsDownloaded(List<Earthquake> earthquakeList);
    }

    public void getEarthquakes(DownloadEqsListener downloadEqsListener) {
        EqApiClient.EqService service  = EqApiClient.getInstance().getService();

        //enqueue , manda la llamada de datos a una cola, nos permite hacer la peticion en "otro hilo"
        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONresponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONresponse> call, Response<EarthquakeJSONresponse> response) {
                // List<Earthquake> earthquakeList =  parseEarthquakes( response.body());

                //

                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                downloadEqsListener.onEqsDownloaded(earthquakeList);

            }

            @Override
            public void onFailure(Call<EarthquakeJSONresponse> call, Throwable throwable) {

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
