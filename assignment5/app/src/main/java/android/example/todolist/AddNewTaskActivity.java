package android.example.todolist;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.todolist.data.Converters;
import android.example.todolist.data.Task;
import android.example.todolist.databinding.NewTaskBinding;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddNewTaskActivity extends AppCompatActivity{
    private NewTaskBinding binding;
    private TaskViewModel toDoViewModel;
    private Task currentTask;
    private Boolean newTask;
    private Calendar calendar;
    private Calendar timeCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Get an instance to the shared ViewModel
        toDoViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setViewmodel(toDoViewModel);
        calendar = new GregorianCalendar();
        timeCalendar = Calendar.getInstance();
        if(!getIncomingIntent()){
            setNoteProperties();
        }
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newTask) {
                    toDoViewModel.createTask();
                }
                else {
//                    Date taskDateString = new SimpleDateFormat("E, dd MMMM yyyy").parse(toDoViewModel.taskDate.getValue());
//                    Date taskTimeString = new SimpleDateFormat("HH:MM").parse(toDoViewModel.taskDate.getValue());
                    currentTask = new Task(currentTask.getId(), toDoViewModel.taskTitle.getValue(),
                            toDoViewModel.taskDescription.getValue(),
                            toDoViewModel.taskDate.getValue(),
                            toDoViewModel.taskTime.getValue());
                    toDoViewModel.updateTask(currentTask);

                }
                startAlarm(calendar);
                finish();
            }
        });

    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")){
            currentTask = getIntent().getParcelableExtra("selected_note");
            newTask = false;
            return false;
        }
        newTask = true;
        return true;
    }

    private void setNoteProperties(){
        toDoViewModel.taskTitle.setValue(currentTask.getTitle());
        toDoViewModel.taskDescription.setValue(currentTask.getDescription());
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(iso8601Format.parse(currentTask.getDdl()));
            timeCalendar.setTime(new SimpleDateFormat("HH:MM").parse(currentTask.getDdl_time()));

        } catch (ParseException e) {
        }
        toDoViewModel.taskDate.setValue(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        toDoViewModel.taskTime.setValue(new SimpleDateFormat("HH:MM").format(timeCalendar.getTime()));

        //TO ADD other attributes.

    }


    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
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
                binding.taskDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

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

    public void backToMainActivity(View view) {
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
