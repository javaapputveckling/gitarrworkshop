package admin.appointment;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="client_name")
    private String client_name;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private Timestamp start_date;
    @Column(name = "end_date")
    private Timestamp end_date;
    @Column(name = "all_day")
    private boolean all_day;
    @Column(name = "url")
    private String url;
    @Column(name = "email")
    private String email;

    public EventEntity() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStart_date() {
        return start_date;
    }
    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }
    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }

    public boolean isAll_day() {
        return all_day;
    }
    public void setAll_day(boolean all_day) {
        this.all_day = all_day;
    }

    public void setUrl(String url) { this.url = url; }
    public String getUrl() { return this.url; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}

