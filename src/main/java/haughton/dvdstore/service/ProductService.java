package haughton.dvdstore.service;

import haughton.dvdstore.model.Product;

import java.util.List;

/**
 * Created by danie on 24/03/2017.
 */
public interface ProductService{
    List<Product> findAll();
    Product findById(Long id);
    List<Product> searchByTitle(String searchTerm);
    int reduceQuantityInStock(Long productId,int quantityToReduce);

}
