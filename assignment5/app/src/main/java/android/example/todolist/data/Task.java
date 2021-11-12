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
import java.time.OffsetDateTime;
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
    private String ddl;
    private String ddl_time;
    private String reminder_date;
    private String reminder_time;

    private boolean status;


    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getDdl_time() {
        return ddl_time;
    }

    public void setDdl_time(String ddl_time) {
        this.ddl_time = ddl_time;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        ddl = in.readString();
        ddl_time = in.readString();
        reminder_date = in.readString();
        reminder_time = in.readString();
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

//    @Ignore
    public Task() {

    }

    public Task(int id, String title, String description, String taskDate, String taskTime,
                String reminder_date, String reminder_time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ddl = taskDate;
        this.ddl_time = taskTime;
        this.reminder_date = reminder_date;
        this.reminder_time = reminder_time;
        //To add more attributes.
        //this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getReminder_date() {
        return reminder_date;
    }

    public void setReminder_date(String reminder_date) {
        this.reminder_date = reminder_date;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static Task createTask(String title, String description, String taskDate, String taskTime,
                                    String reminder_date, String reminder_time) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDdl(taskDate);
        task.setDdl_time(taskTime);
        task.setReminder_date(reminder_date);
        task.setReminder_date(reminder_time);
//To add more attributes.
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
        parcel.writeString(ddl);
        parcel.writeString(ddl_time);
        parcel.writeString(reminder_date);
        parcel.writeString(reminder_time);

//        parcel.writeString(Converters.dateToString(ddl_time));
        //To add more attributes.
//        parcel.writeBoolean(status);
    }
}
