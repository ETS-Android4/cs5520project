package android.example.todolist.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
@Entity(tableName="task_table")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    private String title;

    private String description;
    private String time_ddl;
    private String date_ddl;
    private boolean status;

    public String getTime_ddl() {
        return time_ddl;
    }

    public void setTime_ddl(String time_ddl) {
        this.time_ddl = time_ddl;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        date_ddl = in.readString();
        time_ddl = in.readString();
        status = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Ignore
    public Task() {

    }

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        //To add more attributes.

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_ddl() {
        return date_ddl;
    }

    public void setDate_ddl(String date_ddl) {
        this.date_ddl = date_ddl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static Task createTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        return task;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        //To add more attributes.
//        parcel.writeString(date_ddl);
//        parcel.writeString(time_ddl);
//        parcel.writeBoolean(status);
    }
}
