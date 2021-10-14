package android.example.todolist.data;

import android.example.todolist.data.Task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTasks();

    // Selects n elements from the table;
    // Does not specify order (e.g. most recent n items).
    @Query("SELECT * FROM task_table LIMIT :n")
    LiveData<List<Task>> getNTasks(int n);
}
