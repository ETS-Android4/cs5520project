package android.example.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddNewTask extends AppCompatActivity {

    private EditText taskTitleText;
    private EditText deadlineDateText;
    private CheckBox reminderBox;

    private Button newTaskSaveButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        Intent intent = getIntent();
        taskTitleText = findViewById(R.id.taskTitle);
        deadlineDateText = findViewById(R.id.taskDate);
        reminderBox = findViewById(R.id.reminder);

    }


    public void backToMainActivity(View view) {
        finish();
    }

    public void saveNewTask(View view) {
        ArrayList<Task> taskList = (ArrayList<Task>) getIntent()
                .getSerializableExtra("taskList");
        String taskTitle = taskTitleText.getText().toString();
        String deadlineDate = deadlineDateText.getText().toString();
        Task task = new Task(taskTitle, deadlineDate, false);
        Intent replyIntent = new Intent();
        replyIntent.putExtra("title", taskTitle);
        replyIntent.putExtra("deadlineDate", deadlineDate);

        Log.i("insecond", task.getTitle());

        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
