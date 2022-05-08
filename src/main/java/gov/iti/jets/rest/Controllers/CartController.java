package gov.iti.jets.rest.Controllers;

import java.util.List;

import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.entities.CartItem;
import gov.iti.jets.domain.entities.User;
import gov.iti.jets.domain.services.CartServiceImpl;
import gov.iti.jets.dtos.CartItemDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;
@Path("/carts")
public class CartController {
    private CartServiceImpl cartService;
    String goodMessage="DONE";
    String badMessage="No category found";

    public CartController() {
        cartService = new CartServiceImpl();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public String getCategoriesList(){
        List<Cart> carts = cartService.getAllCarts();
        return carts.toString();
    }

    @GET
    @Path("/users/{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public String getCart(@PathParam("id") int userId){
        Cart cart = cartService.getCartProducts(userId);
        return cart.toString();
    }


    @DELETE
    @Path("/users/{id}/cartItems/{cid}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response deleteFromCart(@PathParam("id") int userId, @PathParam("cid") int cartItemId){
        if( cartService.RemoveProductFromCart(userId,cartItemId))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }

    @POST
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public void addProductToCart(CartItemDto cartItem, @PathParam("id") int id){
        cartService.addProduct(cartItem,id);
           
    }
}
