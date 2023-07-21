package com.example.barbershop.Adaptor;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.ServiceDataSource;
import com.example.barbershop.R;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<Service> serviceList;
    private Context mContext;
    private static final int VIEW_TYPE_ITEM = 0; // Kiểu item_service
    private static final int VIEW_TYPE_EMPTY = 1; // Kiểu rỗng (không có item_service)

    public ServiceAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Service> list){
        this.serviceList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
            return new ServiceViewHolder(itemView);
        }else {
            // Trả về một ViewHolder trống
            View emptyView = new View(parent.getContext());
            return new ServiceViewHolder(emptyView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        if(service == null){
            return;
        }
                ServiceDataSource serviceDataSource = new ServiceDataSource(mContext);
                String fileImage = serviceDataSource.getFilePictureForCategory(service.getId());
                holder.title_service.setText(service.getName());
                holder.serviceDescription.setText(service.getDescription());
                holder.priceService.setId((int) service.getPrice());
                Picasso.get().load(fileImage).resize(300,300).into(holder.imgPicService);

    }


    @Override
    public int getItemCount() {
        if(serviceList != null){
            return serviceList.size();
        }
        return 0;
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imgPicService;
        private TextView title_service;
        private TextView serviceDescription;
        private TextView priceService;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicService = itemView.findViewById(R.id.imgPicService);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            title_service = itemView.findViewById(R.id.title_service);
            priceService = itemView.findViewById(R.id.priceService);

        }
    }
}
