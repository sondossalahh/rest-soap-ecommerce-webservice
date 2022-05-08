package gov.iti.jets.soap.webservices;

import java.util.List;

import gov.iti.jets.domain.entities.Cart;
import gov.iti.jets.domain.services.CartServiceImpl;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;

@WebService
public class CartService {
    
    private CartServiceImpl cartService;
    String goodMessage="DONE";
    String badMessage="No category found";

    public CartService() {
        cartService = new CartServiceImpl();
    }

    
    public String getCategoriesList(){
        List<Cart> carts = cartService.getAllCarts();
        return carts.toString();
    }

   
    public String getCart(int userId){
        Cart cart = cartService.getCartProducts(userId);
        return cart.toString();
    }

    public String deleteFromCart(int userId, int cartItemId){
        if( cartService.RemoveProductFromCart(userId,cartItemId))
            return "product is deleted successfully";

        return "can't find the product";

    }
}
