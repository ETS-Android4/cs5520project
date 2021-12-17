package android.example.todolist.data;
import android.content.Intent;
import android.example.todolist.AddNewTaskActivity;
import android.example.todolist.MainActivity;
import android.example.todolist.R;
import android.example.todolist.TaskViewModel;
import android.example.todolist.databinding.TaskLayoutBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//    public TaskLayoutBinding binding;
    final TextView taskTitle;
    final TextView taskDescription;
    final CheckBox checkbox;
    private Task task;
    //    private final TextView taskDate;
//    private final TextView taskTime;
    final CardView card;
    private static OnTaskListener mOnTaskListener;
    private static final String TAG = "NotesRecyclerAdapter";

    public TasksViewHolder(View itemView, OnTaskListener onTaskListener) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.titleText);
        taskDescription = itemView.findViewById(R.id.descriptionText);
        checkbox = itemView.findViewById(R.id.todoCheckBox);
        card = itemView.findViewById(R.id.cardView);
        mOnTaskListener = onTaskListener;
        itemView.setOnClickListener(this);

    }
    public static TasksViewHolder create(ViewGroup parent, OnTaskListener mOnTaskListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new TasksViewHolder(view, mOnTaskListener);
    }

    public View cardView() {
        return card;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: " + getAbsoluteAdapterPosition());
        mOnTaskListener.onTaskClick(getAbsoluteAdapterPosition());
    }


    public void bind(Task task, TaskViewModel mTaskViewModel) {
        this.task = task;
        taskTitle.setText(task.getTitle());
        String ddl = task.getDdl();
        if(task.getDdl()==null) ddl = "Not set yet.";
        taskDescription.setText("Due on: " + ddl);
        checkbox.setChecked(task.getStatus());
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setStatus(checkbox.isChecked());
                mTaskViewModel.updateTask(task);
            }
        });
    }

    public interface OnTaskListener{
        void onTaskClick(int position);
    }
}
