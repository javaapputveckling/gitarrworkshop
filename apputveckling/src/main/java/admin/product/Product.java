package admin.product;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * this class is used to get the list of products from the database
 * and to show the details of a specific product
 */
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // used to generate the id automatically
    @Column(name = "PRODUCT_ID")
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImages> productImages = new ArrayList<>();

    public Product() {
    }

    /**
     * this method is used to get the first image of the product
     * @return the first image of the product
     */
    public String getFirstImage(){
        if (!productImages.isEmpty()) { // check if the product has images
            byte[] imgData = productImages.get(0).getImgData(); // get the first image
            if (imgData != null) { // check if the image is not null
                String base64Image = Base64.getEncoder().encodeToString(imgData); // convert the image to base64 string
                return "data:image/jpeg;base64," + base64Image; // return the image as base64 string with the prefix
            }
        }
        return null;
    }


    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
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
