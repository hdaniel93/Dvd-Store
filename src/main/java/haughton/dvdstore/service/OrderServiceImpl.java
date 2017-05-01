package haughton.dvdstore.service;

import haughton.dvdstore.dao.OrderDao;
import haughton.dvdstore.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by danie on 29/04/2017.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Override
    public void save(Orders order) {
    orderDao.save(order);
    }

    @Override
    public List<Orders> findByUserIdOrderByDateDesc(Long userId) {
       return orderDao.findByUserIdOrderByDateDesc(userId);
    }

    @Override
    public Orders findById(Long id) {
       return orderDao.findOne(id);
    }


}
