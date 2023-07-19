package com.example.barbershop.Domain;

import androidx.annotation.NonNull;

public class Voucher {
    private int id;
    private String name;
    private String code;
    private Double value;
    private Integer quantity;
    private String startTime;
    private String endTime;

    public Voucher(){

    }

    public Voucher(int id, String name, String code, Double value, int quantity, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.value = value;
        this.quantity = quantity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Voucher(String name, String code, Double value, int quantity, String startTime, String endTime) {
        this.name = name;
        this.code = code;
        this.value = value;
        this.quantity = quantity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @NonNull
    @Override
    public String toString() {
        return this.code + " - " + this.name + " - " + this.value;
    }
}
