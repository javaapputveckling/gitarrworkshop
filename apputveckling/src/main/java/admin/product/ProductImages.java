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

    @Lob
    @Column(name = "IMG_DATA")
    private byte[] imgData;


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

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }


    public String getBase64Image() {
        if (imgData != null) {
            String base64Image = java.util.Base64.getEncoder().encodeToString(imgData);
            return "data:image/jpeg;base64," + base64Image;
        }
        return null;
    }
}
