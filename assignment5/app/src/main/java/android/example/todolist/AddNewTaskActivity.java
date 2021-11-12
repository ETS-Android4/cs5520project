package android.example.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.todolist.data.Task;
import android.example.todolist.databinding.NewTaskBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import kotlinx.coroutines.scheduling.CoroutineScheduler;

public class AddNewTaskActivity extends AppCompatActivity {
    private static final String EXTRA_NOTIFICATION_ID = "2";
    private static final String ACTION_SNOOZE = "Snooze";
    private static final String CHANNEL_ID = "1";
    private NewTaskBinding binding;
    private TaskViewModel toDoViewModel;
    private Task currentTask;
    private Boolean newTask;
    private Calendar calendar;
    private Calendar timeCalendar;
    private int notificationId;
    private Calendar reminderCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notificationId = 0;
        // Get an instance to the shared ViewModel
        toDoViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setViewmodel(toDoViewModel);
        calendar = new GregorianCalendar();
        timeCalendar = Calendar.getInstance();
        if (!getIncomingIntent()) {
            setNoteProperties();
        }
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newTask) {
                    toDoViewModel.createTask();
                } else {
                    currentTask = new Task(currentTask.getId(), toDoViewModel.taskTitle.getValue(),
                            toDoViewModel.taskDescription.getValue(),
                            toDoViewModel.taskDate.getValue(),
                            toDoViewModel.taskTime.getValue(),
                            toDoViewModel.reminderDate.getValue(),
                            toDoViewModel.reminderTime.getValue());
                    toDoViewModel.updateTask(currentTask);
                }

                startAlarm(reminderCalendar);
                setReminder(toDoViewModel.taskTitle.getValue(),toDoViewModel.taskDate.getValue(),
                        toDoViewModel.taskTime.getValue());
//                WorkRequest uploadWorkRequest =
//                        new OneTimeWorkRequest.Builder(ReminderWorker.class)
//                                .setInitialDelay(0, TimeUnit.MINUTES)
//                                .setInputData(
//                                        new Data.Builder()
//                                                .putString("Title", toDoViewModel.taskTitle.getValue())
//                                                .putString("Ddl_date", toDoViewModel.taskDate.getValue())
//                                                .putString("Ddl_time", toDoViewModel.taskTime.getValue())
//                                                .build()
//                                )
//                                .build();
//                WorkManager
//                        .getInstance(getApplicationContext())
//                        .enqueue(uploadWorkRequest);
//                backToMainActivity(view);
            }
        });

    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            currentTask = getIntent().getParcelableExtra("selected_note");
            newTask = false;
            return false;
        }
        newTask = true;
        return true;
    }

    private void setNoteProperties() {
        toDoViewModel.taskTitle.setValue(currentTask.getTitle());
        toDoViewModel.taskDescription.setValue(currentTask.getDescription());
        toDoViewModel.taskDate.setValue(currentTask.getDdl());
        toDoViewModel.taskTime.setValue(currentTask.getDdl_time());
        toDoViewModel.reminderDate.setValue(currentTask.getReminder_date());
        toDoViewModel.reminderTime.setValue(currentTask.getReminder_time());



        //TO ADD other attributes.

    }
private void setReminder(String title, String ddl_date, String ddl_time){
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
    notificationManager.notify(notificationId, builder.build());
    notificationId++;

}

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "A reminder is set", Toast.LENGTH_SHORT).show();
    }


    public void chooseDate(View view) {
        final View dialogView = View.inflate(this, R.layout.date_picker, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Choose Date");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                binding.taskDate.setText(new SimpleDateFormat("E, dd MMMM yyyy").format(calendar.getTime()));

            }
        });
        builder.show();
    }

    public void chooseTime(View view) {
        final View dialogView = View.inflate(this, R.layout.time_picker, null);
        final TimePicker timePicker = dialogView.findViewById(R.id.time_picker);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Choose Time");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                timeCalendar = Calendar.getInstance();
                timeCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                timeCalendar.set(Calendar.MINUTE, timePicker.getMinute());
                binding.taskTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(timeCalendar.getTime()));

            }
        });
        builder.show();
    }

    public void chooseReminderDate(View view) {
        final View dialogView = View.inflate(this, R.layout.date_picker, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Choose Date");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                binding.reminderDate.setText(new SimpleDateFormat("E, dd MMMM yyyy").format(calendar.getTime()));
                reminderCalendar.set(Calendar.YEAR,datePicker.getYear());
                reminderCalendar.set(Calendar.MONTH,datePicker.getMonth());
                reminderCalendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());


            }
        });
        builder.show();
    }

    public void chooseReminderTime(View view) {
        final View dialogView = View.inflate(this, R.layout.time_picker, null);
        final TimePicker timePicker = dialogView.findViewById(R.id.time_picker);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Choose Time");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                timeCalendar = Calendar.getInstance();
                timeCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                timeCalendar.set(Calendar.MINUTE, timePicker.getMinute());
                binding.reminderTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(timeCalendar.getTime()));
                reminderCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                reminderCalendar.set(Calendar.MINUTE, timePicker.getMinute());

            }
        });
        builder.show();
    }

    public void backToMainActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public class ReminderWorker extends Worker {
        private int notification = 0;
        public ReminderWorker(
                @NonNull Context context,
                @NonNull WorkerParameters params) {
            super(context, params);
        }

        @Override
        public Result doWork() {

            // Do the work here--in this case, upload the images.
            String title= getInputData().getString("Title");

            String ddl_time = getInputData().getString("Ddl_time");

            String ddl_date = getInputData().getString("Ddl_date");
//            if(imageUriInput == null) {
//                return Result.failure();
//            }
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
                notificationManager.notify(notificationId, builder.build());
                notificationId++;

            // Indicate whether the work finished successfully with the Result
            return Result.success();
        }
    }

}
