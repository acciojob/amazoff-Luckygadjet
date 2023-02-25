package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        int hours = 0;
        int i = 0;
        while(deliveryTime.charAt(i) != ':')
        {
            hours = hours * 10 + (deliveryTime.charAt(i) - '0');
            i++;
        }
        i++;
        int minutes =0;
        while(i<deliveryTime.length())
        {
            minutes = minutes * 10 + (deliveryTime.charAt(i) - '0');
            i++;
        }

        this.deliveryTime = (hours * 60) + minutes;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

}
