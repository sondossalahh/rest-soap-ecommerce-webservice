package gov.iti.jets.soap.webservices;

import java.util.List;

import gov.iti.jets.domain.entities.Category;
import gov.iti.jets.domain.services.CategoryServiceImpl;
import gov.iti.jets.dtos.CategoryDto;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;
import net.bytebuddy.asm.Advice.Return;

@WebService
public class CategoryService {
    private CategoryServiceImpl categoryService ;

    public CategoryService() {
        categoryService = new CategoryServiceImpl();
    }

    public String getCategoriesList(){
        List<Category> categories = categoryService.getAllCategories();
        return categories.toString();
    }
    
    public String getCategoryById(int id){
        Category category = categoryService.getCategoryById(id);
        return category.toString();
    }

    public String addCategory(CategoryDto CategoryDto){
        if (categoryService.addCategory(CategoryDto))
            return "category is added successfully";
        
        return "can't add the category";
    }
}
