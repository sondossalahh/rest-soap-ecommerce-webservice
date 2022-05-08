package gov.iti.jets.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "carts", catalog = "appli")
public class Cart implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id",length = 10)
    private int id; 

    // @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    // @JoinColumn(nullable = false, name = "user_id")
    // private User user;

    @JsonbTransient
    @OneToOne(fetch=FetchType.LAZY)
    private  User user;

    @Column(name = "total_price")
    private int totalPrice;

  
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();
    

    public Cart() {
     
    }

    public Cart(int id, int totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }
    

    public int getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return this.id;
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
    
    public void addToCart(CartItem cartItem){
        cartItems.add(cartItem);
    }

    public void removeFromCart(int cartItem){
        cartItems.remove(cartItem);
    }
    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }


    @Override
    public String toString() {
        return "{" +
            "cartId='" + getId() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", cartItems='" + getCartItems() + "'" +
            "}";
    }

   
}
