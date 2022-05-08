package gov.iti.jets.dtos;

public class CartItemDto {
    private int productId;
    private int quantity;


    public CartItemDto() {
    }


    public CartItemDto(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    


    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
