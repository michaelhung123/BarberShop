package com.example.barbershop.Adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Common.Common;
import com.example.barbershop.CustomToast;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.BookingDataSource;
import com.example.barbershop.R;

import org.w3c.dom.Text;

import java.util.List;

public class BookingForStaffAdapter extends RecyclerView.Adapter<BookingForStaffAdapter.BookingStaffViewHolder> {
    private List<Booking> bookingList;

    private Context mContext;
    private SelectedServicesViewModel viewModel;

    public BookingForStaffAdapter(Context mContext ) {
        this.mContext = mContext;
    }

    public void setData(List<Booking> list){
        this.bookingList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookingForStaffAdapter.BookingStaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_for_staff, parent, false);
        return new BookingForStaffAdapter.BookingStaffViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingStaffViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        if(booking == null){
            return;
        }
        AccountDataSource accountDataSource = new AccountDataSource(mContext);
        BookingDataSource bookingDataSource = new BookingDataSource(mContext);

        holder.txtBookingId.setText(String.valueOf(booking.getId()));

        String userName = accountDataSource.getUserByUserId(booking.getUserId());
        holder.txtUsername.setText(userName);

        holder.txtBookingTime.setText(booking.getTime());

        String slotString = Common.convertTimeSlotToString(Math.toIntExact(booking.getSlot()));
        holder.txtSlot.setText(slotString);

//        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                booking.setStatus(true);
//                bookingDataSource.updateBookingStatus(mContext,booking);
//                holder.notifiAccept.setVisibility(View.VISIBLE);
//                holder.btnAccept.setVisibility(View.GONE);
//                holder.btnDecline.setVisibility(View.GONE);
//                CustomToast.makeText(mContext,"You have accepted the request",CustomToast.LENGTH_SHORT,CustomToast.SUCCESS,true).show();
//           }
//        });
//
//        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                booking.setStatus(false);
//                bookingDataSource.updateBookingStatus(mContext,booking);
//                bookingList.remove(position); // Xóa mục khỏi danh sách
//                notifyItemRemoved(position); // Cập nhật giao diện
//                notifyItemRangeChanged(position, getItemCount()); // Cập nhật vị trí các mục còn lại
//                CustomToast.makeText(mContext,"You have decline the request",CustomToast.LENGTH_SHORT,CustomToast.ERROR,true).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(bookingList != null){
            return bookingList.size();
        }
        return 0;
    }

    public class BookingStaffViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUsername;
        private TextView txtBookingId;
        private TextView txtBookingTime;
        private TextView txtSlot;
//        private Button btnAccept, btnDecline;
        private TextView notifiAccept;
        public BookingStaffViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtBookingId = itemView.findViewById(R.id.txtBookingId);
            txtBookingTime = itemView.findViewById(R.id.txtBookingTime);
            txtSlot = itemView.findViewById(R.id.txtSlot);
//            btnAccept = itemView.findViewById(R.id.btnAccept);
//            btnDecline = itemView.findViewById(R.id.btnDecline);
            notifiAccept = itemView.findViewById(R.id.notifiAccept);
        }
    }
}
