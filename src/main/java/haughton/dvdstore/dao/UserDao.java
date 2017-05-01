package haughton.dvdstore.dao;


import haughton.dvdstore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User,Long>  {
    User findByUsername(String username);
}
