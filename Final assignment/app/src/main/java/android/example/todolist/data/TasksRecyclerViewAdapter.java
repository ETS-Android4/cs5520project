package android.example.todolist.data;

import android.content.Intent;
import android.example.todolist.ItemTouchHelperContract;
import android.example.todolist.MainActivity;
import android.example.todolist.TaskViewModel;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class TasksRecyclerViewAdapter extends ListAdapter<Task, TasksViewHolder> implements ItemTouchHelperContract {
    // Constructor
    TasksViewHolder.OnTaskListener mOnTaskListener;
    TaskViewModel mTaskViewModel;
    public TasksRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, TasksViewHolder.OnTaskListener mOnTaskListener, TaskViewModel mTaskViewModel) {
        super(diffCallback);
        this.mOnTaskListener = mOnTaskListener;
        this.mTaskViewModel = mTaskViewModel;
    }


    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TasksViewHolder.create(parent, mOnTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        // This is how we bind the UI to a specific task
        holder.bind(getItem(position), mTaskViewModel);
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition, TaskViewModel taskViewModel) {
        List<Task> list = new LinkedList<Task>(getCurrentList());
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);

            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        setIndexInDatabase(list, taskViewModel);

        notifyItemMoved(fromPosition, toPosition);

    }

    void setIndexInDatabase(List<Task> list, TaskViewModel taskViewModel) {
        for (Task task : list) {
//            Task currentTask = new Task(task.getId(), task.getTitle(),
//                    task.getDescription(),task.getDdl(), task.getDdl_time(), task.getReminder_date(),
//                    task.getReminder_time(), task.getTag(), task.getReminder(), task.getStatus());
            task.setMOrder(list.indexOf(task));

            taskViewModel.updateTask(task);
        }

    }
    @Override
    public void onRowSelected(TasksViewHolder myViewHolder) {
        myViewHolder.card.setBackgroundColor(Color.BLUE);

    }

    @Override
    public void onRowClear(TasksViewHolder myViewHolder) {
        myViewHolder.card.setBackgroundColor(Color.WHITE);
        //        setIndexInDatabase(taskViewModel);


    }
    public static class TaskDiff extends DiffUtil.ItemCallback<Task> {

        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}