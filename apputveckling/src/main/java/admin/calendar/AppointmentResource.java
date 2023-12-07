package admin.calendar;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createAppointment(AppointmentAPI newAppointment) {
        entityManager.persist(newAppointment);
        return Response.status(Response.Status.CREATED).build();
    }

    private void loadAppointments() {
        appointmentsAPI = entityManager.createQuery("SELECT a FROM AppointmentAPI a", AppointmentAPI.class)
                .getResultList();
    }

}