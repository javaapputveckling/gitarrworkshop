package admin.cases;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Named
@Transactional
@SessionScoped
public class CasesBean implements Serializable{
    @Produces
    @PersistenceContext(unitName = "PRODUCT")
    private EntityManager entityManager;
    List<Cases> cases;
    List<Cases> cases_details;
    Cases caseToEdit;
    private String newCaseDesc;
    private String newCaseStatus;
    private String newCaseDateStart;
    private String newCaseDateEnd;
    private String newCaseProfit;
    private int newCaseHours;
    private int newMemberId;
    private String newCaseType;
    private String newJournalDesc;
    private int newJournalId;
    public void setNewMemberId(int newMemberId){
        this.newMemberId = newMemberId;
    }
    public int getNewMemberId(){
        return newMemberId;
    }
    public Cases getCaseToEdit(){
        return caseToEdit;
    }
    public void setCaseToEdit(Cases caseToEdit){
        this.caseToEdit = caseToEdit;
    }
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
    public String editCase(int caseId) {
        caseToEdit = entityManager.createQuery("select p from Cases p where p.CASE_ID = :caseId", Cases.class)
                .setParameter("caseId", caseId)
                .getSingleResult();
        return "/includes/editCase?faces-redirect=true&caseId=" + caseId;
    }
    public String goBack() {
        return "/views/admin_cases?faces-redirect=true";
    }
    public List<Cases> getCases(){
        cases = entityManager.createQuery("select p from Cases p", Cases.class).getResultList();
        return cases;
    }
    public String updateCase(int caseId){
        try {
            entityManager.merge(caseToEdit);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Changes saved successfully!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error saving changes", null));
        }
        return "/views/admin_cases?faces-redirect=true";
    }
    public void setCases(List<Cases> cases){
        this.cases = cases;
    }
    public String addCase() {
        Cases newCase = new Cases();
        newCase.setCASE_DESC(newCaseDesc);
        newCase.setCASE_STATUS(newCaseStatus);
        newCase.setCASE_DATE_START(String.valueOf(LocalDate.now()));
        newCase.setCASE_DATE_END(newCaseDateEnd);
        newCase.setCASE_PROFIT(newCaseProfit);
        newCase.setCASE_HOURS(newCaseHours);
        newCase.setCASE_TYPE(newCaseType);
        newCase.setMEMBER_ID(newMemberId);

        CaseJournal newJournal = new CaseJournal();
        newJournal.setJOURNAL_DESC(newJournalDesc);
        newJournal.setJOURNAL_ID(newJournalId);
        newJournal.setaCase(newCase);

        newCase.getCaseJournals().add(newJournal);

        entityManager.persist(newCase);
        resetInputFields();
        return"admin_cases?faces-redirect=true";
    }
    private String resetInputFields() {
        newCaseDesc = null;
        newCaseStatus = null;
        newCaseDateStart = null;
        newCaseDateEnd = null;
        newCaseProfit = null;
        newCaseHours = 0;
        newCaseType = null;
        newJournalDesc = null;
        newJournalId = 0;
        return "admin_cases";
    }
    public String getStatusColor(String status) {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("Done", "green");
        colorMap.put("Open", "yellow");
        colorMap.put("Closed", "gray");

        return colorMap.getOrDefault(status, "transparent");
    }
}

