package Tracker.Mensuration.Entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ContentBus {
    private double likes;
    private String comment;
    private String username;
    private String contentId;
    private String content;
    private String time;
    private String senderId;
    private String image;
}
