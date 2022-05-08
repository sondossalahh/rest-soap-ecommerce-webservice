package gov.iti.jets.rest.Controllers;
import java.util.List;

import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.entities.Order;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.services.CartServiceImpl;
import gov.iti.jets.domain.services.OrderServiceImpl;
import gov.iti.jets.domain.services.UserServiceImpl;
import gov.iti.jets.dtos.CartItemDto;
import gov.iti.jets.dtos.OrderDto;
import gov.iti.jets.dtos.UserGetDto;
import gov.iti.jets.dtos.UserPostDto;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;

@Path("/users")
public class UserController {
    
    private UserServiceImpl userService ;
    private CartServiceImpl cartService;
    private OrderServiceImpl orderService;

    String goodMessage="DONE";
    String badMessage="No user found";

    public UserController() {
        userService = new UserServiceImpl();
        cartService = new CartServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    public Response getAllUsers(@QueryParam("email") String email){
        if(email==null){
            List<User> users = userService.getAllUsers();
            return Response.ok().entity(users).build();
        }
        User user = userService.login(email);
        return Response.status(Response.Status.OK).entity(user).build();
    }
    

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("id") int id, @Context UriInfo uri){
        UserGetDto user = userService.getUser(id, uri);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response addUser(UserPostDto userDto){
        if(userService.addUser(userDto))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }

    @PUT
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response editUser(@PathParam("id") int id,UserPostDto userDto){
        if(userService.updateUser(id, userDto))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(badMessage).build();

    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("id") int id){
        if(userService.removeUser(id))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(badMessage).build();

    }

    @GET
    @Path("{id}/cart")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public String getCart(@PathParam("id") int id){
        Cart cart = cartService.getCartProducts(id);
        return cart.toString();
    }


    @GET
    @Path("{id}/orders")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response getOrders(@PathParam("id") int id){
        List <OrderDto> orders = orderService.getOrder(id);
        return Response.status(Response.Status.OK).entity(orders).build();
    }

    @POST
    @Path("/{id}/checkout")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response addOrder(@PathParam("id") int id){
        if(orderService.addOrder(id))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }

    @POST
    @Path("/{id}/cart")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN})
    public String addProductToCart(CartItemDto cartItem, @PathParam("id") int id){
        
        cartService.addProduct(cartItem, id);
        return "DONE" ;
    }
}
