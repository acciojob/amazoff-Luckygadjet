package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String[] timearr = deliveryTime.split(":");
        int hours = Integer.valueOf(timearr[0]);
        int minutes = Integer.valueOf(timearr[1]);
        this.deliveryTime = (hours * 60) + minutes;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

}
