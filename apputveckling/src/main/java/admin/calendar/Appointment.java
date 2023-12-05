package admin.calendar;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int appointmentId;
    private int clientId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getAppointmentDesc() {
        return appointmentDesc;
    }

    public void setAppointmentDesc(String appointmentDesc) {
        this.appointmentDesc = appointmentDesc;
    }

    private int productId;
    private String appointmentType;
    private Date appointmentDate;
    @JsonbTypeSerializer(TimeSerializer.class)
    private Time appointmentTime;
    private String appointmentDesc;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
