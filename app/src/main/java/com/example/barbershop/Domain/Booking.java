package com.example.barbershop.Domain;


import com.google.firebase.Timestamp;

public class Booking {
//    private static Booking instance;
    private int id;
    private int userId;
    private int staffId;
    private String time;
    private String createTime;
    private Long slot;
    private Double total;
    private boolean status = true;

    public Booking(int id, int userId, int staffId, String time, String createTime, Long slot, double total, boolean status) {
        this.id = id;
        this.userId = userId;
        this.staffId = staffId;
        this.time = time;
        this.createTime = createTime;
        this.slot = slot;
        this.total = total;
        this.status = status;
    }
    public Booking(){
    }


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", staffId=" + staffId +
                ", time='" + time + '\'' +
                ", total=" + total +
                ", createTime=" + createTime +
                ", slot=" + slot +
                ", status=" + status +
                '}';
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
