package Tracker.Mensuration.Repositry;

import Tracker.Mensuration.Entity.Content;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SocialMediaRepo extends MongoRepository<Content, ObjectId> {
   Page<Content> findAll(Pageable pageable); // Pagination support
   List<Content> findByUsername(String name);
}
