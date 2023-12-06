package admin.calendar;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;


import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {
    @Resource(name = "mysql_web")
    private DataSource dataSource;

    private List<Appointment> appointments;
    private List<AppointmentAPI> appointmentsAPI;
    private static final Logger LOGGER = Logger.getLogger(AppointmentResource.class.getName());

    @PostConstruct
    public void init() {
        loadAppointments();
    }
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppointmentAPI> getAppointments() {
        //return appointments;
        return appointmentsAPI;
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<AppointmentAPI>> getAppointments() {
        Map<String, List<AppointmentAPI>> calendarEvents = new HashMap<>();
        calendarEvents.put("events", appointmentsAPI);
        return calendarEvents;
    }

    private void loadAppointments() {
        //appointments = new ArrayList<>();
        appointmentsAPI = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM APPOINTMENT";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    //appointments.add(mapResultSetToAppointment(resultSet));
                    appointmentsAPI.add(mapResultSetToAppointmentForAPI(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    private Appointment mapResultSetToAppointment(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(resultSet.getInt("APPOINTMENT_ID"));
        appointment.setClientId(resultSet.getInt("CLIENT_ID"));
        appointment.setProductId(resultSet.getInt("PRODUCT_ID"));
        appointment.setAppointmentType(resultSet.getString("APPOINTMENT_TYPE"));
        appointment.setAppointmentDate(resultSet.getDate("APPOINTMENT_DATE"));
        appointment.setAppointmentTime(resultSet.getTime("APPOINTMENT_TIME"));
        appointment.setAppointmentDesc(resultSet.getString("APPOINTMENT_DESC"));
        return appointment;
    }

    /**
     * This method maps the result set to an AppointmentAPI object (Calendar.js parameter names).
     */
    private AppointmentAPI mapResultSetToAppointmentForAPI(ResultSet resultSet) throws SQLException {
        AppointmentAPI appointmentAPI = new AppointmentAPI();
        appointmentAPI.setId(resultSet.getInt("APPOINTMENT_ID"));
        appointmentAPI.setTitle(resultSet.getString("APPOINTMENT_TYPE"));
        appointmentAPI.setColor(resultSet.getString("APPOINTMENT_COLOR"));
        // Get date and time from resultSet
        Date appointmentDate = resultSet.getDate("APPOINTMENT_DATE");
        Time appointmentTime = resultSet.getTime("APPOINTMENT_TIME");
        Time appointmentDuration = resultSet.getTime("APPOINTMENT_DURATION");
        // Combine date and time into a from string
        appointmentAPI.setFrom(appointmentDate.toString() + "T" + appointmentTime.toString() + "Z");
        // Calculate the to time by adding the duration to the start time
        LocalDateTime startDateTime = LocalDateTime.of(appointmentDate.toLocalDate(), appointmentTime.toLocalTime());
        LocalTime duration = appointmentDuration.toLocalTime();
        LocalDateTime endDateTime = startDateTime.plusHours(duration.getHour()).plusMinutes(duration.getMinute());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        appointmentAPI.setTo(formatter.format(endDateTime));
        appointmentAPI.setDuration(appointmentDuration.toString());
        return appointmentAPI;
    }
}