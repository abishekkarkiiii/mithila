package Tracker.Mensuration.Controller;

import Tracker.Mensuration.Entity.SpecialPerson;
import Tracker.Mensuration.Entity.Tips;
import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Model.SocialMediaModel;
import Tracker.Mensuration.Model.UserAccountModel;
import Tracker.Mensuration.Repositry.SpecialPersonRepo;
import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("useraccount")
@CrossOrigin
public class UserAccountController {

    @Autowired
    UserAccountModel userAccountModel;
    @Autowired
    UserRepo userRepo;
//    @Autowired
//    Tips tips;

    @Autowired
    SpecialPersonRepo specialPersonRepo;

    @Autowired
    SocialMediaModel socialMediaModel;
    public String id;

    @PostMapping("createAccount")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        System.out.println(user);
        if (userAccountModel.accountCreation(user)) {
            id = user.getUsername();
            return ResponseEntity.ok(user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unsuccessful");
        }
    }

    @PostMapping("/updateDelay")
    @CrossOrigin(origins = "http://localhost:3000") // Explicit CORS for this method
    public void updateDelay(HttpServletRequest request, String date) {
        System.out.println("start");
        // Your update delay logic
    }

    @RequestMapping(value = "/updateDelay", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build(); // Respond to OPTIONS preflight request
    }

    @PostMapping("/registerToken")
    public void getFCMToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String username = payload.get("id");
        System.out.println(token + username + "                  hello I am token");
        userAccountModel.setToken(token, username);
    }

//    @GetMapping("/tips")
//    public void print(){
//        System.out.println(tips.getTips().length);
//    }

    @PostMapping("/Register")
    public void CreateDoctorAccount(@RequestBody SpecialPerson specialPerson) {
        specialPersonRepo.save(specialPerson);
    }



    @GetMapping("getALLRequest")
    public List<SpecialPerson> requestOfSpecialPerson(){
        List <SpecialPerson> special =specialPersonRepo.findAll();
        System.out.println(special);

       return specialPersonRepo.findAll();
    }

    @PostMapping("/profileOpen")
    public User userProfile(@RequestBody String email){
        System.out.println(id+"iddddddddddd");
      return  userRepo.findById(new ObjectId(id)).get();
    }
    @PostMapping("/updateimage")
    public void update(@RequestBody User user) throws IOException {
       User user1=userRepo.findById(user.getId()).get();
        user1.setImage(  socialMediaModel.saveBase64Image(user.getImage()));
       userRepo.save(user1);
    }
}
