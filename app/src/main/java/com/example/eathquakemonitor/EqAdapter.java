package com.example.eathquakemonitor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eathquakemonitor.databinding.EqListItemBinding;

public class EqAdapter extends ListAdapter<Earthquake, EqAdapter.EqViewHolder> {

    public static final DiffUtil.ItemCallback<Earthquake> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Earthquake>() {
                @Override
                public boolean areItemsTheSame(@NonNull Earthquake oldEarthquake, @NonNull Earthquake newEarthquake) {
                    return oldEarthquake.getId().equals(newEarthquake.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Earthquake oldEarthquake, @NonNull Earthquake newEarthquake) {
                    return oldEarthquake.equals(newEarthquake);
                }
            };

    protected EqAdapter() {
        super(DIFF_CALLBACK);
    }

    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClick(Earthquake earthquake);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    //diffCallBack, ve si esque lo elementos en la lista son iguales o diferentes
    //añade alguans animaciones
    @NonNull
    @Override //Se ejecuat cada que se crea un viewHolder
    public EqAdapter.EqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   EqListItemBinding binding = EqListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
//inflate  -> comvertir un layout en un view
        return new EqViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EqAdapter.EqViewHolder holder, int position) {
        Earthquake earthquake = getItem(position);
    }

    class EqViewHolder extends RecyclerView.ViewHolder {

        private final EqListItemBinding binding;
        public EqViewHolder(@NonNull EqListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
        public void bind(Earthquake earthquake) {
            binding.magnitudeText.setText(String.valueOf(earthquake.getMagnitude()));
            binding.placeText.setText(earthquake.getPlace());

            binding.getRoot().setOnClickListener(v -> {
                onItemClickListener.onItemClick(earthquake);
            });

            binding.executePendingBindings();
            //esta linea es necesaria en el usod e bindins desde adapters
        }
    }
}
