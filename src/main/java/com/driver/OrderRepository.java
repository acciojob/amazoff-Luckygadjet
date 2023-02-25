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
        if(orderMap.containsKey(orderId)&& partnerMap.containsKey(partnerId))
        {
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
        }
        unassighned.remove(orderId);
        int ord = partnerMap.get(partnerId).getNumberOfOrders();
        partnerMap.get(partnerId).setNumberOfOrders(ord+1);
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

            return orderpartnerMap.get(partnerId);

    }

    public List<String> getAllOrders() {

        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassighnedOrders() {
        Integer count = unassighned.size();
        return count;
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

            String s = Integer.toString(h);
            String t = Integer.toString(m);
            str.append(s);
            str.append(":");
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
        List<String> l = new ArrayList<>();
        if(orderMap.containsKey(orderId)) {
            for (String partId : orderpartnerMap.keySet()) {
                if (orderpartnerMap.get(partId).contains(orderId)) {
                    l = orderpartnerMap.get(partId);
                    int i = 0;
                    for (String s : l) {
                        if (s.equals(orderId)) {
                            l.remove(i);
                            orderpartnerMap.put(partId, l);
                            break;
                        } else {
                            i++;
                        }

                    }

                    orderMap.remove(orderId);
                    break;
                }
            }
        }
            if(unassighned.contains(orderId))
            {
                unassighned.remove(orderId);
            }



    }
}
