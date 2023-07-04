package com.example.barbershop.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barbershop.Domain.CategoryDomain;
import com.example.barbershop.R;

import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
     ArrayList<CategoryDomain>categoryDomains;
     public CategoryAdaptor(ArrayList<CategoryDomain> categoryDomains){
         this.categoryDomains =categoryDomains;
     }

    public ArrayList<CategoryDomain> getCategoryDomains() {
        return categoryDomains;
    }

    public void setCategoryDomains(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder holder, int position) {
        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        String picUrl = "";
        switch (position){
            case 0: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
            case 1: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
            case 2: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
            case 3: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
            case 4: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
            case 5: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
            case 6: {
                picUrl = "user";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.main_background));
                break;
            }
        }
        int drawbleResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "mipmap",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawbleResourceId)
                .into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
