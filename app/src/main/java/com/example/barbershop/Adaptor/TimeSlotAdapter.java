package com.example.barbershop.Adaptor;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Common.Common;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.TimeSlot;
import com.example.barbershop.Module.BookingDataSource;
import com.example.barbershop.R;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    Context mContext;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;
    BookingDataSource bookingDataSource = new BookingDataSource(mContext);
    public TimeSlotAdapter(Context mContext, List<TimeSlot> timeSlotList) {
        this.mContext = mContext;
        this.timeSlotList = timeSlotList;
        cardViewList = new ArrayList<>();
    }
    public TimeSlotAdapter(Context mContext){
        this.mContext = mContext;
        this.timeSlotList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TimeSlotAdapter.TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.time_slot, parent, false);
        return new TimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.TimeSlotViewHolder holder, int position) {
//        TimeSlot timeSlot = timeSlotList

        holder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(position)).toString());
        int color = Color.parseColor("#90EF92");

        if(timeSlotList.size() == 0){
            //Nếu tất cả vị trí đều "available", thì chỉ show list
            holder.txt_time_slot_description.setText("Available");
            holder.txt_time_slot_description.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.txt_time_slot.setTextColor(mContext.getResources().getColor(android.R.color.black));
//            holder.card_time_slot.setCardBackgroundColor(mContext.getResources().getColor(android.R.color.darker_gray));
        }else // Nếu position is full (đã được book)
        {
//            holder.card_time_slot.setCardBackgroundColor(color);
            for (TimeSlot slotValue: timeSlotList){
                int slot = Integer.parseInt(slotValue.getSlot().toString());
                if(slot == position){
                    holder.card_time_slot.setTag(Common.DISABLE_TAG);
                    holder.card_time_slot.setCardBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
                    holder.txt_time_slot.setText("Full");
                    holder.txt_time_slot.setTextColor(mContext.getResources().getColor(android.R.color.white));
                    holder.txt_time_slot_description.setTextColor(mContext.getResources().getColor(android.R.color.white));
                }
            }
        }

        if(!cardViewList.contains(holder.card_time_slot)) // check cardView có tồn tại trong cardList
            cardViewList.add(holder.card_time_slot);

        Log.d("cardviewList", String.valueOf(cardViewList.size()));

        holder.card_time_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra nếu khe thời gian đã "Full" thì không thực hiện hành động
                if (holder.card_time_slot.getTag() != null && holder.card_time_slot.getTag().equals(Common.DISABLE_TAG)) {
                    return; // Không làm gì nếu đã "Full"
                }

                for (CardView cardView: cardViewList){
                    if (cardView.getTag() == null)
                        cardView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
                }
                int colorClick = Color.parseColor("#F2DB1A");
                holder.card_time_slot.setBackgroundColor(colorClick);
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setSlot(Long.valueOf(position));

                SharedPreferences sharedPreferences = mContext.getSharedPreferences("BookingData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("slot", timeSlot.getSlot()); // Lưu tên người dùng
                editor.apply();
            }
        });
    }
    @Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder{
        TextView txt_time_slot,txt_time_slot_description;
        CardView card_time_slot;
        public TimeSlotViewHolder(@NonNull View itemView){
            super(itemView);
            card_time_slot = (CardView) itemView.findViewById(R.id.card_time_slot);
            txt_time_slot = (TextView) itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description = (TextView) itemView.findViewById(R.id.txt_time_slot_description);

        }
    }

}
