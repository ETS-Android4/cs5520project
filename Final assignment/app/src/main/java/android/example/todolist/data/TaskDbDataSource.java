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
    public void update(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.update(task);
        });
    }
//    @Override
//    public void updateOrder(Task task, int order) {
//        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
//            mTaskDao.updateOrder(task, order);
//        });
//    }
    public void update(List<Task> tasks) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.update(tasks);
        });
    }

    public LiveData<List<Task>> sortByPosition(){
        return mTaskDao.sortByPosition();
    }

    @Override
    public void deleteAll() {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.deleteAll();
        });
    }

    @Override
    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }

    @Override
    public LiveData<List<Task>> getNTasks(int n) {
        return mTaskDao.getNTasks(n);
    }

    public int getLargestOrder(){
        return mTaskDao.getLargestOrder();
    }

}
