package gov.iti.jets.rest.Controllers;

import java.util.List;
import java.util.Optional;

import gov.iti.jets.domain.entities.Category;
import gov.iti.jets.domain.services.CategoryServiceImpl;
import gov.iti.jets.dtos.CategoryDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;

@Path("/categories")
public class CategoryController {
    
    private CategoryServiceImpl categoryService ;

    public CategoryController() {
        categoryService = new CategoryServiceImpl();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response getCategoriesList(){
        List<Category> categories = categoryService.getAllCategories();
        return Response.ok().entity(categories).build();
    }
    

    @GET
    @Path("/{id}/products")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response getProductsList(@PathParam("id") int id){
        ProductController productController = new ProductController();
        return productController.getByCategory(id);
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public String getCategoryById(@PathParam("id") int id){
        Category category = categoryService.getCategoryById(id);
        return category.toString();
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON , MediaType.TEXT_HTML , MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON})
    public void addCategory(CategoryDto CategoryDto){
        categoryService.addCategory(CategoryDto);
    }

}
