package admin.cases;
import admin.clients.Client;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.primefaces.util.IOUtils;


@Named
@Transactional
@SessionScoped
public class CasesBean implements Serializable {
    @Produces
    @PersistenceContext(unitName = "PRODUCT")

    private EntityManager entityManager;
    List<Cases> cases;
    List<Cases> client_cases;
    List<Cases> cases_details;
    Cases caseToEdit;
    int member_id;
    private String newCaseDesc;
    private byte[] journalImageFile;

    public byte[] getJournalImageFile() {
        return journalImageFile;
    }

    public void setJournalImageFile(byte[] journalImageFile) {
        this.journalImageFile = journalImageFile;
    }

    private String newCaseStatus;
    private String newCaseDateStart;
    private String newCaseDateEnd;
    private String newCaseProfit;
    private int newCaseHours;
    private int newMemberId;
    private String newCaseType;
    private String newJournalDesc;
    private String newMemberEmail;



    public List<Cases> getCases_details() {
        return cases_details;
    }

    public void setNewMemberEmail(String newMemberEmail) {
        this.newMemberEmail = newMemberEmail;
    }

    public String getNewMemberEmail() {
        return newMemberEmail;
    }

    public void setNewMemberId(int newMemberId) {
        this.newMemberId = newMemberId;
    }

    public int getNewMemberId() {
        return newMemberId;
    }

    public Cases getCaseToEdit() {
        return caseToEdit;
    }

    public void setCaseToEdit(Cases caseToEdit) {
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

    public CasesBean() {
    }

    public void setCasesDetails(List<Cases> cases_details) {
        this.cases_details = cases_details;
    }

    public void setCases_details(List<Cases> cases_details) {
        this.cases_details = cases_details;
    }

    public String editCase(int caseId) {
        caseToEdit = entityManager.createQuery("select p from Cases p where p.CASE_ID = :caseId", Cases.class)
                .setParameter("caseId", caseId)
                .getSingleResult();
        if (caseToEdit.getCASE_STATUS().equalsIgnoreCase("Closed")) {
            if (caseToEdit.getCASE_DATE_END() == null) {
                caseToEdit.setCASE_DATE_END(String.valueOf(LocalDate.now()));
            }
        }
        return "/includes/editCase?faces-redirect=true&caseId=" + caseId;
    }

    public void addNewCaseJournal() {
        try {
            if (caseToEdit != null) {
                setJournalImageFile(journalImageFile);
                CaseJournal newJournal = new CaseJournal();
                newJournal.setJOURNAL_DESC(newJournalDesc);
                newJournal.setaCase(caseToEdit);
                caseToEdit.getCaseJournals().add(newJournal);
                entityManager.persist(newJournal);

                newJournalDesc = null;

                PrimeFaces.current().executeScript("PF('addjournal').hide();");
                PrimeFaces.current().ajax().update(":myform");
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding new case journal: " + e.getMessage(), null));
        }
    }

    public int getMember_id(String email) {
        try {
            Client client = entityManager.createQuery("SELECT c FROM Client c WHERE c.clientEmail = :email", Client.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return client.getClientId();
        } catch (NoResultException e) {
            return -1;
        }
    }

    public String goBack() {
        return "/views/admin_cases?faces-redirect=true";
    }

    public List<Cases> getCases() {
        cases = entityManager.createQuery("select p from Cases p", Cases.class).getResultList();
        return cases;
    }
    public String getClientCases(int member_id) {
        cases_details = entityManager.createQuery("select p from Cases p WHERE p.MEMBER_ID = :id", Cases.class)
                .setParameter("id", member_id)
                .getResultList();
        return "/views/client/clientcase.xhtml";
    }

    public String updateCase(int caseId) {
        try {
            if (caseToEdit.getCASE_STATUS().equalsIgnoreCase("Closed")) {
                if (caseToEdit.getCASE_DATE_END() == null) {
                    caseToEdit.setCASE_DATE_END(String.valueOf(LocalDate.now()));
                }
            }
            entityManager.merge(caseToEdit);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Changes saved successfully!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error saving changes", null));
        }
        return "/views/admin_cases?faces-redirect=true";
    }
    public void setCases(List<Cases> cases) {
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
        int memberId = getMember_id(newMemberEmail);
        if (memberId == -1) {
            FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Client with email does not exist!", null);
            FacesContext.getCurrentInstance().addMessage("addCaseForm:errorMessages", errorMessage);
            return null;
        }
        newCase.setMEMBER_ID(memberId);

        CaseJournal newJournal = new CaseJournal();
        newJournal.setJOURNAL_DESC(newJournalDesc);
        newJournal.setJournalImageBytes(journalImageFile);
        newJournal.setaCase(newCase);

        newCase.getCaseJournals().add(newJournal);

        entityManager.persist(newCase);
        resetInputFields();
        return "admin_cases?faces-redirect=true";
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
        return "admin_cases";
    }
    public String getStatusColor(String status) {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("Done", "green");
        colorMap.put("Open", "yellow");
        colorMap.put("Closed", "gray");

        return colorMap.getOrDefault(status, "transparent");
    }
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();

        try {
            journalImageFile = IOUtils.toByteArray(uploadedFile.getInputStream());

            CaseJournal newJournal = new CaseJournal();
            newJournal.setaCase(caseToEdit);
            newJournal.setJournalImageBytes(journalImageFile);

            entityManager.persist(newJournal);

            System.out.println("File upload successful. Journal ID: " + newJournal.getJOURNAL_ID());

            FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            System.err.println("Error uploading file: " + e.getMessage());
            e.printStackTrace();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error uploading file");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    public static void main(String[] args){
   /*     byte [] testimg;
        File imageFile = new File("/Users/b/Documents/GitHub/gitarrworkshop/apputveckling/src/main/webapp/resources/img/BobMarley.JPG");
        Cases test = new Cases();
        CaseJournal test1 = new CaseJournal();
        test1.setJOURNAL_DESC("tetst test");
        test1.setJOURNAL_ID(50);
        test.setCASE_ID(80);
        test.setMEMBER_ID(20);
        CasesBean test4 = new CasesBean();
        test4.handleFileUpload();
        test4.setNewJournalDesc(test1.getJOURNAL_DESC());
        test4.setNewJournalDesc(test1.getJOURNAL_DESC());
        test4.setJournalImageFile(test1.getJournalImageBytes());
        test4.setNewMemberId(test.getMEMBER_ID());
        test4.setNewMemberEmail("Bashar@example.com");
        test4.addCase();
        */
    }
}

