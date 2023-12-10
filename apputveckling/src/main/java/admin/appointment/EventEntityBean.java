package admin.appointment;

import jakarta.annotation.PostConstruct;


import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;


import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Named
@ViewScoped
public class EventEntityBean implements Serializable {
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
    private final List<Date> DisabledEventList = new ArrayList<>();


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
        List<EventEntity> events = entityManager.createQuery("SELECT e FROM EventEntity e", EventEntity.class).getResultList();
        if (events != null && !events.isEmpty()) {
            int i = 0;
            for (EventEntity eventEntity : events) {

                String color;
                color = "Available".equals(events.get(i).getTitle()) ? "green" : "red";
                ScheduleEvent<?> event = DefaultScheduleEvent.builder()
                        .id(String.valueOf(eventEntity.getId()))
                        .title(eventEntity.getTitle())
                        .description(eventEntity.getDescription())
                        .url(eventEntity.getUrl())
                        .backgroundColor(color)
                        .startDate(eventEntity.getStart_date().toLocalDateTime())
                        .endDate(eventEntity.getEnd_date().toLocalDateTime())
                        .allDay(eventEntity.isAll_day())
                        .build();
                i++;

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
        System.out.println("setEvent called with: " + event);
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
        loadEventsFromDatabase();
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
                entityManager.persist(dbPostEvent());
                //response.setHeader("Refresh", "0; URL=" + request.getContextPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            model.updateEvent(event);
            mapEntityAttributes();

        }
        event = new DefaultScheduleEvent<>();

    }

    public EventEntity dbPostEvent() {
        EventEntity ny = new EventEntity();
        ny.setTitle(event.getTitle());
        Timestamp start = Timestamp.valueOf(event.getStartDate());
        Timestamp end = Timestamp.valueOf(event.getEndDate());
        ny.setStart_date(start);
        ny.setEnd_date(end);
        ny.setAll_day(event.isAllDay());
        ny.setEmail(email);
        ny.setDescription(event.getDescription());
        ny.setUrl(event.getUrl());
        model.addEvent(event);

        return ny;
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }

    @Transactional
    public void onUpdate(EventEntity event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event updated"));
        int id = event.getId();
        EventEntity ny = new EventEntity();
        String title = event.getTitle();
        Timestamp start = event.getStart_date();
        Timestamp end = event.getEnd_date();
        boolean all_day = event.isAll_day();
        String email = event.getEmail();
        String description = event.getDescription();
        String url = event.getUrl();
    }


    @Transactional
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event moved"));
        ScheduleEvent<?> movedEvent = event.getScheduleEvent();

        // Find the existing EventEntity in the database
        EventEntity eventEntity = entityManager.find(EventEntity.class, Integer.parseInt(movedEvent.getId()));
        // Update the fields of the EventEntity
        eventEntity.setStart_date(Timestamp.valueOf(movedEvent.getStartDate()));
        eventEntity.setEnd_date(Timestamp.valueOf(movedEvent.getEndDate()));
        eventEntity.setAll_day(movedEvent.isAllDay());
        // Merge the updated EventEntity
        entityManager.merge(eventEntity);

    }

    /**
     *
     */
    @Transactional
    public void deleteEvent() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event delete"));
        String query = "DELETE FROM EventEntity e WHERE e.id = " + event.getId();
        entityManager.createQuery(query)
                .executeUpdate();
        model.deleteEvent(event);
    }

    /**
     * This method is used to set the available events
     * It is called from the admin/Calendar.xhtml page
     */
    @Transactional
    public void setAvailableEvents() {
        LocalDateTime slot = LocalDateTime.of(time_from.getYear(), time_from.getMonth(), time_from.getDayOfMonth(), 10, 0);
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(18, 0);
        Duration slotDuration = Duration.ofMinutes(45);
        Duration breakDuration = Duration.ofMinutes(15);
        boolean isLunchBreakSkipped = false;
        while (!slot.isAfter(time_to)) {
            LocalDateTime closed = LocalDateTime.of(slot.toLocalDate(), closingTime);
            LocalDateTime opened = LocalDateTime.of(slot.toLocalDate(), openingTime);
            LocalDateTime lunchStart = LocalDateTime.of(slot.toLocalDate(), LocalTime.of(13, 0));
            LocalDateTime lunchEnd = lunchStart.plusHours(1); //  lunch break is 1 hour
            // Skip the lunch break
            if (!isLunchBreakSkipped && !slot.isBefore(lunchStart) && !slot.isAfter(lunchEnd)) {
                slot = slot.plusHours(1);
                isLunchBreakSkipped = true;
                continue;
            }
            if (slot.isBefore(opened) || slot.isAfter(closed)) {
                slot = slot.plusMinutes(60);
                isLunchBreakSkipped = false;
                continue;
            }
            if (slot.getDayOfWeek() == DayOfWeek.SATURDAY) {
                slot = slot.plusDays(2);
                continue;
            } else if (slot.getDayOfWeek() == DayOfWeek.SUNDAY) {
                slot = slot.plusDays(1);
                continue;

            }
            // Check if the slot is already in the database
            List<EventEntity> events = entityManager.createQuery("SELECT e FROM EventEntity e WHERE e.start_date = :start_date", EventEntity.class)
                    .setParameter("start_date", Timestamp.valueOf(slot))
                    .getResultList();
            if (events != null && !events.isEmpty()) {
                slot = slot.plus(slotDuration).plus(breakDuration);
                continue;
            }
            EventEntity newSlot = new EventEntity();
            newSlot.setTitle("Available");
            newSlot.setEnd_date(Timestamp.valueOf(slot.plus(slotDuration)));
            newSlot.setStart_date(Timestamp.valueOf(slot));
            newSlot.setAll_day(false);
            entityManager.persist(newSlot);
            slot = slot.plus(slotDuration).plus(breakDuration);
        }
        loadEventsFromDatabase(); // refresh the calendar
    }

    public static LocalDate convertToDateToLocalDate(Date date) {
        // Convert Date to Instant
        Instant instant = date.toInstant();
        // Convert Instant to LocalDate using the default time zone
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void mapEntityAttributes() {
        EventEntity eventEntity = entityManager.find(EventEntity.class, Integer.parseInt(event.getId()));
        eventEntity.setTitle(event.getTitle());
        eventEntity.setStart_date(Timestamp.valueOf(event.getStartDate()));
        eventEntity.setEnd_date(Timestamp.valueOf(event.getEndDate()));
        eventEntity.setUrl(event.getUrl());
        eventEntity.setAll_day(event.isAllDay());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setEmail(email);
        entityManager.merge(eventEntity);
    }

    @Transactional
    public void updateEventEntity() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event updated"));

        // Find the existing EventEntity in the database
        EventEntity eventEntity = entityManager.find(EventEntity.class, Integer.parseInt(event.getId()));

        // Update the fields of the EventEntity
        eventEntity.setTitle(event.getTitle());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setEmail(email);

        // Merge the updated EventEntity
        entityManager.merge(eventEntity);
    }

    public void onCommandLinkAction(String eventId) {
        event = model.getEvent(eventId);
    }



    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}