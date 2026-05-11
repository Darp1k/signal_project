package com.alerts;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// this class handles the alert announcment, if it is a repeating alert, it will repeat the alert at the specified intervals until the count runs out
public class AlertManager {
    // Background scheduler for handling timed repeats
    private final ScheduledExecutorService scheduler;

    public AlertManager() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void dispatchAlert(Alert alert) {
        // Immediately output the alert
        System.out.println("ALERT: Patient ID " + alert.getPatientId() + 
                           " - " + alert.getCondition() + 
                           " at " + alert.getTimestamp());

        // check if this alert has a decorator for repeating
        if (alert instanceof RepeatedAlertDecorator) {
            RepeatedAlertDecorator repeatingAlert = (RepeatedAlertDecorator) alert;

            // if there are repeats left, schedule the next one
            if (repeatingAlert.getRepeatCount() > 0) {
                repeatingAlert.decrementCount(); // Reduce the counter by 1

                // Schedule this method to run again in the future with the same alert
                scheduler.schedule(() -> {
                    dispatchAlert(repeatingAlert); // Recursive asynchronous call
                }, repeatingAlert.getRepeatInterval(), TimeUnit.MILLISECONDS);
            }
        }
    }

    // method to close the scheduler when the application is shutting down
    public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}