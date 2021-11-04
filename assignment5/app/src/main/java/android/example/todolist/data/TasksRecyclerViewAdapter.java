package android.example.todolist.data;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public final class TasksRecyclerViewAdapter extends ListAdapter<Task, TasksViewHolder> {
    // Constructor
    TasksViewHolder.OnTaskListener mOnTaskListener;
    public TasksRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, TasksViewHolder.OnTaskListener mOnTaskListener) {
        super(diffCallback);
        this.mOnTaskListener = mOnTaskListener;
    }


    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TasksViewHolder.create(parent, mOnTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        // This is how we bind the UI to a specific task
        holder.bind(getItem(position));
    }

    public static class TaskDiff extends DiffUtil.ItemCallback<Task> {

        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}