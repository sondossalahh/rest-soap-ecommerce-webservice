package gov.iti.jets.domain.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.List;

import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.entities.CartItem;
import gov.iti.jets.domain.entities.Product;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.utils.ManagerFactory;
import gov.iti.jets.dtos.CartItemDto;


public class CartServiceImpl {
    private final static EntityManagerFactory entityManagerFactory = ManagerFactory.getEntityManagerFactory();
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Cart> getAllCarts() {
        TypedQuery<Cart> query = entityManager.createQuery("select m from Cart m", Cart.class);
        List<Cart> carts = query.getResultList();
        return carts;
    }


    public boolean addProductToCart(CartItemDto cartItemDto , int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        

        User user =entityManager.find(User.class,id);

        Cart cart = user.getCart();
        System.out.println(cart);
        CartItem cartItem = new CartItem();

        Product product =entityManager.find(Product.class,cartItemDto.getProductId());

        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);

       
        System.out.println(cartItem);
        System.out.println(cart);
         
       int price = (int) (product.getPrice() * cartItemDto.getQuantity());
 
        cart.setTotalPrice(cart.getTotalPrice()+price);
        transaction.begin();
        entityManager.persist(cartItem);
        entityManager.persist(cart);
        transaction.commit();
     
        return true;
    }

    public void addProduct(CartItemDto cartItemDto , int userId){
        User user =entityManager.find(User.class,userId);
        Cart cart = user.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        Product product =entityManager.find(Product.class,cartItemDto.getProductId());
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDto.getQuantity());

        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(2);
              EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(cart);
        entityManager.persist(cartItem);
        transaction.commit();

    }

    public boolean RemoveProductFromCart(int userId,int cartItemId) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user =entityManager.find(User.class,userId);

        Cart cart = user.getCart();
       
        cart.removeFromCart(cartItemId);
        
        CartItem cartItem =entityManager.find(CartItem.class,cartItemId);
 
        int price = (int) (cart.getTotalPrice() - cartItem.getProduct().getPrice()*cartItem.getQuantity());
 
       
      
       cart.setTotalPrice(cart.getTotalPrice()+price);
       
        entityManager.persist(cart);
        entityManager.remove(cartItem);
        entityManager.persist(user);
        transaction.commit();
        entityManager.close();
        return true;
    }


    public Cart getCartProducts(int userId) {

        User user =entityManager.find(User.class,userId);

        Cart cart = user.getCart();
        
        return cart;
    }
}
