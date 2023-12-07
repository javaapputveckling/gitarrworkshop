package admin.appointment;

import jakarta.annotation.PostConstruct;

import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.event.schedule.ScheduleEntryResizeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class ScheduleBean implements Serializable {
    @Produces
    @PersistenceContext(unitName = "APPOINTMENT")
    private EntityManager entityManager;
    private ScheduleModel model;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    @PostConstruct
    public void init() {
        try {
            loadEventsFromDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void loadEventsFromDatabase() {
        model = new DefaultScheduleModel();
        List<EventEntity> events = entityManager.createQuery("SELECT e FROM EventEntity e", EventEntity.class)
                .getResultList();
        if (events != null && !events.isEmpty()) {
            for (EventEntity eventEntity : events) {
                ScheduleEvent<?> event = DefaultScheduleEvent.builder()
                        .id(String.valueOf(eventEntity.getId()))
                        .title(eventEntity.getTitle())
                        .startDate(eventEntity.getStart_date())
                        .endDate(eventEntity.getEnd_date())
                        //.startDate(eventEntity.getStart_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        //.endDate(eventEntity.getEnd_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        .allDay(eventEntity.isAll_day())
                        .build();
                model.addEvent(event);

            }
        }

    }

    public ScheduleModel getModel() {
        return model;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
    }
    @Transactional
    public void addEvent() {
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            try {
                EventEntity eventEntity = convertToEntity(event);
                //model.addEvent(event);
                entityManager.persist(eventEntity);
                saveToDatabase(eventEntity);
                event = convertToScheduleEvent(eventEntity);
                model.addEvent(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            model.updateEvent(event);
        }

        event = new DefaultScheduleEvent<>();
    }



    private void saveToDatabase(EventEntity eventEntity) throws SQLException {

        entityManager.persist(eventEntity);

    }

    private EventEntity convertToEntity(ScheduleEvent<?> event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTitle(event.getTitle());

        eventEntity.setStart_date(event.getStartDate());
        eventEntity.setEnd_date(event.getEndDate());
        //LocalDateTime startDate = Date.from(Instant.from(event.getStartDate()));
        //LocalDateTime endDate = Date.from(event.getEndDate().atZone(ZoneId.systemDefault()).toInstant());
        //eventEntity.setStart_date(startDate);
        //eventEntity.setEnd_date(endDate);
        eventEntity.setAll_day(event.isAllDay());
        return eventEntity;
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }
    private ScheduleEvent<?> convertToScheduleEvent(EventEntity eventEntity) {
        return DefaultScheduleEvent.builder()
                .id(String.valueOf(eventEntity.getId()))
                .title(eventEntity.getTitle())
                .startDate(eventEntity.getStart_date())
                .endDate(eventEntity.getEnd_date())
                .allDay(eventEntity.isAll_day())
                .build();
    }
    @Transactional
    public void onEventMove(ScheduleEntryMoveEvent event) {
        ScheduleEvent<?> movedEvent = event.getScheduleEvent();
        // Find the existing EventEntity in the database
        EventEntity eventEntity = entityManager.find(EventEntity.class, Integer.parseInt(movedEvent.getId()));
        // Update the fields of the EventEntity
        eventEntity.setStart_date(movedEvent.getStartDate());
        eventEntity.setEnd_date(movedEvent.getEndDate());
        eventEntity.setAll_day(movedEvent.isAllDay());
        // Merge the updated EventEntity
        entityManager.merge(eventEntity);
        // Remove the old event from the model
        model.deleteEvent(movedEvent);
        // Add the updated event to the model
        model.addEvent(movedEvent);
    }

    @Transactional
    public void onEventResize(ScheduleEntryResizeEvent event) {
        ScheduleEvent<?> resizedEvent = event.getScheduleEvent();
        // Find the existing EventEntity in the database
        EventEntity eventEntity = entityManager.find(EventEntity.class, Integer.parseInt(resizedEvent.getId()));
        // Update the fields of the EventEntity
        eventEntity.setStart_date(resizedEvent.getStartDate());
        eventEntity.setEnd_date(resizedEvent.getEndDate());
        eventEntity.setAll_day(resizedEvent.isAllDay());
        // Merge the updated EventEntity
        entityManager.merge(eventEntity);
        // Remove the old event from the model
        model.deleteEvent(resizedEvent);
        // Add the updated event to the model
        model.addEvent(resizedEvent);
    }
}
