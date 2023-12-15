package admin.product;

import com.mysql.cj.jdbc.exceptions.PacketTooBigException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Inject
    private ProductBean productBean;

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
        //return webapp+"../../src/main/webapp/resources/img";
        return webapp + "/resources/img";

    }

    @Transactional
    public void insertImage(int productId, String filePath) throws IOException {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

            ProductImages image = new ProductImages();
            image.setProduct(entityManager.find(Product.class, productId)); // Set product
            image.setImgData(fileContent); // Set image data

            entityManager.persist(image);
            FacesContext.getCurrentInstance().addMessage("message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Picture uploaded."));
        } catch (PersistenceException e) {
            if (e.getCause() instanceof PacketTooBigException) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "The size of the file selected is too large");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                throw e; // rethrow the exception if it's not a PacketTooBigException
            }
        }
    }

    /**
     * This method is used to insert product images to the database.
     * Insertions from the add_data.sql file are moved here.
     */
    @Transactional
    public void firstTimeInsertion() throws IOException {

        String imgPath = getImgPath();
        insertImage(1, imgPath + "/BobMarley.JPG");
        insertImage(1, imgPath + "/IMG_1186.JPG");
        insertImage(1, imgPath + "/IMG_1187.JPG");
        insertImage(1, imgPath + "/IMG_1188.JPG");
        insertImage(2, imgPath + "/IMG_1189.JPG");
        insertImage(2, imgPath + "/BobMarley.JPG");
        insertImage(3, imgPath + "/IMG_1186.JPG");
        insertImage(3, imgPath + "/IMG_1187.JPG");
        insertImage(4, imgPath + "/IMG_1188.JPG");
        insertImage(4, imgPath + "/IMG_1189.JPG");
        insertImage(5, imgPath + "/BobMarley.JPG");
        insertImage(6, imgPath + "/IMG_1186.JPG");
        insertImage(7, imgPath + "/IMG_1187.JPG");
        productBean.reload();
        getProductImages();
    }
}
