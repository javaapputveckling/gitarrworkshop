package admin.appointment;

import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;

@Named
@ViewScoped
public class EventEntityBean implements Serializable {
    @Produces
    @PersistenceContext(unitName = "APPOINTMENT")
    private EntityManager entityManager;
    EventEntity event = new EventEntity();

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    @Transactional
    public void saveEvent(EventEntity eventEntity) {
        entityManager.persist(eventEntity);
    }

    public static void main(String[] args) {
        EventEntityBean eventEntityBean = new EventEntityBean();
        eventEntityBean.event.setTitle("title");
        eventEntityBean.event.setDescription("description");
        eventEntityBean.event.setStart_date(java.time.LocalDateTime.now());
        eventEntityBean.event.setEnd_date(java.time.LocalDateTime.now());
        //eventEntityBean.event.setStart_date(new java.util.Date());
        //eventEntityBean.event.setEnd_date(new java.util.Date());
        eventEntityBean.event.setAll_day(true);
        eventEntityBean.saveEvent(eventEntityBean.event);
    }
}
