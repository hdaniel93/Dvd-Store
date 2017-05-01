package haughton.dvdstore.dao;

import haughton.dvdstore.model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by danie on 29/04/2017.
 */
@Repository
public interface OrderDao extends CrudRepository<Orders,Long> {
    //all sql query of methods below are auto generated based on method name via crudrepo
    Orders save(Orders order);
    List<Orders> findByUserIdOrderByDateDesc(Long userId);

}
