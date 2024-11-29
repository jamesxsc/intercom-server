package uk.co.xsc.intercom.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.co.xsc.intercom.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
