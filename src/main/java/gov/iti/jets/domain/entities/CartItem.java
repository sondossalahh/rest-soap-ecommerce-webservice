package gov.iti.jets.domain.entities;

import java.io.Serializable;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "cartItems", catalog = "appli")

public class CartItem implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cartItem_id",length = 10)
    private int id;

    private Product product;

    private int quantity;

    @JsonbTransient
    @ManyToOne(fetch=FetchType.LAZY)
    private Cart cart;

    public CartItem() {
    }


    public CartItem(int id, Product product, int quantity, Cart cart) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.cart = cart;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


    @Override
    public String toString() {
        return "\n{" +
            " id='" + getId() + "'" +
            " product='" + getProduct() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}\n";
    }
   

  
   

}
