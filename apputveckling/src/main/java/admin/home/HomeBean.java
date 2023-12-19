package admin.home;

import admin.appointment.EventEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class HomeBean implements Serializable {
    @Produces
    @PersistenceContext(unitName = "Events")
    private EntityManager entityManager;
    private List<EventEntity> events;

    @PostConstruct
    public void init() {
        events = entityManager.createQuery("select e from EventEntity e where e.start_date > current_timestamp and e.email is not null and e.client_name is not null order by e.start_date asc", EventEntity.class).setMaxResults(3).getResultList();
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }
}
