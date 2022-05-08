package gov.iti.jets.domain.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import gov.iti.jets.domain.entities.Category;
import gov.iti.jets.domain.entities.Product;
import gov.iti.jets.domain.utils.ManagerFactory;
import gov.iti.jets.dtos.ProducGetDto;
import gov.iti.jets.dtos.ProductPostDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

public class ProductServiceImpl {
    private final static EntityManagerFactory entityManagerFactory = ManagerFactory.getEntityManagerFactory();
    private final  EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();


    public List<Product> getAllProducts(){
        TypedQuery<Product> products = entityManager.createQuery("select p from Product p ",Product.class);
        List<Product> result =products.getResultList();
        return result;

    }
    

    public boolean addProduct(ProductPostDto productDto) {
        
        EntityTransaction transaction = entityManager.getTransaction();
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
       
        if(productDto.getCategoryId()!=0){
            Category category = categoryService.getCategoryById(productDto.getCategoryId());
            product.setCategoryName(category.getName()); 
            product.setCategory(category);
        }
        
        transaction.begin();
        entityManager.persist(product);
        transaction.commit();
        return true;
    }
    public List<Product> LoadProductsByCategory(String category) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.categoryName =:categoryName", Product.class);
        query.setParameter("categoryName", category);
        List<Product> result = query.getResultList();
        return result;
    }

    public List<Product> getProductbyName(String name) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.name like : name ", Product.class);
        query.setParameter("name", "%" + name + "%");
        List<Product> result = query.getResultList();
        return result;
    }

    public Product getProductById(int id) {
        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        query.select(productRoot).where(cb.equal(productRoot.get("id"), id));
        List<Product> result1 = entityManager.createQuery(query).getResultList();
        return result1.get(0);
    }

    public int getNext(int id) {
        TypedQuery<Product> query  = entityManager.createQuery("select p from Product p where p.id > :id order by p.id",Product.class);
        query.setParameter("id", id);
        if (query.getResultList().size() == 0) {return 0;}
        Product product = query.setMaxResults(1).getSingleResult();
        return product.getId();
    }

    public int getPrevious(int id) {
        TypedQuery<Product> query  = entityManager.createQuery("select p from Product p where p.id > :id order by p.id desc",Product.class);
        if (query.getResultList().size() == 0) {return 0;}
        Product product = query.setMaxResults(1).getSingleResult();
        return product.getId();
    }


    public ProducGetDto getProduct(int id ,UriInfo uri ) {
        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        query.select(productRoot).where(cb.equal(productRoot.get("id"), id));
        List<Product> result1 = entityManager.createQuery(query).getResultList();
        Product product = result1.get(0);
        ProducGetDto productItemDto = new ProducGetDto();
        productItemDto.setCategoryId(product.getCategory().getId());
        productItemDto.setName(product.getName());
        productItemDto.setPrice(product.getPrice());
        productItemDto.setCategoryName(product.getCategoryName());
        Link self = Link.fromUriBuilder(uri.getAbsolutePathBuilder()).rel("self").build();
       
        
        int nextProductId = getNext(id);
        int previousProductId = getPrevious(id);

        //Link l = Link.fromUriBuilder(uri.getAbsolutePathBuilder().path("/cart")).rel("cart").build(); 

        Link next = Link.fromUriBuilder(uri.getBaseUriBuilder().path("/products").path("/"+nextProductId)).rel("next").build(); 
        Link prev = Link.fromUriBuilder(uri.getBaseUriBuilder().path("/products").path("/"+previousProductId)).rel("previous").build(); 


        if (nextProductId>0) productItemDto.addLink(next);
        if (previousProductId>0) productItemDto.addLink(prev);

       return productItemDto;
    }

   

    public boolean removeProduct(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String select = "SELECT p FROM Product p where p.id=:id";
        Query query = entityManager.createQuery(select);
        query.setParameter("id", id);
        List<Product> product = query.getResultList();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(product.get(0));
        transaction.commit();
        entityManager.close();
        return true;
    }

    public boolean removeAllProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String select = "SELECT p FROM Product p";
        Query query = entityManager.createQuery(select);
        List<Product> product = query.getResultList();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (Product p : product) {
            entityManager.remove(p);
        }
       transaction.commit();
        entityManager.close();
        return true;
    }

    public boolean editProduct(int id, ProductPostDto productDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        query.select(productRoot).where(cb.equal(productRoot.get("id"), id));
        List<Product> result1 = entityManager.createQuery(query).getResultList();
        Product updatedProduct = result1.get(0);

        updatedProduct.setName(productDto.getName());
        
        updatedProduct.setPrice(productDto.getPrice());
       
        if(productDto.getCategoryId()!=0){
            Category category = categoryService.getCategoryById(productDto.getCategoryId());
            updatedProduct.setCategory(category);
        }

        entityManager.persist(updatedProduct);
        transaction.commit();
        entityManager.close();
        return true;
        
    }

    public List<Product> getProductsbyCategory(int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        String categoryName = category.getName();
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.categoryName like : categoryName ", Product.class);
        query.setParameter("categoryName", categoryName);
        List<Product> result = query.getResultList();
        return result;
    }

    public List<Product> searchProductByName(String searchProduct) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.name like : name ", Product.class);
        query.setParameter("name", "%" + searchProduct + "%");
        List<Product> result = query.getResultList();
        return result;
    }

    public List<Product> searchProductByPrice(double max, double min) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.price between : min and : max",Product.class);
        query.setParameter("max",max);
        query.setParameter("min",min);
        List<Product> result =query.getResultList();
        return result;
    }
  

}
