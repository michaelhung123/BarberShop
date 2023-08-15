package com.example.barbershop.Common;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.example.barbershop.Adaptor.SelectedServicesViewModel;

public class MyApplication extends Application {
    private SelectedServicesViewModel viewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        viewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(SelectedServicesViewModel.class);
    }

    public SelectedServicesViewModel getViewModel() {
        return viewModel;
    }
}
