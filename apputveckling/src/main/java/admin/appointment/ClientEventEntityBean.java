package admin.appointment;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class is used to create a schedule model for the client to book an appointment
 */
@Named
@ViewScoped
public class ClientEventEntityBean implements Serializable {
    @Produces
    @PersistenceContext(unitName = "Events")
    private EntityManager entityManager;
    private ScheduleModel model;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
    private LocalDateTime time_from;
    private LocalDateTime time_to;
    private LocalDate date;
    private int duration;
    private String email;
    private String client_name;
    @PostConstruct
    public void init(){
        try {
            availableTime();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing EventEntityBean");
        }

    }

    /**
     * This method is used to add an event to the schedule model
     * @return a string to redirect to the client appointments page
     */
    @Transactional
    public String addEvent() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event booked"));
        EventEntity event2 = entityManager.find(EventEntity.class, Integer.parseInt(event.getId()));
        event2.setTitle(event.getTitle());
        event2.setStart_date(Timestamp.valueOf(event.getStartDate()));
        event2.setEnd_date(Timestamp.valueOf(event.getEndDate()));
        event2.setUrl(event.getUrl());
        event2.setAll_day(false);
        event2.setDescription(event.getDescription());
        event2.setEmail(email);
        event2.setClient_name(client_name);
        entityManager.merge(event2);

        model.deleteEvent(model.getEvent(event.getId())); // delete the old event from the model

        event = new DefaultScheduleEvent<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully booked a time."));
        return "ClientAppointments.xhtml?faces-redirect=true";
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        ScheduleEvent<?> event2 = selectEvent.getObject();
        event = DefaultScheduleEvent.builder()
                .id(String.valueOf(event2.getId()))
                .title("") // set the title to empty string to hide it from the client
                .description(event2.getDescription())
                .url(event2.getUrl())
                .backgroundColor("red")
                .startDate(event2.getStartDate())
                .endDate(event2.getEndDate())
                .allDay(event2.isAllDay())
                .build();
    }

    private void availableTime() {
        model = new DefaultScheduleModel();
        List<EventEntity> events = entityManager.createQuery("SELECT e FROM EventEntity e WHERE e.title = 'Available' and e.start_date>current_timestamp", EventEntity.class).getResultList();
        if (events != null && !events.isEmpty()) {
            for (EventEntity eventEntity : events) {
                ScheduleEvent<?> event = DefaultScheduleEvent.builder()
                        .id(String.valueOf(eventEntity.getId()))
                        .title(eventEntity.getTitle())
                        .description(eventEntity.getDescription())
                        .url(eventEntity.getUrl())
                        .backgroundColor("green")
                        .startDate(eventEntity.getStart_date().toLocalDateTime())
                        .endDate(eventEntity.getEnd_date().toLocalDateTime())
                        .allDay(eventEntity.isAll_day())
                        .build();
                model.addEvent(event);
            }
        }
    }

    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public LocalDateTime getTime_from() {
        return time_from;
    }

    public void setTime_from(LocalDateTime time_from) {
        this.time_from = time_from;
    }

    public LocalDateTime getTime_to() {
        return time_to;
    }

    public void setTime_to(LocalDateTime time_to) {
        this.time_to = time_to;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
