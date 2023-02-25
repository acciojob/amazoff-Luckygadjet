package com.driver;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    OrderRepository repository = new OrderRepository();

    public void addorder(Order order) {
        repository.addOrder(order);
    }

    public void addpartner(String partnerId) {
        repository.addPartner(partnerId);
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
        Integer i = repository.getOrderCountByPartnerId(partnerId);
        return i;
    }

    public List<String> getordersbypartnerid(String partnerId) {
        return repository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getallorders() {
        return repository.getAllOrders();
    }

    public Integer getcountofunassignedorders() {
        Integer i = repository.getCountOfUnassignedOrders();
        return i;
    }

    public Integer getcountofordersleftaftergiventimebypartnerid(String time, String partnerId) {
        Integer i = repository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
        return i;
    }

    public String getlastdeliverytime(String partnerId) {
        return repository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletepartnerbyid(String partnerId) {

        repository.deletePartnerById(partnerId);
    }

    public void deleteorderbyid(String orderId) {
        repository.deleteOrderById(orderId);
    }
}
