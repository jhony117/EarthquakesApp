package com.example.eathquakemonitor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.eathquakemonitor.Earthquake;
import java.util.List;

@Dao
public interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> eqList);

    //se uas aqui earthquakes por que lo definimos como entity
    @Query("SELECT * FROM earthquakes")
    LiveData<List<Earthquake>> getEarthquake();

    @Query("SELECT * FROM earthquakes WHERE magnitude > :myMagnitude")
    LiveData<List<Earthquake>> getEarthquakesWithMagnitudeAbove(double myMagnitude);

}
