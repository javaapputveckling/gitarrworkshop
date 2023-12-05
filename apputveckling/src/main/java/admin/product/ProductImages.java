package admin.product;

import jakarta.persistence.*;

/**
 * Product images entity
 * This class is used to get product images from the database
 */
@Entity
@Table(name = "PROD_IMG")
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PROD_IMG_ID;
    @Column(name = "PRODUCT_ID")
    private int PRODUCT_ID;
    @Column(name = "IMG_PATH_STRING")
    private String imgPathString;

    public int getPROD_IMG_ID() {
        return PROD_IMG_ID;
    }

    public void setPROD_IMG_ID(int PROD_IMG_ID) {
        this.PROD_IMG_ID = PROD_IMG_ID;
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(int PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public String getImgPathString() {
        return imgPathString;
    }

    public void setImgPathString(String imgPathString) {
        this.imgPathString = imgPathString;
    }

}
