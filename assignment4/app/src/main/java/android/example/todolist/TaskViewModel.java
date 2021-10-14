package android.example.todolist;

import android.app.Application;
import android.example.todolist.data.Task;
import android.example.todolist.data.TaskRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public MutableLiveData<String> taskTitle = new MutableLiveData<>();
    public MutableLiveData<String> taskDescription = new MutableLiveData<>();

    private MutableLiveData<Boolean> taskCreated = new MutableLiveData<>();

    private TaskRepository repository;

    private final LiveData<List<Task>> mAllTasks;

    // TODO(ahs): Review/include the SavedStateHandle stuff
    public TaskViewModel(Application application) {
        super(application);
        repository = TaskRepository.getSingleton(application);
        if (taskTitle == null) {
            taskTitle = new MutableLiveData<>();
            taskTitle.setValue("");
        }
        if (taskDescription == null) {
            taskDescription = new MutableLiveData<>();
            taskDescription.setValue("");
        }

        // Using only *n* todos
//        mAllToDos = repository.getAllTodos();
        mAllTasks = repository.getNTasks(5);

        taskCreated.setValue(Boolean.FALSE);
    }

    public LiveData<Boolean> getTodoCreated() {
        return taskCreated;
    }

    public void createTask() {
        repository.addTask(Task.createTask(taskTitle.getValue(), taskDescription.getValue()));
        taskCreated.setValue(Boolean.TRUE);
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }


}
