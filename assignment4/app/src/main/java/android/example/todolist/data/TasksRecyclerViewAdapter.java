package android.example.todolist.data;

import android.content.Context;
import android.example.todolist.R;
import android.example.todolist.databinding.TaskLayoutBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public final class TasksRecyclerViewAdapter extends ListAdapter<Task, TasksViewHolder> {
    // Constructor
    public TasksRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TasksViewHolder.create(parent);
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