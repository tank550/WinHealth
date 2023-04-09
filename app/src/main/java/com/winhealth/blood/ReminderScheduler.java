package com.winhealth.blood;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class ReminderScheduler {
    private static final String CHANNEL_ID = "reminder_channel";
    private static final String CHANNEL_NAME = "Reminder Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel for reminder notifications";
    private static final int NOTIFICATION_ID = 1;

    public static void scheduleReminder(Context context, Calendar reminderDateTime) {
        // Créer un intent pour déclencher la réception du rappel
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Obtenir une instance d'AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Planifier les rappels
        scheduleAlarm(alarmManager, reminderDateTime.getTimeInMillis() - 24 * 60 * 60 * 1000, pendingIntent); // 24 heures avant
        scheduleAlarm(alarmManager, reminderDateTime.getTimeInMillis() - 12 * 60 * 60 * 1000, pendingIntent); // 12 heures avant
        scheduleAlarm(alarmManager, reminderDateTime.getTimeInMillis() - 8 * 60 * 60 * 1000, pendingIntent); // 8 heures avant
        scheduleAlarm(alarmManager, reminderDateTime.getTimeInMillis() - 1 * 60 * 60 * 1000, pendingIntent); // 1 heures avant
        scheduleAlarm(alarmManager, reminderDateTime.getTimeInMillis() - 30 * 60 * 1000, pendingIntent); // 30 minutes avant
        scheduleAlarm(alarmManager, reminderDateTime.getTimeInMillis() - 5 * 60 * 1000, pendingIntent); // 5 minutes avant
    }

    private static void scheduleAlarm(AlarmManager alarmManager, long triggerAtMillis, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    public static void cancelReminder(Context context) {
        // Annuler les rappels programmés
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public static class ReminderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Afficher la notification de rappel
            showReminderNotification(context);
        }

        private void showReminderNotification(Context context) {
            // Créer le canal de notification (pour les versions d'Android >= Oreo)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(CHANNEL_DESCRIPTION);
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            // Construire la notification de rappel
            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.img_3)
                    .setContentTitle("Rappel")
                    .setContentText("N'oubliez pas votre rendez-vous !");

            // Afficher la notification
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
