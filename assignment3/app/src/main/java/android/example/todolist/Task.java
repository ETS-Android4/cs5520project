package android.example.todolist;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    private String title;
    private String date_ddl;
    private boolean status;

    public Task(String title, String date_ddl, boolean status) {
        this.title = title;
        this.date_ddl = date_ddl;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_ddl() {
        return date_ddl;
    }

    public void setDate_ddl(String date_ddl) {
        this.date_ddl = date_ddl;
    }
}
