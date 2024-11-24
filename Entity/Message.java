package Tracker.Mensuration.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String name;
    private String message;
    private String id;
    private String token;
}
