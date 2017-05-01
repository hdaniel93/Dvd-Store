package haughton.dvdstore.service;

import haughton.dvdstore.model.Orders;

import java.util.List;

/**
 * Created by danie on 29/04/2017.
 */
public interface OrderService {
    void save(Orders order);
    List<Orders> findByUserIdOrderByDateDesc(Long userId);
    Orders findById(Long id);
}
