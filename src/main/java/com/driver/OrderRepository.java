package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {


    HashMap<String ,Order> orderMap = new HashMap<>();
    HashMap<String,DeliveryPartner> partnerMap = new HashMap<>();

    HashMap<String,List<String >> orderpartnerMap = new HashMap<>();

    HashSet<String > unassighned = new HashSet<>();

    public OrderRepository() {

    }

    public OrderRepository(HashMap<String, Order> orderMap, HashMap<String, DeliveryPartner> partnerMap, HashMap<String, List<String>> orderpartnerMap, HashMap<String, Order> unassighnedMap) {
        this.orderMap = orderMap;
        this.partnerMap = partnerMap;
        this.orderpartnerMap = orderpartnerMap;
        this.unassighned = unassighned;
    }

    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
        unassighned.add(order.getId());
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        partnerMap.get(partnerId).setNumberOfOrders(partnerMap.get(partnerId).getNumberOfOrders()+1);

            if(orderpartnerMap.containsKey(partnerId))
            {
                orderpartnerMap.get(partnerId).add(orderId);
            }
            else
            {
                List<String > l  = new ArrayList<>();
                l.add(orderId);
                orderpartnerMap.put(partnerId,l);
            }

        unassighned.remove(orderId);

    }

    public Order getOrderById(String orderId) {

            return orderMap.get(orderId);


    }

    public DeliveryPartner getPartnerById(String partnerId) {

            return partnerMap.get(partnerId);

    }

    public Integer addOrderCountByPartnerId(String partnerId) {
        Integer i = partnerMap.get(partnerId).getNumberOfOrders();
        return i;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orderList = new ArrayList<>();
        if(orderpartnerMap.containsKey(partnerId))
        {
            orderList = orderpartnerMap.get(partnerId);
        }

            return orderList;

    }

    public List<String> getAllOrders() {
        List<String> l = new ArrayList<>();
        if(orderMap.isEmpty() == false)
        {
            for(Order s : orderMap.values())
            {
                l.add(s.getId());
            }
        }

        return l;
    }

    public Integer getCountOfUnassighnedOrders() {
       return unassighned.size();

    }

    public Integer getCountofOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] timearr = time.split(":");
        int hours = Integer.valueOf(timearr[0]);
        int minutes = Integer.valueOf(timearr[1]);
        int given_time = (hours * 60) + minutes;
        Integer count = 0;
        if(orderpartnerMap.containsKey(partnerId))
        {
            List<String > l = orderpartnerMap.get(partnerId);
            for(String s : l)
            {
                if(orderMap.get(s).getDeliveryTime() > given_time)
                {
                    count++;
                }
            }
        }

        return count;
    }

    public String getLastDeliveryTime(String partnerId) {
            List<String> l = orderpartnerMap.get(partnerId);
            int last = orderMap.get(l.get(l.size()-1)).getDeliveryTime();

            StringBuilder str = new StringBuilder();
            int h = last/60;
            int m = last - (h*60);
            if(h<10) str.append("0");
            String s = Integer.toString(h);

            String t = Integer.toString(m);
            str.append(s);
            str.append(":");
            if(m<10) str.append("0");
            str.append(t);

            return str.toString();

    }

    public void deletePartnerByid(String partnerId) {

        if(orderpartnerMap.containsKey(partnerId))
        {
            List<String> l = orderpartnerMap.get(partnerId);
            for(String s : l)
            {
                unassighned.add(s);
            }
        }
        if(partnerMap.containsKey(partnerId))
        {
            partnerMap.remove(partnerId);
        }
        if(orderpartnerMap.containsKey(partnerId))
        {
            orderpartnerMap.remove(partnerId);
        }
    }

    public void deleteOrderByid(String orderId) {
        orderMap.remove(orderId);
            if(unassighned.contains(orderId))
            {
                unassighned.remove(orderId);
            }
            else {
                for(List<String> listoforders : orderpartnerMap.values())
                {
                    listoforders.remove(orderId);
                }
            }



    }
}
