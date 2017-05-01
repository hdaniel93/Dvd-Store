package haughton.dvdstore.service;

import haughton.dvdstore.dao.ProductDao;
import haughton.dvdstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by danie on 25/03/2017.
 */
@Service
public class ProductServiceImpl implements ProductService  {
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return productDao.findOne(id);
    }

    @Override
    public List<Product> searchByTitle(String searchTerm) {
        return productDao.searchByTitle(searchTerm);
    }

    @Override
    public int reduceQuantityInStock(Long productId, int quantityToReduce) {
        return productDao.reduceQuantityInStock(productId,quantityToReduce);
    }
}
