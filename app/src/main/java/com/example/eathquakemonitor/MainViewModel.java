package com.example.eathquakemonitor;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eathquakemonitor.api.EqApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    //Creamos un mutableLiveData para que lo recoja el view
    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    public void getEarthquakes() {
      EqApiClient.EqService service  = EqApiClient.getInstance().getService();

              //enqueue , manda la llamada de datos a una cola, nos permite hacer la peticion en "otro hilo"
              service.getEarthquakes().enqueue(new Callback<String>() {
                  @Override
                  public void onResponse(Call<String> call, Response<String> response) {
                    List<Earthquake> earthquakeList =  parseEarthquakes( response.body());

                    eqList.setValue(earthquakeList);
                  }

                  @Override
                  public void onFailure(Call<String> call, Throwable throwable) {

                  }
              });

      //  this.eqList.setValue(eqList);
    }

    private List<Earthquake> parseEarthquakes(String responseString) {

        ArrayList<Earthquake> eqList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject((responseString));
            JSONArray featuresJSONArray = jsonResponse.getJSONArray("features");

            for (int i = 0;  i> featuresJSONArray.length(); i ++){
                JSONObject jsonFeature = featuresJSONArray.getJSONObject(i);
                String id = jsonFeature.getString("id");
                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");

                Double magnitude = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");
                long time  = jsonProperties.getLong("time");

                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coordinatesJSONArray = jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJSONArray.getDouble(0);
                double latitude = coordinatesJSONArray.getDouble(1);

                Earthquake earthquake = new Earthquake(id, place, magnitude, time, latitude, longitude);
                eqList.add(earthquake);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }

        return eqList;
    }


}


