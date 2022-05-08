package gov.iti.jets.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.domain.enums.OrderStatus;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "orders", catalog = "appli")
public class Order implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id",length = 10)
    private int id;

    @JsonbTransient
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name="order_time", length=50)
    private LocalDate orderTime;

    @JsonbTransient
    @OneToMany(fetch=FetchType.LAZY, mappedBy="order" ,cascade = CascadeType.ALL)
    private List<LineItem> lineItemList = new ArrayList<>();

    @Enumerated
    OrderStatus orderStatus;
    
    public Order() {
    }

    

    public Order(int id, User user, double totalPrice, LocalDate orderTime, List<LineItem> lineItemList) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.lineItemList = lineItemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
   

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public List<LineItem> getLineItemList() {
        return lineItemList;
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


    @Override
    public String toString() {
        return "\n\n\n{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", orderTime='" + getOrderTime() + "'" +
            ", lineItemList='" + getLineItemList() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}\n\n\n";
    }
    
    
    
}
