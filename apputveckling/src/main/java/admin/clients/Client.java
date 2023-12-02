package admin.clients;

import java.io.Serializable;

public class Client implements Serializable {

    // The serialVersionUID is a universal version identifier for a Serializable class.
    //private static final long serialVersionUID = 1L;

    private int clientID;
    private String name;
    private String phone;
    private String email;

    private String date;

    // Constructors, getters, and setters

    public Client() {
        // Default constructor
    }

    public Client(int clientID, String name, String phone, String email, String date) {
        this.clientID = clientID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.date = date;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


