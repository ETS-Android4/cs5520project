package android.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TaskDataSource taskDataSource;
    private RecyclerView recyclerView;
    private ArrayList<Task> tasks;
    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        // here we have created new array list and added data to it.
        tasks = new ArrayList<>();
        tasks.add(new Task("Mobile Assignment 1", "04/05/2021", true));
        tasks.add(new Task("Mobile Assignment 2", "04/05/2021", true));
        tasks.add(new Task("Mobile Assignment 3", "04/05/2021", true));


        // we are initializing our adapter class and passing our arraylist to it.
        taskDataSource = new TaskDataSource(this, tasks);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskDataSource);
    }

    public void launchAddNewTask(View view) {
        Intent intent = new Intent(this, AddNewTask.class);
        intent.putExtra("taskList",tasks);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void launchEditTask(View view) {
        Intent intent = new Intent(this, EditTask.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(taskDataSource != null){
            taskDataSource.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Task newTask = new Task(data.getStringExtra("title"),
                    data.getStringExtra("deadlineDate"), false);
            tasks.add(newTask);
            taskDataSource.notifyDataSetChanged();
        }

    }

}