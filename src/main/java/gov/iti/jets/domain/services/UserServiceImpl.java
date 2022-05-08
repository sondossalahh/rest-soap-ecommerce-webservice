package gov.iti.jets.domain.services;

import java.util.List;

import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.enums.UserType;
import gov.iti.jets.domain.utils.ManagerFactory;
import gov.iti.jets.dtos.UserGetDto;
import gov.iti.jets.dtos.UserPostDto;
import gov.iti.jets.rest.Exceptions.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriInfo;

public class UserServiceImpl {
    private final static EntityManagerFactory entityManagerFactory = ManagerFactory.getEntityManagerFactory();
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<User> getAllUsers(){
        TypedQuery<User> users = entityManager.createQuery("select p from User p ",User.class);
        List<User> result =users.getResultList();
        return result;
    }

    public User login(String email) throws CustomException {
        Query query = entityManager.createQuery("SELECT c FROM User c WHERE c.email=:email");
        query.setParameter("email", email);
        if (query.getResultList().size() == 0) 
            throw new CustomException("User does not exist");
    
        User user = (User) query.getSingleResult();
        return user;
    }

    public boolean addUser(UserPostDto userDto) throws CustomException {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("SELECT c FROM User c WHERE c.email=:email");
        query.setParameter("email", userDto.getEmail());
        if (query.getResultList().size() != 0) 
            throw new CustomException("User already exists");
        
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setDetailedAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setWallet(userDto.getWallet());
        user.setUserType(UserType.CLIENT);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        entityManager.persist(user);
        entityManager.persist(cart);
        transaction.commit();
        return true;
        
    }

    public UserGetDto getUser(int id , UriInfo uri) {
        User user =entityManager.find(User.class,id);
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setAddress(user.getDetailedAddress());
        userGetDto.setEmail(user.getEmail());
        userGetDto.setPassword(user.getPassword());
        userGetDto.setUserName(user.getUserName());
        userGetDto.setWallet(user.getWallet());

        Link cartLink = Link.fromUriBuilder(uri.getAbsolutePathBuilder().path("/cart")).rel("Cart").build(); 
        Link ordersLink = Link.fromUriBuilder(uri.getAbsolutePathBuilder().path("/orders")).rel("Orders").build(); 

        userGetDto.addLink(cartLink);
        userGetDto.addLink(ordersLink);
        return userGetDto;
    }

    public boolean updateUser(Integer id, UserPostDto userDto) {
        EntityTransaction transaction = entityManager.getTransaction();
        Query query = entityManager.createQuery("SELECT c FROM User c WHERE c.id=:id");
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();

        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setDetailedAddress(userDto.getAddress());
        user.setWallet(userDto.getWallet());
        user.setUserType(UserType.valueOf(userDto.getUserName().toUpperCase()));

        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        return true;
    }

    public boolean removeUser(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String select = "SELECT c FROM User c where c.id=:id";
        Query query = entityManager.createQuery(select);
        query.setParameter("id", id);
        List<User> users = query.getResultList();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(users.get(0));
        transaction.commit();
        entityManager.close();
        return true;
    }
}
