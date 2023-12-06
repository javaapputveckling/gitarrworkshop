package admin.cases;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
@Transactional

public class CaseJournalBean implements Serializable{
    @Produces
    @PersistenceContext(unitName = "PRODUCT")
    private EntityManager entityManager;
    List<CaseJournal> casejournlas;
    CaseJournal newJournal = new CaseJournal();
    public CaseJournalBean(){

    }

    public void setCases(List<CaseJournal> casejournlas){
        this.casejournlas = casejournlas;
    }

    public void saveCaseJournalWithCase(int caseId, String caseJournalDescription, int caseJournalId) {
        try {
            Cases aCase = entityManager.find(Cases.class, caseId);

            // Create a new CaseJournal instance
            CaseJournal newJournal = new CaseJournal();
            newJournal.setJOURNAL_DESC(caseJournalDescription);
            newJournal.setJOURNAL_ID(caseJournalId);
            newJournal.setaCase(aCase);

            // Add the new CaseJournal to the list of case journals in the Cases object
            aCase.getCaseJournals().add(newJournal);

            // Persist the changes
            entityManager.persist(newJournal);
            entityManager.merge(aCase);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Case Journal added successfully!", null));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding Case Journal: " + e.getMessage(), null));
        }
    }

}
