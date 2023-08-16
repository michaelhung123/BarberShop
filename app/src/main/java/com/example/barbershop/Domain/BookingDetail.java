package com.example.barbershop.Domain;

public class BookingDetail {
    private int id;
    private int bookingId;
    private int serviceId;
    private String serviceName;

    public BookingDetail(int id, int bookingId, int serviceId) {
        this.id = id;
        this.bookingId = bookingId;
        this.serviceId = serviceId;
    }
    public BookingDetail(int id, int bookingId, int serviceId, String serviceName) {
        this.id = id;
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }
    public BookingDetail(){

    }

    @Override
    public String toString() {
        return "BookingDetail{" +
                "serviceName='" + serviceName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
