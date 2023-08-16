package com.example.barbershop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adaptor.BookingForStaffAdapter;
import com.example.barbershop.Adaptor.CategoryAdapter;
import com.example.barbershop.Adaptor.StaffAdapter;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.Category;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.BookingDataSource;
import com.example.barbershop.Module.CategoryDataSource;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HomeStaffFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public HomeStaffFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeStaffFragment newInstance(String param1, String param2) {
        HomeStaffFragment fragment = new HomeStaffFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        TextView txtName = view.findViewById(R.id.txtName);

        String txtUsername = sharedPreferences.getString("username", "Guest");
        txtName.setText(txtUsername);

        TextView notifiBooking = view.findViewById(R.id.textView11);
        int listSizeBooking = getListBookingForStaff().size();
        notifiBooking.setText(String.format("Bạn có %d đơn đặt lịch", listSizeBooking));

        RecyclerView rcvBooking_for_staff = view.findViewById(R.id.rcvBooking_for_staff);
        BookingForStaffAdapter bookingForStaffAdapter = new BookingForStaffAdapter(getActivity());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvBooking_for_staff.setLayoutManager(linearLayoutManager1);
        rcvBooking_for_staff.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        bookingForStaffAdapter.setData(getListBookingForStaff());
        rcvBooking_for_staff.setAdapter(bookingForStaffAdapter);
        getListBookingForStaff().size();
    }

    private List<Booking> getListBookingForStaff() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int staffId = sharedPreferences.getInt("userId",-1);

        BookingDataSource bookingDataSource = new BookingDataSource(getActivity());
        List<Booking> bookingList = bookingDataSource.getBookingsByStaffId(getActivity(), staffId);

        List<Booking> filteredBookingList = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (booking.isStatus() == true) { // Thêm điều kiện kiểm tra isStatus là true hoặc null
                filteredBookingList.add(booking);
            }
        }
        Log.d("booking fitler", String.valueOf(filteredBookingList));
        return filteredBookingList;
    }
}
