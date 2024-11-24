//package Tracker.Mensuration.Configuration;
//
//import Tracker.Mensuration.Controller.SocialMediaController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpSession;
//
//@Component
//public class UserSessionInterceptor implements ChannelInterceptor {
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
//
//        // Accessing the HTTP session from the WebSocket session
//        HttpSession httpSession = (HttpSession) accessor.getHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER);
//
//        // Retrieve the userId from the HTTP session
//        if (httpSession != null) {
//            String userId =  // Replace "userId" with your actual session attribute key
//
//            if (userId != null) {
//                // Set the userId in the WebSocket session attributes
//                accessor.getSessionAttributes().put("userId", userId);
//            }
//        }
//
//        return message;
//    }
//}
