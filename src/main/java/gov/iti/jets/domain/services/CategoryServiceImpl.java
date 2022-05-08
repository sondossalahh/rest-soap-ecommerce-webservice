package gov.iti.jets.domain.services;

import java.util.List;
import java.util.Optional;

import gov.iti.jets.domain.entities.Category;
import gov.iti.jets.domain.utils.ManagerFactory;
import gov.iti.jets.dtos.CategoryDto;
import jakarta.persistence.*;
public class CategoryServiceImpl{

private final static EntityManagerFactory entityManagerFactory = ManagerFactory.getEntityManagerFactory();
private final static EntityManager entityManager = entityManagerFactory.createEntityManager();

public List<Category> getAllCategories() {
    TypedQuery<Category> query = entityManager.createQuery("select m from Category m", Category.class);
    List<Category> categories = query.getResultList();
    return categories;
}

public boolean addCategory(CategoryDto categoryDto) {
    EntityTransaction transaction = entityManager.getTransaction();
    Category category = new Category();
    category.setName(categoryDto.getName());
    transaction.begin();
    entityManager.persist(category);
    transaction.commit();
    return true;
}

public Category getCategoryByName(String categoryName) {
    String select = "SELECT c FROM Category c WHERE c.name=:categoryName";
    Query query = entityManager.createQuery(select,Category.class);
    query.setParameter("categoryName", categoryName);
    Category category = (Category) query.getSingleResult();
    return category;
}

public Category getCategoryById(int categoryId) {
    String select = "SELECT c FROM Category c WHERE c.id=:categoryId";
    Query query = entityManager.createQuery(select,Category.class);
    query.setParameter("categoryId", categoryId);
    Category category = (Category) query.getSingleResult();
    return category;
}



}