package android.example.todolist.data;
import android.example.todolist.R;
import android.example.todolist.databinding.TaskLayoutBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//    public TaskLayoutBinding binding;
    private final TextView taskTitle;
    private final TextView taskDescription;
    private final CardView card;
    private static OnTaskListener mOnTaskListener;
    private static final String TAG = "NotesRecyclerAdapter";


    public TasksViewHolder(View itemView, OnTaskListener onTaskListener) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.titleText);
        taskDescription = itemView.findViewById(R.id.descriptionText);
        card = itemView.findViewById(R.id.cardView);
        mOnTaskListener = onTaskListener;
        itemView.setOnClickListener(this);

    }
    static TasksViewHolder create(ViewGroup parent, OnTaskListener mOnTaskListener) {
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

    public void bind(Task task) {
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
//        binding.setTask(task);
//        binding.executePendingBindings();
    }

    public interface OnTaskListener{
        void onTaskClick(int position);
    }
}
