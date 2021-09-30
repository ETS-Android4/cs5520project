package android.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public final class TaskDataSource extends RecyclerView.Adapter<TaskDataSource.Viewholder>{

    private Context context;
    private ArrayList<Task> list;

    // Constructor
    public TaskDataSource(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TaskDataSource.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDataSource.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Task model = list.get(position);
        holder.taskText.setText(model.getTitle() + "\n Due: " + model.getDate_ddl());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return list.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView taskText;
        private TextView deadlineDate;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            deadlineDate = itemView.findViewById(R.id.taskDate);
        }
    }
}