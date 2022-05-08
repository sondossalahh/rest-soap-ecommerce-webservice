package gov.iti.jets.dtos;

public class ProductPostDto {

    private String name;
    private double price;
    private int categoryId=0;

    public ProductPostDto() {
    }


    public ProductPostDto(String name, double price, int categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductPostDto(String name, double price) {
        this.name = name;
        this.price = price;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
}
