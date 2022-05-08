package gov.iti.jets.soap.webservices;

import java.util.List;

import gov.iti.jets.domain.entities.Order;
import gov.iti.jets.domain.services.OrderServiceImpl;
import gov.iti.jets.dtos.OrderDto;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;

@WebService
public class OrderService {
    private OrderServiceImpl orderService;
    String goodMessage="DONE";
    String badMessage="No orders found";

    public OrderService() {
        orderService = new OrderServiceImpl();
    }

    public String getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return orders.toString();
    }

    public String getOrders(String orderState) {
        List<Order> orders = orderService.getOrderByState(orderState);
        return orders.toString();
    }
    
    public String editOrderState(int orderId, String state){
        if( orderService.changeOrderState(orderId, state))
            return "product is edited successfully";

        return "can't edit this product";

    }

    
    public String addOrder(int id){
        if(orderService.addOrder(id))
            return "order is added successfully";
        
        return "can't add the order";

    }

    public String getOrder(int id){
        List<OrderDto> orders = orderService.getOrder(id);

            return orders.toString();

    }
}
