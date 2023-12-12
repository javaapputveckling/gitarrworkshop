package admin.product;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Product images bean
 * This class is used to get product images from the database
 * This class is also used to add product images to the database
 * This class is also used to get one product image from the database
 */
@Named
@RequestScoped
public class ProductImagesBean implements Serializable {

    @Produces
    @PersistenceContext(unitName = "ProductImages")
    private EntityManager entityManager;
    List<ProductImages> productImages;
    public ProductImagesBean() {
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }


    public List<ProductImages> getProductImages() {
        productImages = entityManager.createQuery("select p from ProductImages p", ProductImages.class).getResultList();
        return productImages;
    }

    private String getImgPath() {
        String webapp = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        return webapp+"../../src/main/webapp/resources/img";
    }


}
