package android.example.todolist;

import android.content.Intent;
import android.example.todolist.data.Task;
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

    private TaskViewModel toDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        // Get an instance to the shared ViewModel
        toDoViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Observe a flag we use to say the new ToDo has been created
        // This is a bit of a hack; there's a slightly better way to do this (observe an
        //   event rather than a Boolean), but this is okay for now.
        toDoViewModel.getTodoCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean todoCreated) {
                if (todoCreated) {
//                    setResult();
                    finish();
                }
            }
        });
    }
}
