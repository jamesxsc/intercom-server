package uk.co.xsc.intercom.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import uk.co.xsc.intercom.entity.PhoneNumber;
import uk.co.xsc.intercom.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, Long> {

    // Security: include the below line to not project password hash
//    @Query(fields = "{ 'password': '' }")
    // The below query is exempt because it is used to authenticate
    @Query(value = "{'email': {$regex: ?0, $options: 'i'}}")
    Optional<User> findByEmail(String email);

    @Update("{ $push: { phoneNumbers: ?1 } }")
    void findAndPushPhoneNumberByEmail(String email, PhoneNumber phoneNumber);

}
