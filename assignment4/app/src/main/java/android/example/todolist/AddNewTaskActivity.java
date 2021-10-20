package android.example.todolist;

import android.content.Intent;
import android.example.todolist.data.Task;
import android.example.todolist.databinding.NewTaskBinding;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class AddNewTaskActivity extends AppCompatActivity {
    private NewTaskBinding binding;
    private TaskViewModel toDoViewModel;
    private Task currentTask;
    private Boolean newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Get an instance to the shared ViewModel
        toDoViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setViewmodel(toDoViewModel);
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
                    currentTask = new Task(currentTask.getId(), toDoViewModel.taskTitle.getValue(), toDoViewModel.taskDescription.getValue());
                    toDoViewModel.updateTask(currentTask);
                }
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
//        binding.taskDate.setText(currentTask.getDate_ddl());
//        binding.taskTime.setText(currentTask.getTime_ddl());
        toDoViewModel.taskDescription.setValue(currentTask.getDescription());
        //TO ADD other attributes.

    }

}
