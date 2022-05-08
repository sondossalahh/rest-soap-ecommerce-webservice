package gov.iti.jets.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.domain.entities.LineItem;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.enums.OrderStatus;

public class OrderDto {
    int id;

    private double totalPrice;
    private LocalDate orderTime;
    private List<LineItem> lineItemList = new ArrayList<>();
    OrderStatus orderStatus;
    User user ;


    public OrderDto() {
    }

    public OrderDto(double totalPrice, LocalDate orderTime, List<LineItem> lineItemList, OrderStatus orderStatus) {
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.lineItemList = lineItemList;
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public List<LineItem> getLineItemList() {
        return this.lineItemList;
    }

    public void setLineItemList(List<LineItem> lineItemList) {
        this.lineItemList = lineItemList;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "\n\n\n{" +
            " id='" + getId() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", orderTime='" + getOrderTime() + "'" +
            ", lineItemList='" + getLineItemList() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", user='" + getUser() + "'" +
            "}\n\n\n";
    }
   
}
