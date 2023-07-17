package com.example.barbershop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barbershop.Adaptor.CategoryAdapter;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Category;
import com.example.barbershop.Module.CategoryDataSource;
import com.google.android.material.textfield.TextInputEditText;
import com.example.barbershop.Adaptor.StaffAdapter;
import com.example.barbershop.Domain.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtName;
    TextInputEditText txtUsername;
    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewCategoryList = view.findViewById(R.id.recycleView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<Staff> staff = new ArrayList<>();
        staff.add(new Staff("Hair cut", "pic1"));
        staff.add(new Staff("Hair cut", "pic2"));
        staff.add(new Staff("Hair cut", "pic3"));
        staff.add(new Staff("Hair cut", "pic4"));
        staff.add(new Staff("Hair cut", "pic5"));
        staff.add(new Staff("Hair cut", "pic6"));

        StaffAdapter adapter = new StaffAdapter(staff);
        recyclerViewCategoryList.setAdapter(adapter);

        txtName = view.findViewById(R.id.txtName);
        // Đọc thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Hiển thị thông tin người dùng lên giao diện người dùng
        txtName.setText(username);

        RecyclerView rcvCategory = view.findViewById(R.id.rcvCategory);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvCategory.setLayoutManager(linearLayoutManager1);
        rcvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);
    }

    private List<Category> getListCategory(){
        CategoryDataSource categoryDataSource = new CategoryDataSource(getActivity());
        List<Category> categoryList = categoryDataSource.selectCategories(getActivity());
        // Đọc thông tin người dùng từ SharedPreferences
        return categoryList;
    }
}