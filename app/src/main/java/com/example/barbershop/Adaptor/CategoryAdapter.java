package com.example.barbershop.Adaptor;

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

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;
import com.example.barbershop.R;
import com.example.barbershop.ServiceActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private Context mContext;
    private int categoryId;


    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Category> list){
        this.categoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);
        if(category == null){
            return;
        }

        CategoryDataSource categoryDataSource = new CategoryDataSource(mContext);
        String fileImage = categoryDataSource.getFilePictureForCategory(category.getId());
        holder.title_cate.setText(category.getName());
        Picasso.get().load(fileImage).resize(300,300).into(holder.imgPicCate);
        holder.imgPicCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Service> serviceList;
                ServiceDataSource serviceDataSource = new ServiceDataSource(mContext);
                serviceList = serviceDataSource.selectServices(mContext);
                for (Service service : serviceList){
                    if(category.getId() == service.getCategory_id()){
                        //Lưu thông tin của người dùng vừa nhập vào SharedPreferences để hiển thị lên giao diện của HomeFragment
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("categoryId", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("categoryId", category.getId()); // Lưu tên người dùng
                        editor.apply();
                        Intent intent = new Intent(mContext, ServiceActivity.class);
                        mContext.startActivity(intent);
                    }
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        if (categoryList != null){
            return categoryList.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imgPicCate;
        private TextView title_cate;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicCate = itemView.findViewById(R.id.imgPicCate);
            title_cate = itemView.findViewById(R.id.title_cate);
        }
    }
}
