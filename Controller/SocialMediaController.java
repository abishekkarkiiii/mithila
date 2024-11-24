package Tracker.Mensuration.Controller;

import Tracker.Mensuration.Entity.Content;
import Tracker.Mensuration.Entity.ContentBus;
import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Model.SocialMediaModel;
import Tracker.Mensuration.Repositry.SocialMediaRepo;
import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/socialMedia")
public class SocialMediaController {
    @Autowired
    SocialMediaModel socialMediaModel;
    @Autowired
    SocialMediaRepo socialMediaRepo;
    @Autowired
    UserRepo userRepo;
    User user=null;
    @GetMapping("userFinder")
    public String userfinder(HttpServletRequest request){
        this.user=userRepo.findById(new ObjectId(request.getSession(false).getAttribute("userId").toString())).get();
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
        accessor.getSessionAttributes().put("userId",user.getUserId());
        return user.getId().toString();
    }

//    @MessageMapping("/newPost")
//    @SendTo("/returnMessage/newPost")
    @PostMapping("/post")
    public Content Post(@RequestBody  Content content) {
        System.out.println(content);
        // Fetch the user from the session ID
        User user = userRepo.findById(new ObjectId(content.getUserId())).get();
        Content content1=socialMediaModel.postModel(content, user);
        // Pass the user along with content to save the post
        content1.setContentId(content1.getId().toString());
        return content1 ;

    }


    @MessageMapping("/like")
    @SendTo("/returnMessage/like")
    public Content like(String id){
       return socialMediaModel.like(id);
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<List<Content>> getAllPosts(@RequestParam int page, @RequestParam int size) {
        // Log page and size for debugging
        System.out.println("Page: " + page + ", Size: " + size);

        // Adjust pagination to ensure zero-based index
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("time")));  // Sort by time descending

        // Fetch posts from the repository
        Page<Content> contentPage = socialMediaRepo.findAll(pageable);

        // Log the size of the fetched content list
        System.out.println("Fetched Content Size: " + contentPage.getContent().size());

        List<Content> posts = contentPage.getContent();

        // Optionally log the 'time' field of each post
        posts.forEach(post -> System.out.println("Post Time: " + post.getTime()));

        // Set contentId for each post
        posts = posts.stream()
                .peek(x -> x.setContentId(x.getId().toString()))  // Set contentId
                .collect(Collectors.toList());

        // Log the number of fetched posts
        System.out.println("Fetched Posts: " + posts.size());

        // Return the posts as a ResponseEntity
        return ResponseEntity.ok(posts);  // Properly returning the list in a ResponseEntity
    }





    @MessageMapping("/comment")
     @SendTo("/returnMessage/comment")
    public ContentBus comment(ContentBus contentBus){
        socialMediaModel.comment(contentBus);
        return contentBus;
    }

    @PostMapping("getAllComment")
    public List<String> getallComment(@RequestBody ContentBus contentBus){
        System.out.println(contentBus);
        return socialMediaModel.getallComment(contentBus);
    }


    @PostMapping("/profile-image")
    public void imagesaver(String base64){

    }




}
