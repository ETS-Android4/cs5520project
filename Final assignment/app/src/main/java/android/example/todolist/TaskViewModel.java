package android.example.todolist;

import android.app.Application;
import android.example.todolist.data.Task;
import android.example.todolist.data.TaskRepository;
import android.preference.EditTextPreference;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public MutableLiveData<String> taskTitle = new MutableLiveData<>();
    public MutableLiveData<String> taskDescription = new MutableLiveData<>();
    public MutableLiveData<String> taskDate = new MutableLiveData<>();
    public MutableLiveData<String> taskTime = new MutableLiveData<>();
    public MutableLiveData<String> reminderDate = new MutableLiveData<>();
    public MutableLiveData<String> reminderTime = new MutableLiveData<>();
    public MutableLiveData<String> tags = new MutableLiveData<>();
    public MutableLiveData<Boolean> status = new MutableLiveData<>();
    public MutableLiveData<Boolean> reminder = new MutableLiveData<>();
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

        if (reminderDate == null) {
            reminderDate = new MutableLiveData<>();
            reminderDate.setValue("");
        }

        if (reminderTime == null) {
            reminderTime = new MutableLiveData<>();
            reminderTime.setValue("");
        }
        if (tags == null) {
            tags = new MutableLiveData<>();
            tags.setValue("");
        }
        if (status == null) {
            status = new MutableLiveData<>();
            status.setValue(false);
        }
        if (reminder == null) {
            reminder = new MutableLiveData<>();
            reminder.setValue(false);
        }

        mAllTasks = repository.sortByPosition();
//        mAllTasks = repository.getNTasks(5);

        taskCreated.setValue(Boolean.FALSE);
    }

    public LiveData<Boolean> getTodoCreated() {
        return taskCreated;
    }

    public void createTask(String tag) {
        boolean remind = true;
        if(reminder == null || reminder.getValue()==null || reminder.getValue() == false) remind = false;
        repository.addTask(Task.createTask(taskTitle.getValue(), taskDescription.getValue(),
                taskDate.getValue(),taskTime.getValue(), reminderDate.getValue(), reminderTime.getValue(),
                repository.getLargestOrder()+1, tag, remind, false));
//        taskCreated.setValue(Boolean.TRUE);

    }

    public void updateTask(Task task){

        repository.updateTask(task);
//        taskCreated.setValue(Boolean.TRUE);
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void deleteAll() {
        repository.deleteAll();
    }


}
