package com.example.barbershop.Domain;

public class TimeSlot {
    private Long slot;
    private boolean available;
    public TimeSlot(){
    }
    public TimeSlot(Long slot){
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "slot=" + slot +
                '}';
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
