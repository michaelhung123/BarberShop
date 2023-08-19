package com.example.barbershop;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.cloudinary.android.MediaManager;
import com.example.barbershop.Adaptor.SelectedServicesViewModel;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    private SelectedServicesViewModel viewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        viewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(SelectedServicesViewModel.class);
        initConfig();
    }

    public SelectedServicesViewModel getViewModel() {
        return viewModel;
    }

    public void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "dgm68hajt");
        config.put("api_key", "445342655699255");
        config.put("api_secret", "-RkgzrKOgwbd32E9oK71iOW_WDQ");
        config.put("secure", true);
        MediaManager.init(this, config);
    }
}
