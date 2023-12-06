package admin.cases;

import jakarta.persistence.*;


@Entity
@Table(name = "CASE_JOURNAL")

public class CaseJournal{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int JOURNAL_ID;
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private Cases aCase;
    @Column(name = "JOURNAL_DESC")
    private String JOURNAL_DESC;

    public CaseJournal(){

    }
    public void setJOURNAL_ID(int JOURNAL_ID){
        this.JOURNAL_ID = JOURNAL_ID;
    }
    public int getJOURNAL_ID(){
        return JOURNAL_ID;
    }

    public void setaCase(Cases aCase){
        this.aCase = aCase;
    }
    public Cases getaCase(){
        return aCase;
    }
    public void setJOURNAL_DESC(String JOURNAL_DESC){
        this.JOURNAL_DESC = JOURNAL_DESC;
    }
    public String getJOURNAL_DESC(){
        return JOURNAL_DESC;
    }

}
