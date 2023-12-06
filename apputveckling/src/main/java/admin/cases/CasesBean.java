package admin.cases;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named
@Transactional
@SessionScoped
public class CasesBean implements Serializable{
    @Produces
    @PersistenceContext(unitName = "PRODUCT")
    private EntityManager entityManager;
    List<Cases> cases;
    List<Cases> cases_details;
    private String isShowProductDetails = "fales";
    Cases newCase = new Cases();
    private String newCaseDesc;
    private String newCaseStatus;
    private String newCaseDateStart;
    private String newCaseDateEnd;
    private String newCaseProfit;
    private int newCaseHours;
    private String newCaseType;

    // Fields for adding a new journal
    private String newJournalDesc;
    private int newJournalId;


    // Getter and setter methods for the new case fields
    public String getNewCaseDesc() {
        return newCaseDesc;
    }

    public void setNewCaseDesc(String newCaseDesc) {
        this.newCaseDesc = newCaseDesc;
    }

    public String getNewCaseStatus() {
        return newCaseStatus;
    }

    public void setNewCaseStatus(String newCaseStatus) {
        this.newCaseStatus = newCaseStatus;
    }

    public String getNewCaseDateStart() {
        return newCaseDateStart;
    }

    public void setNewCaseDateStart(String newCaseDateStart) {
        this.newCaseDateStart = newCaseDateStart;
    }

    public String getNewCaseDateEnd() {
        return newCaseDateEnd;
    }

    public void setNewCaseDateEnd(String newCaseDateEnd) {
        this.newCaseDateEnd = newCaseDateEnd;
    }

    public String getNewCaseProfit() {
        return newCaseProfit;
    }

    public void setNewCaseProfit(String newCaseProfit) {
        this.newCaseProfit = newCaseProfit;
    }

    public int getNewCaseHours() {
        return newCaseHours;
    }

    public void setNewCaseHours(int newCaseHours) {
        this.newCaseHours = newCaseHours;
    }

    public String getNewCaseType() {
        return newCaseType;
    }

    public void setNewCaseType(String newCaseType) {
        this.newCaseType = newCaseType;
    }

    // Getter and setter methods for the new journal fields
    public String getNewJournalDesc() {
        return newJournalDesc;
    }

    public void setNewJournalDesc(String newJournalDesc) {
        this.newJournalDesc = newJournalDesc;
    }

    public int getNewJournalId() {
        return newJournalId;
    }

    public void setNewJournalId(int newJournalId) {
        this.newJournalId = newJournalId;
    }

    public CasesBean(){}

    public void setCasesDetails(List<Cases> cases_details){
        this.cases_details = cases_details;
    }

    public void showProductDetails(int caseId){
        cases_details = entityManager.createQuery("select p from Cases p where p.CASE_ID = :caseId", Cases.class)
            .setParameter("caseId", caseId)
                .getResultList();
        isShowProductDetails = "true";
    }

    public List<Cases> getCases(){
        cases = entityManager.createQuery("select p from Cases p", Cases.class).getResultList();
        return cases;
    }

    public void setCases(List<Cases> cases){
        this.cases = cases;
    }

    public void addCase() {
        try {
            // Create a new Cases instance
            Cases newCase = new Cases();
            newCase.setCASE_DESC(newCaseDesc);
            newCase.setCASE_STATUS(newCaseStatus);
            newCase.setCASE_DATE_START(newCaseDateStart);
            newCase.setCASE_DATE_END(newCaseDateEnd);
            newCase.setCASE_PROFIT(newCaseProfit);
            newCase.setCASE_HOURS(newCaseHours);
            newCase.setCASE_TYPE(newCaseType);

            // Create a new CaseJournal instance
            CaseJournal newJournal = new CaseJournal();
            newJournal.setJOURNAL_DESC(newJournalDesc);
            newJournal.setJOURNAL_ID(newJournalId);
            newJournal.setaCase(newCase);

            // Add the new CaseJournal to the list of case journals in the Cases object
            newCase.getCaseJournals().add(newJournal);

            // Persist the changes
            entityManager.persist(newCase);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Case added successfully!", null));

            // Reset input fields for future additions
            resetInputFields();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding Case: " + e.getMessage(), null));
        }
    }
    private void resetInputFields() {
        newCaseDesc = null;
        newCaseStatus = null;
        newCaseDateStart = null;
        newCaseDateEnd = null;
        newCaseProfit = null;
        newCaseHours = 0;
        newCaseType = null;
        newJournalDesc = null;
        newJournalId = 0;
    }

    public static void main(String[] args) {
        // This main method can be used for testing the addCase functionality
        // Create an instance of CasesBean using a CDI container
        CasesBean casesBean = new CasesBean();

        // Set values for the new case and journal (you may modify these as needed)
        casesBean.setNewCaseDesc("Test Case");
        casesBean.setNewCaseStatus("InProgress");
        casesBean.setNewCaseDateStart("2023-01-01");
        casesBean.setNewCaseDateEnd("2023-01-15");
        casesBean.setNewCaseProfit("1000");
        casesBean.setNewCaseHours(10);
        casesBean.setNewCaseType("TypeA");

        casesBean.setNewJournalDesc("Journal for Test Case");
        casesBean.setNewJournalId(1);

        // Call the addCase method
        casesBean.addCase();
    }
    public String editCase(int caseId) {
        return "/includes/editCase?faces-redirect=true&caseId=" + caseId;
    }


}