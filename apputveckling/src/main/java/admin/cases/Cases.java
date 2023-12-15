package admin.cases;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CASES")
@NamedQuery(name = "Cases.All", query = "SELECT cases FROM Cases cases")
public class Cases{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CASE_ID;
    @Column(name = "CASE_DESC")
    private String CASE_DESC;
    @Column(name = "STATUS")
    private String STATUS;
    @Column(name = "CASE_DATE_START")
    private String CASE_DATE_START;
    @Column(name = "CASE_DATE_END")
    private String CASE_DATE_END;
    @Column(name = "CASE_PROFIT")
    private String CASE_PROFIT;
    @Column(name = "CASE_HOURS")
    private int CASE_HOURS;
    @Column(name = "CASE_TYPE")
    private String CASE_TYPE;
    @Column(name = "MEMBER_ID")
    private int MEMBER_ID;
/*    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Client> test = new ArrayList<>();*/
    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaseJournal> caseJournals = new ArrayList<>();
    public List<CaseJournal> getCaseJournals() {
        return caseJournals;
    }
    public void setCaseJournals(List<CaseJournal> caseJournals) {
        this.caseJournals = caseJournals;
    }
    public Cases(){
    }
    public int getCASE_ID(){
        return CASE_ID;
    }
    public void setCASE_ID(int PRODUCT_ID){
        this.CASE_ID = CASE_ID;
    }
    public void setCASE_DESC(String CASE_DESC){
        this.CASE_DESC = CASE_DESC;
    }
    public String getCASE_DESC(){
        return CASE_DESC;
    }
    public void setCASE_STATUS(String STATUS){
        this.STATUS = STATUS;
    }
    public String getCASE_STATUS(){
        return STATUS;
    }
    public void setCASE_DATE_START(String CASE_DATE_START){
        this.CASE_DATE_START = CASE_DATE_START;
    }
    public String getCASE_DATE_START(){
        return CASE_DATE_START;
    }
    public void setCASE_DATE_END(String CASE_DATE_END){
        this.CASE_DATE_END = CASE_DATE_END;
    }
    public String getCASE_DATE_END(){
        return CASE_DATE_END;
    }
    public void setCASE_TYPE(String CASE_TYPE){
        this.CASE_TYPE = CASE_TYPE;
    }
    public String getCASE_TYPE(){
        return CASE_TYPE;
    }
    public void setCASE_PROFIT(String CASE_PROFIT){
        this.CASE_PROFIT = CASE_PROFIT;
    }
    public String getCASE_PROFIT(){
        return CASE_PROFIT;
    }
    public void setCASE_HOURS(int CASE_HOURS){
        this.CASE_HOURS = CASE_HOURS;
    }
    public int getCASE_HOURS(){
        return CASE_HOURS;
    }
   public void setMEMBER_ID(int MEMBER_ID){
        this.MEMBER_ID = MEMBER_ID;
   }
    public int getMEMBER_ID(){
        return MEMBER_ID;
    }

}
