//package Tracker.Mensuration.Controller;
//
//
//import Tracker.Mensuration.Entity.Message;
//import Tracker.Mensuration.Entity.User;
//import Tracker.Mensuration.FCM.Notification;
//import Tracker.Mensuration.Repositry.UserRepo;
//import jakarta.servlet.http.HttpServletRequest;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/chat")
//public class ChatController {
//
//    @Autowired
//    UserRepo userRepo;
//    @Autowired
//    Notification notification;
//
//    @GetMapping("/handShake")
//    public Message sessionSetter(HttpServletRequest request){
//        User user=userRepo.findById(new ObjectId(request.getSession().getAttribute("userId").toString())).get();
//        Message message=new Message();
//        message.setName(user.getUsername());
//        message.setId(user.getId().toString());
//        message.setToken(user.getFcmToken());
//        return message;
//
//    }
//    @MessageMapping("/doctor")
//    @SendTo("/returnMessage/chatDoctor")
//    public Message sendMessage(Message message) {
//        return message;
//    }
//
//    @PostMapping("/sendNotification")
//    public void sendNotifiacation(@RequestBody Message message){
//        System.out.println(message.getName()+"acertoken");
//        notification.sendFCMNotification(message.getToken(),message.getName(),message.getMessage());
//    }
//}
