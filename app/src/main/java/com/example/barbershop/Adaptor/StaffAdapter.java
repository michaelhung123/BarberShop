package com.example.barbershop.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Domain.Staff;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.ServiceDataSource;
import com.example.barbershop.R;
import com.example.barbershop.StaffProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    private List<Account> staffList;
    private Context mContext;
    public StaffAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Account> list){
        this.staffList = list;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff, parent, false);
            return new StaffViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.StaffViewHolder holder, int position) {
        Account account = staffList.get(position);
        if(account == null){
            return;
        }
        AccountDataSource accountDataSource = new AccountDataSource(mContext);
        String fileImage = accountDataSource.getFilePictureForCategory(account.getId());
        holder.staffName.setText(account.getName());
        Picasso.get().load(fileImage).resize(300,300).into(holder.imgPicStaff);
        holder.imgPicStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StaffProfileActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if(staffList != null){
            return staffList.size();
        }
        return 0;
    }

    public class StaffViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPicStaff;
        private TextView staffName;
        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicStaff = itemView.findViewById(R.id.imgPicStaff);
            staffName = itemView.findViewById(R.id.staffName);
        }
    }
}
