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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {
    @Resource(name = "mysql_web")
    private DataSource dataSource;

    private List<Appointment> appointments;
    private static final Logger LOGGER = Logger.getLogger(AppointmentResource.class.getName());

    @PostConstruct
    public void init() {
        loadAppointments();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAppointments() {
        return appointments;
    }

    private void loadAppointments() {
        appointments = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM APPOINTMENT";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    appointments.add(mapResultSetToAppointment(resultSet));
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
}