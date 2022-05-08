package gov.iti.jets.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.core.Link;

public class UserGetDto {
    
    private String userName;

    private String password;

    private String email;

    private String address;

    private double wallet;

    private List<Link> links = new ArrayList<>();
    

    public UserGetDto() {
    }

    public UserGetDto(String userName, String password, String email, String address, double wallet) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.wallet = wallet;
    }


    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWallet() {
        return this.wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
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


    @Override
    public String toString() {
        return "{" +
            " userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", wallet='" + getWallet() + "'" +
            ", links='" + getLinks() + "'" +
            "}";
    }


}
