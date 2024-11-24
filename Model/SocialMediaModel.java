package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.Content;
import Tracker.Mensuration.Entity.ContentBus;
import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.FCM.Notification;
import Tracker.Mensuration.Repositry.SocialMediaRepo;
import Tracker.Mensuration.Repositry.UserRepo;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class SocialMediaModel {

    @Autowired
    SocialMediaRepo socialMediaRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    Notification notification;

    private static final String UPLOAD_DIRECTORY = "C:\\Projects\\Mensuration\\src\\main\\resources\\static\\SocialMedia\\image"; // Folder where images will be stored

    public Content postModel(Content content, User user){
        System.out.println(user);
        if(content.getImage()==null){
            System.out.println("line number 36");
            content.setUsername(user.getUsername());
            return socialMediaRepo.save(content);
        }
        try {
           content.setImage(saveBase64Image(content.getImage()));
        } catch (IOException e) {
            System.out.println("Error on line number 36");
            throw new RuntimeException(e);
        }
        content.setUsername(user.getUsername());
        return socialMediaRepo.save(content);
    }

    public Content like(String id){
        Content content = socialMediaRepo.findById(new ObjectId(id)).get();
        double i = content.getLikes();
        i++;
        content.setLikes(i);
        return socialMediaRepo.save(content);
    }

    public List<ContentBus> getAllContent() {

        List<Content> contents = socialMediaRepo.findAll();
        Set<ContentBus> uniqueContentBusSet = new HashSet<>();

        for (Content content : contents) {
            ContentBus contentBus = new ContentBus();
            contentBus.setLikes(content.getLikes());
            contentBus.setUsername(content.getUsername());
            contentBus.setContentId(content.getId().toString());
            contentBus.setContent(content.getContent());
            contentBus.setImage(content.getImage());
            contentBus.setTime(content.getTime());
            uniqueContentBusSet.add(contentBus); // Use Set to ensure uniqueness
        }

        // Convert the set to a list and shuffle it
        List<ContentBus> contentBusList = new ArrayList<>(uniqueContentBusSet);
        Collections.shuffle(contentBusList);

        // Return only 3 unique items
        return contentBusList.stream().limit(3).collect(Collectors.toList());
    }

    public String comment(ContentBus contentBus){
        Content content = socialMediaRepo.findById(new ObjectId(contentBus.getContentId())).get();
        User user = userRepo.findByUsername(content.getUsername());
        User sender = userRepo.findById(new ObjectId(contentBus.getSenderId())).get();
        content.getComment().add(contentBus.getComment());
        socialMediaRepo.save(content);
        notification.sendFCMNotification(user.getFcmToken(), "Hi, There " + sender.getUsername() + " just replied on your post", contentBus.getComment());
        return contentBus.getContent();
    }

    public List<String> getAllComment(ContentBus contentBus){
        Content content = socialMediaRepo.findById(new ObjectId(contentBus.getContentId())).get();
        ArrayList<String> list = new ArrayList<>();
        for (String comment : content.getComment()) {
            list.add(comment);
        }
        return list;
    }

    // Method to handle the base64 image saving on the server
    public String saveBase64Image(String base64Image) throws IOException {
        // Remove the data URL prefix if it's there (i.e., "data:image/png;base64,")
        if (base64Image.contains("base64,")) {
            base64Image = base64Image.split("base64,")[1];
        }

        // Decode the base64 string into byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Generate a unique name for the image (UUID for uniqueness)
        String uniqueImageName = generateUniqueFileName();

        // Path where the image will be stored
        Path path = Paths.get(UPLOAD_DIRECTORY +"\\"+ uniqueImageName);

        // Ensure the upload directory exists
        Files.createDirectories(path.getParent());

        // Write the byte array to the file
        Files.write(path, imageBytes);

        // Return the unique filename
        return uniqueImageName;
    }

    // Method to generate a unique filename for each image
    private String generateUniqueFileName() {
        // Use UUID to ensure the name is unique or combine it with a timestamp
        return UUID.randomUUID().toString() + ".png"; // For PNG images, change if necessary
    }

    public List<String> getallComment(ContentBus contentBus){
        Content content= socialMediaRepo.findById(new ObjectId(contentBus.getContentId())).get();
        ArrayList <String> list = new ArrayList<>();
        for (String comment : content.getComment()) {
            list.add(comment);                // Convert ObjectId to String for the bus
            // Add the converted object to the list
        }
        return list;
    }
}
