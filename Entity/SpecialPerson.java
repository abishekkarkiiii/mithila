package Tracker.Mensuration.Entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Document(collection = "doctorRequestList")
@Component
public class SpecialPerson {
    private String name;
    private  String categories;
    private String phonenumber;
    private String workdone;
    private String email;
    private String password;
}



