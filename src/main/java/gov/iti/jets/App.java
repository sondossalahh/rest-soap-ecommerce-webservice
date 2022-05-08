package gov.iti.jets;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {
    public static void main(String[] args) {
         EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("appli");
    
    }
   
}
