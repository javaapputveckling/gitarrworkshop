package admin.calendar;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

import java.sql.Date;
import java.sql.Time;

/**
 * This class represents an appointment from the database with only the parameters required from the
 * williamtroup/Calendar.js calendar component. Parameters are also renamed to match the calendar
 * parameters.
 */
public class AppointmentAPI {
    public AppointmentAPI() {
    }

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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    private int id;
    private String title;
    private String from;
    private String to;

    private String duration;
    private String color;
}
