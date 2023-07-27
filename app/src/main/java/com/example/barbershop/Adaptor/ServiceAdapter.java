package com.example.barbershop.Adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.ServiceDataSource;
import com.example.barbershop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<Service> serviceList;
    private List<Service> selectedServices = new ArrayList<>();
    private Button btnAddListService;
    private Button btnAddService;
    boolean isVisible = false;
    private Context mContext;
    private static final int VIEW_TYPE_ITEM = 0; // Kiểu item_service
    private static final int VIEW_TYPE_EMPTY = 1; // Kiểu rỗng (không có item_service)

    public ServiceAdapter(Context mContext, Button btnAddListService) {
        this.mContext = mContext;
        this.btnAddListService = btnAddListService;
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

        holder.btnAddService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Kiểm tra xem item đã tồn tại trong danh sách selectedServices chưa
                        if (selectedServices.contains(service)) {
                            // Nếu đã tồn tại, xóa item khỏi danh sách
                            holder.btnAddService.setBackgroundColor(Color.parseColor("#2BFFB3"));
                            holder.btnAddService.setText("Booking");
                            selectedServices.remove(service);
                        } else {
                            // Nếu chưa tồn tại, thêm item vào danh sách
                            holder.btnAddService.setBackgroundColor(Color.YELLOW);
                            holder.btnAddService.setText("Remove");
                            selectedServices.add(service);
                        }

                        // Cập nhật số lượng dịch vụ đã chọn lên btnAddListService
                        btnAddListService.setText(String.format("Chọn %d dịch vụ", selectedServices.size()));

                        // Kiểm tra xem danh sách selectedServices có rỗng không để ẩn btnAddListService nếu cần
                        if (selectedServices.isEmpty()) {
                            isVisible = false;
                            btnAddListService.setVisibility(View.GONE);
                        } else {
                            isVisible = true;
                            btnAddListService.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    @Override
    public int getItemCount() {
        if(serviceList != null){
            return serviceList.size();
        }
        return 0;
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPicService;
        private TextView title_service;
        private TextView serviceDescription;
        private TextView priceService;
        private Button btnAddService;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicService = itemView.findViewById(R.id.imgPicService);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            title_service = itemView.findViewById(R.id.title_service);
            priceService = itemView.findViewById(R.id.priceService);
            btnAddService = itemView.findViewById(R.id.btnAddService);
        }
    }
}
