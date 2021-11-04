package android.example.todolist.data;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {
    private static DateTimeFormatter formatter_date = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//    private static DateTimeFormatter formatter_time = DateTimeFormatter.ISO_OFFSET_TIME;

    @TypeConverter
    public static OffsetDateTime datefromString(String value) {
        return value == null ? null : formatter_date.parse(value, OffsetDateTime::from);
    }

    @TypeConverter
    public static String dateToString(OffsetDateTime date) {
        return date == null ? null : date.format(formatter_date);
    }

//    @TypeConverter
//    public static OffsetDateTime timefromString(String value) {
//        return value == null ? null : formatter_date.parse(value, OffsetDateTime::from);
//    }
//
//    @TypeConverter
//    public static String timeToString(OffsetDateTime time) {
//        return time == null ? null : time.format(formatter_date);
//    }
}
