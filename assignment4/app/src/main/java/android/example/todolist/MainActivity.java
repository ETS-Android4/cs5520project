package android.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.example.todolist.data.Task;
import android.example.todolist.data.TasksRecyclerViewAdapter;
import android.example.todolist.databinding.ActivityMainBinding;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
//    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        final TasksRecyclerViewAdapter adapter = new TasksRecyclerViewAdapter(new TasksRecyclerViewAdapter.TaskDiff());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Get a new or existing ViewModel from the ViewModelProvider.
//        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
//
//        // Add an observer on the LiveData returned by getAlphabetizedWords.
//        // The onChanged() method fires when the observed data changes and the activity is
//        // in the foreground.
//        mTaskViewModel.getAllTasks().observe(this, tasks -> {
//            // Update the cached copy of the words in the adapter.
//            adapter.submitList(tasks);
//        });
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
////            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//        });
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final TasksRecyclerViewAdapter adapter =
                new TasksRecyclerViewAdapter(new TasksRecyclerViewAdapter.TaskDiff());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        mTaskViewModel.getAllTasks().observe(this, tasks -> {
            adapter.submitList(tasks);
        });



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

}