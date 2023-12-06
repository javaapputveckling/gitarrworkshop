package admin.cases;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

import java.io.Serializable;

@Named
@Transactional
@SessionScoped
public class EditCaseBean implements Serializable {

    @PersistenceContext(unitName = "PRODUCT")
    private EntityManager entityManager;

    private String editCaseDesc;
    private String editCaseStatus;
    private String editCaseType;
    private String editCaseHours;
    private String editCaseStartDate;
    private String editCaseEndDate;
    private String editCaseCost;

    // Getter and setter methods for each property

    public String getEditCaseDesc() {
        return editCaseDesc;
    }

    public void setEditCaseDesc(String editCaseDesc) {
        this.editCaseDesc = editCaseDesc;
    }

    public String getEditCaseStatus() {
        return editCaseStatus;
    }

    public void setEditCaseStatus(String editCaseStatus) {
        this.editCaseStatus = editCaseStatus;
    }

    public String getEditCaseType() {
        return editCaseType;
    }

    public void setEditCaseType(String editCaseType) {
        this.editCaseType = editCaseType;
    }

    public String getEditCaseHours() {
        return editCaseHours;
    }

    public void setEditCaseHours(String editCaseHours) {
        this.editCaseHours = editCaseHours;
    }

    public String getEditCaseStartDate() {
        return editCaseStartDate;
    }

    public void setEditCaseStartDate(String editCaseStartDate) {
        this.editCaseStartDate = editCaseStartDate;
    }

    public String getEditCaseEndDate() {
        return editCaseEndDate;
    }

    public void setEditCaseEndDate(String editCaseEndDate) {
        this.editCaseEndDate = editCaseEndDate;
    }

    public String getEditCaseCost() {
        return editCaseCost;
    }

    public void setEditCaseCost(String editCaseCost) {
        this.editCaseCost = editCaseCost;
    }

    // Other methods (e.g., updateCase) as needed

    @Transactional
    public void updateCase() {
        try {
            // Retrieve the existing Cases entity from the database based on some identifier (e.g., caseId)
            int caseId = 1; // Replace with the actual caseId or obtain it from the request parameters
            Cases existingCase = entityManager.find(Cases.class, caseId);

            // Apply the changes
            existingCase.setCASE_DESC(editCaseDesc);
            existingCase.setCASE_STATUS(editCaseStatus);
            existingCase.setCASE_TYPE(editCaseType);
            existingCase.setCASE_HOURS(Integer.parseInt(editCaseHours)); // Assuming hours is an integer
            existingCase.setCASE_DATE_START(editCaseStartDate);
            existingCase.setCASE_DATE_END(editCaseEndDate);
            existingCase.setCASE_PROFIT(editCaseCost);

            // Persist the updated entity back to the database
            entityManager.merge(existingCase);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Case updated successfully!", null));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating case: " + e.getMessage(), null));
        }
    }
}
