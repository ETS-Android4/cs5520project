package android.example.todolist;

import android.app.Application;
import android.example.todolist.data.Task;
import android.example.todolist.data.TaskRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public MutableLiveData<String> taskTitle = new MutableLiveData<>();
    public MutableLiveData<String> taskDescription = new MutableLiveData<>();
    public MutableLiveData<String> taskDate = new MutableLiveData<>();
    public MutableLiveData<String> taskTime = new MutableLiveData<>();

//    public MutableLiveData<Boolean> status = new MutableLiveData<>();

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
        if (taskDate == null) {
            taskDate = new MutableLiveData<>();
            taskDate.setValue("");
        }

        if (taskTime == null) {
            taskTime = new MutableLiveData<>();
            taskTime.setValue("");
        }
//        if (status == null) {
//            status = new MutableLiveData<>();
//            status.setValue(false);
//        }

        // Using only *n* todos
        mAllTasks = repository.getAllTasks();
//        mAllTasks = repository.getNTasks(5);

        taskCreated.setValue(Boolean.FALSE);
    }

    public LiveData<Boolean> getTodoCreated() {
        return taskCreated;
    }

    public void createTask() {
        try {
            Date taskDateString = new SimpleDateFormat("E, dd MMMM yyyy").parse(taskDate.getValue());
            Date taskTimeString = new SimpleDateFormat("HH:MM").parse(taskTime.getValue());

            repository.addTask(Task.createTask(taskTitle.getValue(), taskDescription.getValue(), taskDateString.toString(), taskTimeString.toString()));
            taskCreated.setValue(Boolean.TRUE);
        } catch (ParseException e) {
        }
    }

    public void updateTask(Task task){

        repository.updateTask(task);
        taskCreated.setValue(Boolean.TRUE);
    }
    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void deleteAll() {
        repository.deleteAll();
    }


}
