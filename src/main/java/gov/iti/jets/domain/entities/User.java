package gov.iti.jets.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.domain.enums.UserType;


@Entity
@Table(name = "users", catalog = "appli")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",length = 10)
    private int id; 

    @Column(name="user_name")
    private String userName;

    private String password;

    private String email;

    @Column(name = "detailed_address")
    private String detailedAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    private double wallet;

    // @OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    // private List<Order> orderList = new ArrayList<>();

    // @JsonbTransient
    // private Cart cart;

    @JsonbTransient
    @OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    private List<Order> orderList = new ArrayList<>();

    @JsonbTransient
    @OneToOne(fetch=FetchType.LAZY)
    private Cart cart;

    public User() {
      
    }

    public User(int id, String userName, String password, String email, String detailedAddress, UserType userType, double wallet) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.detailedAddress = detailedAddress;
        this.userType = userType;
        this.wallet = wallet;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetailedAddress() {
        return this.detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public double getWallet() {
        return this.wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
   

    public List<Order> getOrderList() {
        return this.orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
    


}
