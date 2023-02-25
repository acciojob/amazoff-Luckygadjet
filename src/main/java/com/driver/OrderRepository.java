package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {


    HashMap<String ,Order> orderMap = new HashMap<>();
    HashMap<String,DeliveryPartner> partnerMap = new HashMap<>();

    HashMap<String,List<String >> orderpartnerMap = new HashMap<>();

    HashMap<String ,Order> unassighnedMap = new HashMap<>();

    public OrderRepository(HashMap<String, Order> orderMap, HashMap<String, DeliveryPartner> partnerMap, HashMap<String, List<String>> orderpartnerMap, HashMap<String, Order> unassighnedMap) {
        this.orderMap = orderMap;
        this.partnerMap = partnerMap;
        this.orderpartnerMap = orderpartnerMap;
        this.unassighnedMap = unassighnedMap;
    }

    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }

    public void addPartner(String partnerId,DeliveryPartner partner) {
        partnerMap.put(partnerId,partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderMap.containsKey(orderId) == true && partnerMap.containsKey(partnerId))
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
    }

    public Order getOrderById(String orderId) {
        if(orderMap.containsKey(orderId))
        {
            return orderMap.get(orderId);
        }
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(partnerMap.containsKey(partnerId))
        {
            return partnerMap.get(partnerId);
        }
        return null;
    }

    public Integer addOrderCountByPartnerId(String partnerId) {
        Integer i = 0;
        if(orderpartnerMap.containsKey(partnerId))
        {
            i = orderpartnerMap.get(partnerId).size();

        }
        return i;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        if(orderpartnerMap.containsKey(partnerId))
        {
            return orderpartnerMap.get(partnerId);
        }
        return null;
    }

    public List<String> getAllOrders() {

        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassighnedOrders() {
        Integer count = 0;
        for(String ordId : orderMap.keySet())
        {
            boolean present = false;
            for(String partId : orderpartnerMap.keySet())
            {
                if(orderpartnerMap.get(partId).contains(ordId))
                {
                    present = true;
                    break;
                }
            }
            if(present == false)
            {
                unassighnedMap.put(ordId,orderMap.get(ordId));
            }
        }
        return unassighnedMap.size();
    }

    public Integer getCountofOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int hours = 0;
        int i = 0;
        while(time.charAt(i) != ':')
        {
            hours = hours * 10 + (time.charAt(i) - '0');
            i++;
        }
        i++;
        int minutes =0;
        while(i<time.length())
        {
            minutes = minutes * 10 + (time.charAt(i) - '0');
            i++;
        }

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
        if(orderpartnerMap.containsKey(partnerId))
        {
            List<String> l = orderpartnerMap.get(partnerId);
            int max = Integer.MIN_VALUE;
            for(String s : l)
            {
                max = Math.max(max,orderMap.get(s).getDeliveryTime());
            }
            StringBuilder str = new StringBuilder();
            int h = max/60;
            int m = max - (h*60);

            String s = Integer.toString(h);
            String t = Integer.toString(m);
            str.append(s);
            str.append(":");
            str.append(t);

            return str.toString();
        }
        return null;
    }

    public void deletePartnerByid(String partnerId) {

        if(orderpartnerMap.containsKey(partnerId))
        {
            List<String> l = orderpartnerMap.get(partnerId);
            for(String s : l)
            {
                unassighnedMap.put(s,orderMap.get(s));
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
        if(orderMap.containsKey(orderId))
        {
            for(String partId : orderpartnerMap.keySet())
            {
                if(orderpartnerMap.get(partId).contains(orderId))
                {
                    l = orderpartnerMap.get(partId);
                    int i = 0;
                    for(String  s : l)
                    {
                        if(s.equals(orderId))
                        {
                            l.remove(i);
                            break;
                        }
                        else {
                            i++;
                        }

                    }
                    orderpartnerMap.put(partId,l);
                    orderMap.remove(orderId);
                    break;
                }
            }
        }
        else if(unassighnedMap.containsKey(orderId))
        {
            unassighnedMap.remove(orderId);
        }


    }
}
