package com.example.eathquakemonitor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eathquakemonitor.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Earthquake> eqList = new ArrayList();
        eqList.add(new Earthquake("casdnciao", "Buenos Aires", 5.0, 4234234523L, 105.23, 68.234));
        eqList.add(new Earthquake("fwefwxwrf", "Ciudad de Mexico", 4.0, 43123345L, 105.23, 68.234));
        eqList.add(new Earthquake("gegtgthth", "Lima", 2.3, 4234234523L, 105.23, 68.234));
        eqList.add(new Earthquake("vthythxzx", "Madrid", 3.4, 4234234523L, 105.23, 68.234));
        eqList.add(new Earthquake("tjdtyjyui", "Caracas", 6.2, 4234234523L, 105.23, 68.234));

        EqAdapter adapter = new EqAdapter();
        binding.eqRecycler.setAdapter(adapter);
        adapter.submitList(eqList);
    }
}
