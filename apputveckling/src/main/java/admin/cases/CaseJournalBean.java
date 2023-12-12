package admin.cases;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
//import jakarta.faces.application.FacesMessage;
//import jakarta.faces.context.FacesContext;
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
  /*  public void saveCaseJournalWithCase(int caseId, String caseJournalDescription, int caseJournalId) {
        try {
            Cases aCase = entityManager.find(Cases.class, caseId);

            CaseJournal newJournal = new CaseJournal();
            newJournal.setJOURNAL_DESC(caseJournalDescription);
            newJournal.setJOURNAL_ID(caseJournalId);
            newJournal.setaCase(aCase);

            aCase.getCaseJournals().add(newJournal);

            entityManager.persist(newJournal);
            entityManager.merge(aCase);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Case Journal added successfully!", null));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding Case Journal: " + e.getMessage(), null));
        }
    }*/
}
