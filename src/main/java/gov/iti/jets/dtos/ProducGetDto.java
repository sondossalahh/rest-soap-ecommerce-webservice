package gov.iti.jets.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.core.Link;

public class ProducGetDto {

    private List<Link> links = new ArrayList<>();

    private String name;
    private double price;
    private int categoryId=0;
    private String categoryName;

    public ProducGetDto() {
    }

    public ProducGetDto(List<Link> links, String name, double price, int categoryId) {
        this.links = links;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
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

    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
   
    public void addLink(Link uri2){
        links.add(uri2);
     
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



    @Override
    public String toString() {
        return "{" +
            " links='" + getLinks() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", categoryId='" + getCategoryId() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
   

}
