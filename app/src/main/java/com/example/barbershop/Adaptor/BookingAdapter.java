package com.example.barbershop.Adaptor;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.BookingActivity;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.BookingDetail;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.BookingDataSource;
import com.example.barbershop.Module.BookingDetailDataSource;
import com.example.barbershop.Module.ServiceDataSource;
import com.example.barbershop.MyApplication;
import com.example.barbershop.R;
import com.example.barbershop.ServiceActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<Booking> bookingList;

    private Context mContext;
    private SelectedServicesViewModel viewModel;

    public BookingAdapter(Context mContext ) {
        this.mContext = mContext;
    }

    public void setData(List<Booking> list){
        this.bookingList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        if(booking == null){
            return;
        }
        BookingDataSource bookingDataSource = new BookingDataSource(mContext);
        AccountDataSource accountDataSource = new AccountDataSource(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",-1);


        String staffName = accountDataSource.getStaffByStaffId(booking.getStaffId());
        holder.txtStaff.setText(staffName);

        String userName = accountDataSource.getUserByUserId(booking.getUserId());
        holder.txtUsername.setText(userName);

        holder.txtBookingTime.setText(booking.getTime());

        String slotString = Common.convertTimeSlotToString(Math.toIntExact(booking.getSlot()));
        holder.txtSlot.setText(slotString);

        MyApplication myApplication = (MyApplication) mContext.getApplicationContext();
        viewModel = myApplication.getViewModel();

        BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(mContext);
        List<Service> serviceList = bookingDetailDataSource.getServicesByBookingId(mContext, booking.getId());

        ServiceAdapter serviceAdapter = new ServiceAdapter(mContext, null,true,viewModel);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        holder.rcvService.setLayoutManager(linearLayoutManager1);
        holder.rcvService.setLayoutManager(new GridLayoutManager(mContext, 2));
        serviceAdapter.setData(serviceList);
        holder.rcvService.setAdapter(serviceAdapter);

    }



    @Override
    public int getItemCount() {
        if(bookingList != null){
            return bookingList.size();
        }
        return 0;
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUsername;
        private TextView txtStaff;
        private TextView txtBookingTime;
        private TextView txtSlot;
        private RecyclerView rcvService;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtStaff = itemView.findViewById(R.id.txtStaff);
            txtBookingTime = itemView.findViewById(R.id.txtBookingTime);
            txtSlot = itemView.findViewById(R.id.txtSlot);
            rcvService = itemView.findViewById(R.id.rcvService);
        }
    }
}
