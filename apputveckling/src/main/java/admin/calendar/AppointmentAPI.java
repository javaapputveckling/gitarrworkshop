package admin.calendar;

import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * This class represents an appointment from the database with only the parameters required from the
 * williamtroup/Calendar.js calendar component. Parameters are also renamed to match the calendar
 * parameters.
 */
@Entity
@Table(name = "APPOINTMENT")
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

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
/*
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }*/

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @Column(name = "APPOINTMENT_ID")
    private int id;
    @Column(name = "APPOINTMENT_TYPE")
    private String title;
    @Column(name = "APPOINTMENT_FROM")
    private LocalDateTime from;
    @Column(name = "APPOINTMENT_TO")
    private LocalDateTime to;
    @Column(name = "CLIENT_ID")
    private int clientId;
    @Column(name = "PRODUCT_ID")
    private int productId;
    @Column(name = "APPOINTMENT_COLOR")
    private String color;
    @Column(name = "APPOINTMENT_DESC")
    private String description;

    //private String duration;
}
