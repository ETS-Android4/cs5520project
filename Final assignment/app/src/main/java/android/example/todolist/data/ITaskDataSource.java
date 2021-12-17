package android.example.todolist.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ITaskDataSource {
    void insert(Task task);

    void deleteAll();

    void update(Task task);

    void update(List<Task> tasks);

    LiveData<List<Task>> sortByPosition();

    int getLargestOrder();

    LiveData<List<Task>> getTasks();

    LiveData<List<Task>> getNTasks(int n);
}
