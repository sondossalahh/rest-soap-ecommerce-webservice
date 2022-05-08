package gov.iti.jets.domain.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.entities.CartItem;
import gov.iti.jets.domain.entities.LineItem;
import gov.iti.jets.domain.entities.Order;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.enums.OrderStatus;
import gov.iti.jets.domain.utils.ManagerFactory;
import gov.iti.jets.dtos.OrderDto;

public class OrderServiceImpl {
    
    private final static EntityManagerFactory entityManagerFactory = ManagerFactory.getEntityManagerFactory();
    private final static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Order> getAllOrders() {
        TypedQuery<Order> query = entityManager.createQuery("select m from Order m", Order.class);
        List<Order> orders = query.getResultList();
        return orders;
    }
   
    public boolean addOrder(int userId) {
        EntityTransaction transaction = entityManager.getTransaction();
        User user =entityManager.find(User.class,userId);
        Cart cart = user.getCart();
        Order order = new Order();
        List <LineItem> lineItems = order.getLineItemList();
        LineItem lineItem = new LineItem();
        for(CartItem cartItem : cart.getCartItems()){
            lineItem.setProduct(cartItem.getProduct());
            lineItem.setProductQuantity(cartItem.getQuantity());
            lineItem.setOrder(order);
            lineItems.add(lineItem);
        }
        order.setLineItemList(lineItems);

        double totalPrice = cart.getTotalPrice();
    
        order.setUser(user);
        user.getOrderList().add(order);
        order.setTotalPrice(totalPrice);
        user.setWallet(user.getWallet()-totalPrice);

        order.setOrderTime(LocalDate.now());
        order.setOrderStatus(OrderStatus.PROCESSED);
        
        transaction.begin();
        entityManager.persist(user);
        entityManager.persist(order);
        transaction.commit();
       
        return true;
    }

    public List<OrderDto> getOrder(int userId) {
        User user =entityManager.find(User.class,userId);
        List <OrderDto> orderDtos = new ArrayList<>();
       OrderDto orderDto = new OrderDto();

        List <Order> orders = user.getOrderList();
        List <LineItem> lineItems = new ArrayList<>();
        for (Order order : orders) {
             for (LineItem item: order.getLineItemList()) {
                lineItems.add(item);
             }
            orderDto.setLineItemList(lineItems);
            orderDto.setOrderStatus(order.getOrderStatus());
            orderDto.setOrderTime(order.getOrderTime());
            orderDto.setTotalPrice(order.getTotalPrice());
            orderDto.setUser(order.getUser());
            orderDto.setId(order.getId());
            orderDtos.add(orderDto);
        }
        
       
        return orderDtos;
    }

    public List<Order> getOrderByState(String state) {

        List <Order> orders = getAllOrders();
        List <Order> stateOrders = new ArrayList<>();
        for(Order order : orders){
           
            if(order.getOrderStatus()==OrderStatus.valueOf(state.toUpperCase())){
                stateOrders.add(order);
                
        }
           
        } 
       
        return stateOrders;
    }

    public boolean changeOrderState(int orderId,String state) {
        EntityTransaction transaction = entityManager.getTransaction();
        Order order =entityManager.find(Order.class,orderId);

        order.setOrderStatus(OrderStatus.valueOf(state.toUpperCase()));
        
        transaction.begin();
        entityManager.persist(order);
        transaction.commit();
       
        return true;
    }

}
