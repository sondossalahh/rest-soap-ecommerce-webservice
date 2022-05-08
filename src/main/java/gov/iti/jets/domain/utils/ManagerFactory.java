package gov.iti.jets.domain.utils;

import jakarta.persistence.*;

public class ManagerFactory {
    
    private static final EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("appli");
    
    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    } 
}
