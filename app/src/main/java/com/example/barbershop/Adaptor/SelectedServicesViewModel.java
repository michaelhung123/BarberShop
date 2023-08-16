package com.example.barbershop.Adaptor;

import androidx.lifecycle.ViewModel;

import com.example.barbershop.Domain.Service;

import java.util.ArrayList;

public class SelectedServicesViewModel extends ViewModel {
    private ArrayList<Service> selectedServices = new ArrayList<>();
    private double total;

    public ArrayList<Service> getSelectedServices() {
        return selectedServices;
    }
    public double getTotalServices() {
        return total;
    }

    public void setSelectedServices(ArrayList<Service> selectedServices) {
        this.selectedServices = selectedServices;
    }
    public void setTotalServices(double total){
        this.total = total;
    }
}
