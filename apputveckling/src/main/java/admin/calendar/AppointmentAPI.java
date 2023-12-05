package admin.calendar;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

import java.sql.Date;
import java.sql.Time;

/**
 * This class represents an appointment from the database with only the parameters requierd from the
 * williamtroup/Calendar.js calendar component. Parameters are also renamed to match the calendar
 * parameters.
 */
public class AppointmentAPI {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private int id;
    private String title;

    private String from;
}
