package android.example.todolist.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TaskRepository implements Iterable<Task>{

    private ITaskDataSource mTaskDataSource;

    private static TaskRepository singleton;

    private TaskRepository(Application application) {
//        mToDoDataSource = new ToDoInMemoryDataSource();
        mTaskDataSource = new TaskDbDataSource(application);
        this.createFakeData();
    }

    public static TaskRepository getSingleton(Application application) {
        if (singleton == null) {
            singleton = new TaskRepository(application);
        }
        return singleton;
    }

    public List<Task> asList() {
        return mTaskDataSource.getTasks().getValue();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDataSource.getTasks();
    }

    public LiveData<List<Task>> getNTasks(int n) {
        return mTaskDataSource.getNTasks(n);
    }

    public void addTask(Task newToDo) {
        mTaskDataSource.insert(newToDo);
    }

    private void createFakeData() {
        addTask(Task.createTask("Task 1", "do something, already"));
        addTask(Task.createTask("Task 2", "and another thign!"));

    }

    @NonNull
    @Override
    public Iterator<Task> iterator() {
        return mTaskDataSource.getTasks().getValue().iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super Task> action) {
        mTaskDataSource.getTasks().getValue().forEach(action);
    }

    @NonNull
    @Override
    public Spliterator<Task> spliterator() {
        return mTaskDataSource.getTasks().getValue().spliterator();
    }
}
