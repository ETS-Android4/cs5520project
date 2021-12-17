package android.example.todolist;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ReminderWorker extends Worker {
    private static final String EXTRA_NOTIFICATION_ID = "2";
    private static final String ACTION_SNOOZE = "Snooze";
    private static final String CHANNEL_ID = "1";
    private int notification = 0;
    public ReminderWorker(
            Context context,
            WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        String title= getInputData().getString("Title");

        String ddl_time = getInputData().getString("Ddl_time");

        String ddl_date = getInputData().getString("Ddl_date");

        Intent snoozeIntent = new Intent(getApplicationContext(), AlertReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(), 0, snoozeIntent, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText("Deadline on " + ddl_date + " "+ddl_time)
                .setFullScreenIntent(snoozePendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
//                .setContentIntent(pendingIntent);
//                .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
//                        snoozePendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notification, builder.build());
        notification++;

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}