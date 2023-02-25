package com.driver;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    public void addorder(Order order) {
        repository.addOrder(order);
    }

    public void addpartner(String partnerId,DeliveryPartner partner) {
        repository.addPartner(partnerId,partner);
    }

    public void addorderpartnerpair(String orderId, String partnerId) {
        repository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getorderbyid(String orderId) {
        return  repository.getOrderById(orderId);
    }

    public DeliveryPartner getpartnerbyid(String partnerId) {
        return repository.getPartnerById(partnerId);
    }

    public Integer addordercountbypartnerid(String partnerId) {
        Integer i = repository.addOrderCountByPartnerId(partnerId);
        return i;
    }

    public List<String> getordersbypartnerid(String partnerId) {
        return repository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getallorders() {
        return repository.getAllOrders();
    }

    public Integer getcountofunassignedorders() {
        Integer i = repository.getCountOfUnassighnedOrders();
        return i;
    }

    public Integer getcountofordersleftaftergiventimebypartnerid(String time, String partnerId) {
        Integer i = repository.getCountofOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
        return i;
    }

    public String getlastdeliverytime(String partnerId) {
        return repository.getLastDeliveryTime(partnerId);
    }

    public void deletepartnerbyid(String partnerId) {

        repository.deletePartnerByid(partnerId);
    }

    public void deleteorderbyid(String orderId) {
        repository.deleteOrderByid(orderId);
    }
}
