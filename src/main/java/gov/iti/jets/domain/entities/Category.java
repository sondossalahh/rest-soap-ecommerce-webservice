package gov.iti.jets.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "categories", catalog = "appli")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id",length = 10)
    private int id;

    @Column(name="category_name",length = 25)
    private String name;
    
    @JsonbTransient
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "category")
    private List<Product> products = new ArrayList<>();
    
    public Category(){

    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }

}
