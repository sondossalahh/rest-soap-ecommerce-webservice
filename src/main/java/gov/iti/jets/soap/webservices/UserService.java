package gov.iti.jets.soap.webservices;

import java.util.List;

import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.services.CartServiceImpl;
import gov.iti.jets.domain.services.OrderServiceImpl;
import gov.iti.jets.domain.services.UserServiceImpl;
import gov.iti.jets.dtos.CartItemDto;
import gov.iti.jets.dtos.OrderDto;
import gov.iti.jets.dtos.UserPostDto;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;

@WebService
public class UserService {
    private UserServiceImpl userService ;
    private CartServiceImpl cartService;
    private OrderServiceImpl orderService;

    String goodMessage="DONE";
    String badMessage="No user found";

    public UserService() {
        userService = new UserServiceImpl();
        cartService = new CartServiceImpl();
        orderService = new OrderServiceImpl();
    }
    
    public String getAllUsers(String email){
        if(email==null){
            List<User> users = userService.getAllUsers();
            return users.toString();
        }
        User user = userService.login(email);
        return user.toString();
    }
    
  
    public String addUser(UserPostDto userDto){
        if(userService.addUser(userDto))
            return "user is added successfully";
        
        return "can't add the user";

    }

    public String editUser(int id,UserPostDto userDto){
        if(userService.updateUser(id, userDto))
            return "user is updated successfully";
        
        return "can't update the user";
    }

    public String deleteUser(int id){
        if(userService.removeUser(id))
            return "user is removed successfully";
        
        return "can't remove the user";
    }

    public String getCart(int id){
        Cart cart = cartService.getCartProducts(id);
        return cart.toString();
    }

    public String getOrders(int id){
        List <OrderDto> orders = orderService.getOrder(id);
        return orders.toString();
    }

    public String addOrder(int id){
        if(orderService.addOrder(id))
            return "order is added successfully";
        
        return "can't add the order";

    }

  
    public Response addProductToCart(CartItemDto cartItem,int id){
        if(cartService.addProductToCart(cartItem, id))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }
}
