package gov.iti.jets.rest.Controllers;

import java.util.List;

import gov.iti.jets.domain.entities.Product;
import gov.iti.jets.domain.services.ProductServiceImpl;
import gov.iti.jets.dtos.ProducGetDto;
import gov.iti.jets.dtos.ProductPostDto;
//import gov.iti.jets.utils.Link;
import jakarta.ws.rs.core.*;
import jakarta.decorator.Delegate;
import jakarta.ws.rs.*;

@Path("/products")
public class ProductController {
    
    private ProductServiceImpl productService ;
    String goodMessage="DONE";
    String badMessage="No category found";

    public ProductController() {
        productService = new ProductServiceImpl();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    public Response getProductsList(@QueryParam("name") String name){
        if(name != null){
            List <Product> products = productService.searchProductByName(name);
            return Response.status(Response.Status.OK).entity(products).build();
        }
        List<Product> products = productService.getAllProducts();
        return Response.ok().entity(products).build();
    }
    

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response getProductById(@PathParam("id") int id,@Context UriInfo uri){
        ProducGetDto product = productService.getProduct(id,uri);
        return Response.status(Response.Status.OK).entity(product).build();
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response addProduct(ProductPostDto productDto){
        if(productService.addProduct(productDto))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.NOT_FOUND).entity(badMessage).build();

    }

    @PUT
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response editProduct(@PathParam("id") int id,ProductPostDto productDto){
        if(productService.editProduct(id, productDto))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(badMessage).build();

    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response deleteProduct(@PathParam("id") int id){
        if(productService.removeProduct(id))
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(badMessage).build();

    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response deleteAllProducts(){
        if(productService.removeAllProducts())
            return Response.status(Response.Status.OK).entity(goodMessage).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(badMessage).build();

    }

    public Response getByCategory(int categoryId){
        List<Product> products = productService.getProductsbyCategory(categoryId);
        return Response.ok().entity(products).build();
    }

}
