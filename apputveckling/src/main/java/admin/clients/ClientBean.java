package admin.clients;
import admin.DB.DB;
import admin.cases.Cases;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class ClientBean implements Serializable {
    private Client clientTOedit;
    private String passwordTemp;
    private Cases caseData = new Cases();

    private String searchName;
    private List<Client> clientList;
    private   List<Cases> caseList;

    private List<Client> historyClientList;



    @PersistenceContext(unitName = "mysql_web")
    EntityManager em;


    public Cases getCaseData() {
        return caseData;
    }

    public void setCaseData(Cases caseData) {
        this.caseData = caseData;
    }

    public List<Cases> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Cases> caseList) {
        this.caseList = caseList;
    }
    public String getPasswordTemp() {
        return passwordTemp;
    }

    public void setPasswordTemp(String passwordTemp) {
        this.passwordTemp = passwordTemp;
    }

    public Client getClientTOedit() {
        return clientTOedit;
    }

    public void setClientTOedit(Client clientTOedit) {
        this.clientTOedit = clientTOedit;
    }


    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public List<Client> getHistoryClientList() {
        return historyClientList;
    }

    public void setHistoryClientList(List<Client> historyClientList) {
        this.historyClientList = historyClientList;
    }

    public void newClient(){
        clientTOedit = new Client();
    }
    @PostConstruct
    public void init() {

        TypedQuery<Client> query2 = em.createQuery(
                "SELECT c FROM Client c WHERE EXISTS " +
                        "(SELECT 1 FROM Cases ca WHERE c.clientId = ca.MEMBER_ID AND ca.STATUS = 'Closed')",
                Client.class);

        historyClientList = query2.getResultList();

        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        clientList = query.getResultList();

    }


    @Transactional
    public String editClient() {

        Client temp = em.find(Client.class, clientTOedit.getClientId());
        temp.setClientDate(clientTOedit.getClientDate());
        temp.setClientEmail(clientTOedit.getClientEmail());
        temp.setClientName(clientTOedit.getClientName());
        temp.setClientPhone(clientTOedit.getClientPhone());

        em.merge(clientTOedit);
        init();
        return "customerInfo.xhtml";
    }

    public String collectClientInfo(int clientID){
        if(this.caseList != null){
            caseList.clear();
        }

        caseList = em.createQuery("select p from Cases p where p.MEMBER_ID = " + clientID +" ORDER BY p.CASE_DATE_START DESC", Cases.class)
                .getResultList();

        Query query = em.createQuery("SELECT c FROM Client c WHERE c.clientId =:clientID", Client.class);
        query.setParameter("clientID", clientID);
        clientTOedit = (Client) query.getSingleResult();

        return "customerInfo.xhtml";
    }

    @Transactional
    public String addClient() {
        int count = 0;

        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        clientList = query.getResultList();

        for (Client x : clientList) {
            if (clientTOedit.getClientEmail().equals(x.getClientEmail()) || clientTOedit.getClientPhone().equals(x.getClientPhone())) {
                count = count + 1;
            }
        }

        if (count == 0) {
            em.persist(clientTOedit);
        }

        init();
        return "admin_clients";
    }


    @Transactional
    public String deleteEntity(int clientID) {
        // Find the entity by ID
        Client entityToDelete = em.find(Client.class, clientID);

        // Check if the entity exists before attempting to delete
        if (entityToDelete != null) {
            // Remove the entity from the database
            em.remove(entityToDelete);
        }

        init();

        return "admin_clients.xhtml"; // Return the name of the page where the user can see the member list
    }

    public void SearchClient(){

        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.clientName LIKE :searchName", Client.class);
        query.setParameter("searchName", "%" + searchName + "%");
        List<Client> matchList = query.getResultList();

        Iterator<Client> iterator = clientList.iterator();
        while (iterator.hasNext()) {
            Client match = iterator.next();
            if (matchList.stream().anyMatch(name -> name.getClientName().equals(match.getClientName()))) {
                iterator.remove(); // Remove the matching client
            }
        }

        clientList.addAll(0, matchList);

    }

    public void FindCase(int clientID){

        caseList = em.createQuery("select p from Cases p where p.MEMBER_ID = " + clientID +" ORDER BY p.CASE_DATE_START DESC", Cases.class)
                .getResultList();
        caseData = caseList.get(0);

    }


}