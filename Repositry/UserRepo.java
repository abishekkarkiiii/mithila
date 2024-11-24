package Tracker.Mensuration.Repositry;

import Tracker.Mensuration.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<Tracker.Mensuration.Entity.User, ObjectId> {
    User findByUsername(String name);
    List<User> findByDate(int date);
}
