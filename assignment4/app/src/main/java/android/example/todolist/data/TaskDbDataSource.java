package android.example.todolist.data;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskDbDataSource implements ITaskDataSource {
    private TaskDao mTaskDao;

    public TaskDbDataSource(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.TaskDao();
    }

    @Override
    public void insert(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }

    @Override
    public LiveData<List<Task>> getNTasks(int n) {
        return mTaskDao.getNTasks(n);
    }

}
