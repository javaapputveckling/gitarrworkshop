import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
@Named
@SessionScoped
public class NavigationBean implements java.io.Serializable{
    private String selectedSection = "home";
    private String currentPage = "home";
    public NavigationBean() {
    }
    public String goToClientHome(){
        this.selectedSection = "home";
        return "ClientHome";
    }
    public String goToClientProducts(){
        this.selectedSection = "product";
        return "ClientProduct";
    }
    public String goToClientReserve(){
        this.selectedSection = "Reserve";
        return "ClientAppointment";
    }

    public String getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(String selectedSection) {
        this.selectedSection = selectedSection;
    }

    public void redirectToAdminHome() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.redirect("admin_home.xhtml");
        facesContext.responseComplete();
    }
    //to be used in the future, if we want to do more with the admin page
    public String goToProductPage() throws IOException {
        return "admin_products";
    }
    public String goToCalendarPage() throws IOException {
        return "admin_calendar";
    }
    public String goToHomePage() throws IOException {
        return "admin_home";
    }
    public String goToCasesPage() throws IOException {
        return "admin_cases";
    }
    public String goToClients() throws IOException {
        return "admin_clients";
    }



}
