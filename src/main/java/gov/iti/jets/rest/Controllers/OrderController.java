package gov.iti.jets.rest.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

import gov.iti.jets.domain.entities.Order;
import gov.iti.jets.domain.services.OrderServiceImpl;
import gov.iti.jets.dtos.OrderDto;
import jakarta.decorator.Delegate;
import jakarta.ws.rs.*;

@Path("/orders")
public class OrderController {
    private OrderServiceImpl orderService;
    String goodMessage="DONE";
    String badMessage="No orders found";

    public OrderController() {
        orderService = new OrderServiceImpl();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public String getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return orders.toString();
    }

    @GET
    @Path("/{orderState}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
   
    public String getOrders(@PathParam("orderState") String orderState) {
        List<Order> orders = orderService.getOrderByState(orderState);
        return orders.toString();
    }
    

    @PUT
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    public Response editOrderState(@PathParam("id") int orderId, @QueryParam("state") String state){
        if( orderService.changeOrderState(orderId, state))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }

    @POST
    @Path("/users/{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response addOrder(@PathParam("id") int id){
        if(orderService.addOrder(id))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }

    
    @GET
    @Path("/users/{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public String getOrder(@PathParam("id") int id){
        List<OrderDto> orders = orderService.getOrder(id);

            return orders.toString();

    }
}
