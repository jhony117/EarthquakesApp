package com.example.eathquakemonitor.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eathquakemonitor.Earthquake;
import com.example.eathquakemonitor.R;
import com.example.eathquakemonitor.api.RequestStatus;
import com.example.eathquakemonitor.databinding.ActivityMainBinding;

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

        MainViewModel viewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);



        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));


        EqAdapter adapter = new EqAdapter();
        adapter.setOnItemClickListener(new EqAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Earthquake earthquake) {
                Toast.makeText(MainActivity.this, earthquake.getPlace(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.eqRecycler.setAdapter(adapter);

        viewModel.getEqList().observe(this, eqList -> {
            adapter.submitList(eqList);

            if (eqList.isEmpty()) {
                binding.emptyView.setVisibility((View.VISIBLE));
            } else {
                binding.emptyView.setVisibility(View.GONE);
            }

        });
        viewModel.getStatusMutableLiveData().observe(this, statusWithDescription -> {
            if (statusWithDescription.getStatus() == RequestStatus.LOADING){
                    binding.loadingWheel.setVisibility(View.VISIBLE);
            } else {
                    binding.loadingWheel.setVisibility(View.GONE);
            }
            if(statusWithDescription.getStatus() ==RequestStatus.ERROR){
                Toast.makeText(this, statusWithDescription.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


   viewModel.downloadEarthquakes();
    }
}
