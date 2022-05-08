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

    
    // public boolean addCartProduct(CartProducts cartProducts) {
    //     EntityTransaction transaction = entityManager.getTransaction();
    //     transaction.begin();
    //     entityManager.persist(cartProducts);
    //     transaction.commit();
    //     return true;
    // }

    // public boolean chechIfProductExist(Integer userId, Integer productId) {
    //     CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
    //     CriteriaQuery<CartProducts> query = cb.createQuery(CartProducts.class);
    //     Root<CartProducts> cartProducts = query.from(CartProducts.class);
    //     Predicate predicate = cb.and(cb.equal (cartProducts.get("user").<Integer>get("id"),userId),cb.equal ( cartProducts.get("product").<String>get("id"),productId));
    //     query.select(cartProducts).where(predicate);
    //     List<CartProducts> result = entityManager.createQuery(query).getResultList();
    //     return result.size() > 0;
    // }

    // @Override
    // public boolean updateProduct(int productId, int userId, int quantity) {
    //     CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    //     CriteriaUpdate<CartProducts> cu = cb.createCriteriaUpdate(CartProducts.class);
    //     Root<CartProducts> c = cu.from(CartProducts.class);
    //     cu.set(c.get("quantity"), quantity).where(cb.and(cb.equal (c.get("user").<Integer>get("id"),userId),cb.equal ( c.get("product").<String>get("id"),productId)));
    //     entityManager.createQuery(cu).executeUpdate();
    //     return false;
    // }

    // public boolean addCart(User user) {
    //     EntityTransaction transaction = entityManager.getTransaction();
    //     transaction.begin();
    //     Cart cart = new Cart();
    //     cart.setUser(user);
    //     entityManager.persist(cart);
    //     User updatedUser =entityManager.find(User.class,user.getId());
    //     updatedUser.setCartId(cart.getId());
    //     entityManager.persist(updatedUser);
    //     transaction.commit();
    //     return true;
    // }

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
    // public boolean addProductToCart(CartItemDto cartItemDto, int userId) {
    //     EntityTransaction transaction = entityManager.getTransaction();
    //     transaction.begin();

    //     User user =entityManager.find(User.class,userId);

    //     Cart cart = user.getCart();
    //     System.out.println(cart);
    //     CartItem cartItem = new CartItem();

    //     Product product =entityManager.find(Product.class,cartItemDto.getProductId());

    //     cartItem.setProduct(product);
    //     cartItem.setQuantity(cartItemDto.getQuantity());
    //     cartItem.setCart(cart);
    //     cart.addToCart(cartItem);
    //     System.out.println(cartItem);
    //     System.out.println(cart);
    //      cart.setUser(user);
       
    //    int price = (int) (product.getPrice() * cartItemDto.getQuantity());
 
    //    cart.setTotalPrice(cart.getTotalPrice()+price);
    //     entityManager.persist(cartItem);
    //     entityManager.persist(cart);
    //     entityManager.persist(user);
    //     transaction.commit();
    //     entityManager.close();
    //     return true;
    // }

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


    // @Override
    // public List<CartProducts> getCartProductsList(int id) {
    //     User user =entityManager.find(User.class,id);
    //     List<CartProducts> cartProductsList = user.getCartProductsList();
    //     return cartProductsList;
    // }
}
