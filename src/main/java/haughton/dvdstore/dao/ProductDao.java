package haughton.dvdstore.dao;

import haughton.dvdstore.model.Product;
import haughton.dvdstore.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by danie on 31/03/2017.
 */
@Repository
public interface ProductDao extends CrudRepository<Product, Long> {
    @Query("select p from Product p where p.title like %:searchTerm%")
    List<Product> searchByTitle( @Param("searchTerm") String searchTerm);
    @Modifying
    @Query("UPDATE Product P SET quantityInStock = quantityInStock - :quantityToReduce where id = :productId")
    int reduceQuantityInStock (@Param("productId") Long productId, @Param("quantityToReduce") int quantityToReduce);
}
