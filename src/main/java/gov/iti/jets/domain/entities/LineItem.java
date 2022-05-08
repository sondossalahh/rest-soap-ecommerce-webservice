package gov.iti.jets.domain.entities;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lineitems", catalog = "appli")
public class LineItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id",length = 10)
    private int id;

    @ManyToOne
    @JsonbTransient
    private Product product;
   
    @Column(name = "product_quantity")
    private int productQuantity;

    @JsonbTransient
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    
    public LineItem() {
    }

   

    public LineItem(int id, Product product, int productQuantity, Order order) {
        this.id = id;
        this.product = product;
        this.productQuantity = productQuantity;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }


    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
  

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @Override
    public String toString() {
        return "\n{" +
            ", product='" + getProduct() + "'" +
            ", productQuantity='" + getProductQuantity() + "'" +
            "}\n";
    }
   
  
}
