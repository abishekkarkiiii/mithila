package Tracker.Mensuration.Repositry;

import Tracker.Mensuration.Entity.SpecialPerson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecialPersonRepo extends MongoRepository< SpecialPerson,ObjectId> {
}
