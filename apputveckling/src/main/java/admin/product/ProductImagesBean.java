package admin.product;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
@SessionScoped
public class ProductImagesBean implements Serializable {

    @Produces
    @PersistenceContext(unitName = "ProductImages")
    private EntityManager entityManager;
    List<ProductImages> productImages;
    List<ProductImages> oneImage; // for the product page
    public ProductImagesBean() {
    }

    /**
     * Get all product images from the database
     * @param productId the product id
     * @return a list of product images
     */
    public List<ProductImages> getProductImages(int productId) {
        productImages = entityManager.createQuery("select p from ProductImages p where p.PRODUCT_ID = :productId", ProductImages.class)
                .setParameter("productId", productId)
                .getResultList();
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }

    /**
     * Get one product image from the database
     * @param productId the product id
     * @return a string of the image path
     */
    public String getOneImage(int productId) {
       oneImage = entityManager.createQuery("select p from ProductImages p where p.PRODUCT_ID = :productId", ProductImages.class)
                .setParameter("productId", productId)
                .getResultList();
        return oneImage.get(0).getImgPathString();
    }


    public void setOneImage(List<ProductImages> oneImage) {
        this.oneImage = oneImage;
    }

    /**
     * Add product images to the database
     * @param productId the product id
     * @param imgPathString the image path string [example:image49.jpg]
     * @Deprecated need to be removed in the future or changed to a better method
     */
    @Transactional
    public void addProductImages(int productId, String imgPathString) {
        ProductImages productImages = new ProductImages();
        productImages.setPRODUCT_ID(productId);
        productImages.setImgPathString(imgPathString);
        entityManager.persist(productImages);
    }
   
}
