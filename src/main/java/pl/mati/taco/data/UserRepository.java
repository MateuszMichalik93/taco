package pl.mati.taco.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mati.taco.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
