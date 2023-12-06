package admin.calendar;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {
    @PersistenceContext(unitName = "mysql_web")
    private EntityManager entityManager;
    private List<AppointmentAPI> appointmentsAPI;
    private static final Logger LOGGER = Logger.getLogger(AppointmentResource.class.getName());

    @PostConstruct
    public void init() {
        loadAppointments();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<AppointmentAPI>> getAppointments() {
        Map<String, List<AppointmentAPI>> calendarEvents = new HashMap<>();
        calendarEvents.put("events", appointmentsAPI);
        return calendarEvents;
    }

    private void loadAppointments() {
        appointmentsAPI = entityManager.createQuery("SELECT a FROM AppointmentAPI a", AppointmentAPI.class)
                .getResultList();
    }

}