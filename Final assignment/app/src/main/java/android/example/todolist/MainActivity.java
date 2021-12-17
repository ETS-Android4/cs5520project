package android.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.example.todolist.ItemMoveCallback;
import android.example.todolist.data.Task;
import android.example.todolist.data.TasksRecyclerViewAdapter;
import android.example.todolist.data.TasksViewHolder;
import android.example.todolist.databinding.ActivityMainBinding;
import android.example.todolist.databinding.TaskLayoutBinding;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TasksViewHolder.OnTaskListener {
    private static final String CHANNEL_ID = "1";
    //    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TaskViewModel mTaskViewModel;
    private List<Task> mTasks;
    private String selectedFilter = "all";
    private String currentSearchText ="";
    private TasksRecyclerViewAdapter adapter;
    private List<Task> filteredTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createNotificationChannel();
        //vars
        mTasks = new ArrayList<>();
//        initSearchWidgets();
        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        adapter = new TasksRecyclerViewAdapter(new TasksRecyclerViewAdapter.TaskDiff(), this, mTaskViewModel);
        binding.recyclerView.setAdapter(adapter);
        mTaskViewModel.getAllTasks().observe(this, tasks -> {
            mTasks.addAll(tasks);

            adapter.submitList(mTasks);

        });
        initSearchWidgets(mTasks);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
                startActivity(intent);
            }
        });

        // Enable the move and drag of tasks in the RecyclerView.
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(adapter, mTaskViewModel);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(binding.recyclerView);

    }

    private void initSearchWidgets(List<Task> tasks)
    {
        SearchView searchView = binding.searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                selectedFilter = "all";
                filteredTasks = new ArrayList<>();

                for(Task task: tasks)
                {
                    if(task.getTitle().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilter.equals("all"))
                        {
                            filteredTasks.add(task);
                        }
                        else
                        {
                            if(task.getTitle().toLowerCase().contains(selectedFilter))
                            {
                                filteredTasks.add(task);
                            }
                        }
                    }
                }
//                mTasks = filteredTasks;
                adapter.submitList(filteredTasks);



//                ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, filteredShapes);
//                listView.setAdapter(adapter);

                return false;
            }
        });
    }


    @Override
    public void onTaskClick(int position) {
        Task task = mTasks.get(position);
        if(filteredTasks!=null && filteredTasks.size()!=0) task = filteredTasks.get(position);
        Intent intent = new Intent(this, AddNewTaskActivity.class);
        intent.putExtra("selected_note", task);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}