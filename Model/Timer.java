package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.Tips;
import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.FCM.Notification;
import Tracker.Mensuration.Repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.TimeZone;

@Component
public class Timer {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    Notification notification;

    @Autowired
    Tips tips;

    private String cronExpression = "15 * * * * *"; // CRON expression for 6 AM every day
    private ScheduledFuture<?> scheduledTask;
    private final TaskScheduler scheduler = new ThreadPoolTaskScheduler();

    @PostConstruct
    public void start() {
        notification.initializeFirebase();
        ((ThreadPoolTaskScheduler) scheduler).initialize();
        scheduleTask();
    }

    private void scheduleTask() {
        if (scheduledTask != null) scheduledTask.cancel(true); // Cancel previous task if exists
        scheduledTask = ((ThreadPoolTaskScheduler) scheduler).schedule(() -> executeTask(),
                new CronTrigger(cronExpression, TimeZone.getDefault())); // Schedule task at 6 AM
    }

    private void executeTask() {
        System.out.println("startedddddddddddddddddd");

             notification.sendFCMNotification("fNs9fXgWT5qtQn-EWmF42l:APA91bE8oHJKijk1aC1YNBRkIqayJD-miB1Oq6ggvkIy6kVl86LdGdDfVAg6po2wIpiGudeR5R3cnFcG_sDIJfg9WAPIY_Vf1O-iaIDftOSay2tFRuWN30I","कालभैरव जयन्तीको शुभकामना!"
                     ,"उनको आशीर्वादले तपाईंको जीवनमा शान्ति र समृद्धि छाओस।");


    }

}
