package admin.clients;
import com.mysql.cj.jdbc.MysqlDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.io.Serializable;

@Named
@SessionScoped
public class ClientSearch implements Serializable{

    private String inputtext;
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        this.inputtext="";

        MysqlDataSource dataSource1 = new MysqlDataSource();
        dataSource1.setURL("jdbc:mysql://localhost:3306/Projectdb");
        dataSource1.setUser("root2");
        dataSource1.setPassword("1234");
        dataSource = dataSource1;

        // Load member data from the database during bean initialization
    }

    public String getInputtext() {
        return inputtext;
    }

    public void setInputtext(String inputtext) {
        this.inputtext = inputtext;
    }

    public void Submited(){

    }
}
