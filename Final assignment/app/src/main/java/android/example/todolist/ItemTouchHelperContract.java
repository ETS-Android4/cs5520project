package android.example.todolist;

import android.example.todolist.TaskViewModel;
import android.example.todolist.data.TasksViewHolder;

public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition, TaskViewModel taskViewModel);
        void onRowSelected(TasksViewHolder myViewHolder);
        void onRowClear(TasksViewHolder myViewHolder);

}
