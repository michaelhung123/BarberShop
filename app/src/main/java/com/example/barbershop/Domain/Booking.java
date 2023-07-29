package com.example.barbershop.Domain;


import com.google.firebase.Timestamp;

public class Booking {
    private int id;
    private int userId;
    private int staffId;
    private String time;
    private Timestamp createTime;
    private Long slot;
    private boolean status;

    public Booking(int id, int userId, int staffId, String time, Long slot, boolean status) {
        this.id = id;
        this.userId = userId;
        this.staffId = staffId;
        this.time = time;
        this.createTime = createTime;
        this.slot = slot;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public Booking(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
