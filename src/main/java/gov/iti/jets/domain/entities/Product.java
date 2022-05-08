package gov.iti.jets.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.ws.rs.core.Link;

@Entity
@Table(name = "products", catalog = "appli")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id",length =10 )
    private int id;

    @Column(name="product_name",length = 45)
    private String name;

    @Column(name="product_price",length = 50)
    private double price;

    @Column(name="category_name")
    private String categoryName;
    
    @JsonbTransient
    @ManyToOne(fetch=FetchType.LAZY)
    private Category category;

    public Product() {
    }

    public Product(int id, String name, double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

   
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
   


}
