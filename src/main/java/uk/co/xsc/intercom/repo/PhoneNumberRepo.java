package uk.co.xsc.intercom.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.co.xsc.intercom.entity.PhoneNumber;

@Repository
public interface PhoneNumberRepo extends MongoRepository<PhoneNumber, String> {
}
