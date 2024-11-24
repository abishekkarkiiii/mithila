package Tracker.Mensuration.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Document(collection = "post")
public class Content {
    private String Content;
    private double likes=0;
    private String username;
    private List<String> comment= new ArrayList<>();
    @Id
    private ObjectId id;
   private  String userId;
    private String contentId;
    private String time;
    private String image;
}
