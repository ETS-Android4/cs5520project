package android.example.todolist.data;
import android.example.todolist.R;
import android.example.todolist.databinding.TaskLayoutBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TasksViewHolder extends RecyclerView.ViewHolder {
//    public TaskLayoutBinding binding;
    private final TextView taskTitle;
    private final TextView taskDescription;


    public TasksViewHolder(View itemView) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.titleText);
        taskDescription = itemView.findViewById(R.id.descriptionText);

    }
    static TasksViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new TasksViewHolder(view);
    }

    public void bind(Task task) {
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
//        binding.setTask(task);
//        binding.executePendingBindings();
    }
}
