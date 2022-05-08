package gov.iti.jets.soap.webservices;

import java.util.List;

import gov.iti.jets.domain.entities.Product;
import gov.iti.jets.domain.services.ProductServiceImpl;
import gov.iti.jets.dtos.ProductPostDto;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;

@WebService
public class ProductService {
    private ProductServiceImpl productService ;
    String goodMessage="DONE";
    String badMessage="No category found";

    public ProductService() {
        productService = new ProductServiceImpl();
    }

    public String getProductsList(String name){
        if(name != null){
            List <Product> products = productService.searchProductByName(name);
            return products.toString();
        }
        List<Product> products = productService.getAllProducts();
          return products.toString();
    }
    

    public String getProductById(int id){
        Product product = productService.getProductById(id);
        return product.toString();
    }

    public String addProduct(ProductPostDto productDto){
        if(productService.addProduct(productDto))
            return "product is added successfully";
        
        return "can't add the product";
    }

    public String editProduct(int id,ProductPostDto productDto){
        if(productService.editProduct(id, productDto))
            return "product is edited successfully";
        
        return "can't edit the product";
    }

    public String deleteProduct(int id){
        if(productService.removeProduct(id))
            return "product is deleted successfully";
        
        return "can't delete the product";
    }

    public String deleteAllProducts(){
        if(productService.removeAllProducts())
            return "all products are deleted successfully";
        
        return "can't delete all products";
    }

    public String getByCategory(int categoryId){
        List<Product> products = productService.getProductsbyCategory(categoryId);
        return products.toString();
    }
}
