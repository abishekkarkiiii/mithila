package Tracker.Mensuration.FCM;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class Notification {

        public void initializeFirebase() {
            try (FileInputStream serviceAccount = new FileInputStream("your dir location")) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendFCMNotification(String token,String name,String content) {
            Message message = Message.builder()
                    .putData("name",name)
                    .putData("content",content)
                    .setToken(token)
                    .build();
            FirebaseMessaging.getInstance().sendAsync(message);
            System.out.println("FCM notification sent to token: " + token);
        }
    public void sendFCMNotification(String token) {
        Message message = Message.builder()
                .putData("action", "triggerPrint")
                .putData("name","abishek")
                .setToken(token)
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);
        System.out.println("FCM notification sent to token: " + token);
    }


    }


