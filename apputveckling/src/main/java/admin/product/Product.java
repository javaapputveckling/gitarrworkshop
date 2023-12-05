package admin.product;

import jakarta.persistence.*;

/**
 * this class is used to get the list of products from the database
 * and to show the details of a specific product
 */
@Entity
@Table(name = "PRODUCT")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // used to generate the id automatically
    private int PRODUCT_ID;  // the id of the product
    @Column(name = "PRODUCT_NAME")
    private String PRODUCT_NAME;
    @Column(name = "PRODUCT_PRICE")
    private int PRODUCT_PRICE;
    @Column(name = "PRODUCT_MAIN_DESC")
    private String PRODUCT_MAIN_DESC;
    @Column(name = "PRODUCT_HISTORY_DESC")
    private String PRODUCT_HISTORY_DESC;
    @Column(name = "PRODUCT_YEAR")
    private int PRODUCT_YEAR;

    public Product() {
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(int PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public int getPRODUCT_PRICE() {
        return PRODUCT_PRICE;
    }

    public void setPRODUCT_PRICE(int PRODUCT_PRICE) {
        this.PRODUCT_PRICE = PRODUCT_PRICE;
    }

    public String getPRODUCT_MAIN_DESC() {
        return PRODUCT_MAIN_DESC;
    }

    public void setPRODUCT_MAIN_DESC(String PRODUCT_MAIN_DESC) {
        this.PRODUCT_MAIN_DESC = PRODUCT_MAIN_DESC;
    }

    public String getPRODUCT_HISTORY_DESC() {
        return PRODUCT_HISTORY_DESC;
    }

    public void setPRODUCT_HISTORY_DESC(String PRODUCT_HISTORY_DESC) {
        this.PRODUCT_HISTORY_DESC = PRODUCT_HISTORY_DESC;
    }

    public int getPRODUCT_YEAR() {
        return PRODUCT_YEAR;
    }

    public void setPRODUCT_YEAR(int PRODUCT_YEAR) {
        this.PRODUCT_YEAR = PRODUCT_YEAR;
    }
}
