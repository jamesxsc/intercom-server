package uk.co.xsc.intercom.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.co.xsc.intercom.entity.PhoneNumber;

import java.util.Optional;

@Repository
public interface PhoneNumberRepo extends MongoRepository<PhoneNumber, String> {

    Optional<PhoneNumber> findByNumber(String number);

}
