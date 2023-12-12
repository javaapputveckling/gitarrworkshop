package admin.product;

import jakarta.faces.context.FacesContext;
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
    @Column(name = "PROD_IMG_ID")
    private int PROD_IMG_ID;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @Column(name = "IMG_PATH_STRING")
    private String imgPathString;


    public int getPROD_IMG_ID() {
        return PROD_IMG_ID;
    }

    public void setPROD_IMG_ID(int PROD_IMG_ID) {
        this.PROD_IMG_ID = PROD_IMG_ID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setImgPathString(String imgPathString) {
        this.imgPathString = imgPathString;
    }

    public String getImgPathString() {
        return imgPathString;
    }

    /**
     *
     * @return
     */
    public String getImgPathStringToSave() {
        String webapp = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        return webapp+"../../src/main/webapp/img";

    }

}
