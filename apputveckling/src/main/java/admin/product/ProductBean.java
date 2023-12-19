package admin.product;

import com.mysql.cj.jdbc.exceptions.PacketTooBigException;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;


/**
 * this class is used to get the list of products from the database
 * and to show the details of a specific product
 *
 * @see Product for the product entity
 */
@Named
@ViewScoped
public class ProductBean implements Serializable {
    @Produces
    @PersistenceContext(unitName = "PRODUCT")
    private EntityManager entityManager;
    Product newProduct = new Product();
    private List<Part>imageFile;
    List<Product> products; // used to get the list of products from the database
    List<Product> productsDetails; // used to show the details of a specific product
    private String isShowProductDetails = "false"; // used to show the details of a specific product

    List<ProductImages> productImagesList; // used to get the list of product images from the database
    private Part saveProductImages; // used to save the product images to the database

    private boolean showForm = false;
    private int product_id;


    public boolean getShowForm() {
        return showForm;
    }

    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }
    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    /**
     * this method is used to get the value of isShowProductDetails
     *
     * @return the value of isShowProductDetails
     */
    public String getIsShowProductDetails() {
        return isShowProductDetails;
    }

    public void setIsShowProductDetails(String isShowProductDetails) {
        this.isShowProductDetails = isShowProductDetails;
    }

    public void setProductsDetails(List<Product> productsDetails) {
        this.productsDetails = productsDetails;
    }

    public List<Part> getImageFile() {
        return imageFile;
    }

    public void setImageFile(List<Part> imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * this method is used to show the details of a specific product
     *
     * @param productId the id of the product
     *                  the id is used to get the product from the database
     *                  and to show the details of the product
     * @see Product for the product entity
     */
    public void showProductDetails(int productId) {
        productsDetails = entityManager.createQuery("SELECT p FROM Product p WHERE p.PRODUCT_ID = :productId", Product.class)
                .setParameter("productId", productId)
                .getResultList();
        isShowProductDetails = "true";

    }

    public List<ProductImages> getProductImagesList() {
        productImagesList = entityManager.createQuery("SELECT p FROM ProductImages p", ProductImages.class).getResultList();
        return productImagesList;
    }

    public void setProductImagesList(List<ProductImages> productImagesList) {
        this.productImagesList = productImagesList;
    }

    /**
     * this method is used to get the list of products from the database
     *
     * @return the list of products
     */
    public List<Product> getProducts() {
        products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        return products;
    }

    /**
     * this method is used to set the list of products
     *
     * @param products the list of products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * this method is used to get the list of products from the database
     *
     * @return the list of products
     */
    public List<Product> getProductsDetails() {
        return productsDetails;
    }



    /**
     * this method is used to update a product in the database
     *
     * @param productId the id of the product
     */
    @Transactional
    public void deleteProduct(int productId) {
        entityManager.createQuery("DELETE FROM Product p WHERE p.PRODUCT_ID = :productId")
                .setParameter("productId", productId).executeUpdate();
    }

    @Transactional
    public void getProductImages(Product product) throws IOException{
        try {
            for(Part image: imageFile) {
                ProductImages productImages = new ProductImages();
                byte[] imageData = image.getInputStream().readAllBytes();
                productImages.setImgData(imageData);
                productImages.setProduct(product);
                product.getProductImages().add(productImages);
            }
        }catch (Exception e) {
            throw e;
        }
    }


    @Transactional
    public void updateProduct(int product_id) throws IOException {
        try {

            Product product = entityManager.find(Product.class, product_id);
            product.setPRODUCT_NAME(productsDetails.get(0).getPRODUCT_NAME());
            product.setPRODUCT_YEAR(productsDetails.get(0).getPRODUCT_YEAR());
            product.setPRODUCT_HISTORY_DESC(productsDetails.get(0).getPRODUCT_HISTORY_DESC());
            product.setPRODUCT_MAIN_DESC(productsDetails.get(0).getPRODUCT_MAIN_DESC());
            product.setPRODUCT_PRICE(productsDetails.get(0).getPRODUCT_PRICE());

            getProductImages(product);
            entityManager.merge(product);
            reload();
            setIsShowProductDetails("false");

        }catch (Exception e) {
            throw e;
        }
    }


    /**
     *
     * @throws IOException
     */
    @Transactional
    public void addProduct() throws IOException {
        try {
            Product product = new Product();
            product.setPRODUCT_NAME(newProduct.getPRODUCT_NAME());
            product.setPRODUCT_YEAR(newProduct.getPRODUCT_YEAR());
            product.setPRODUCT_HISTORY_DESC(newProduct.getPRODUCT_HISTORY_DESC());
            product.setPRODUCT_MAIN_DESC(newProduct.getPRODUCT_MAIN_DESC());
            product.setPRODUCT_PRICE(newProduct.getPRODUCT_PRICE());

            getProductImages(product);
            entityManager.persist(product);
            reload();
            showForm=false;


        }catch (PersistenceException e) {
            Throwable cause = e;
            while(cause.getCause() != null && !(cause instanceof PacketTooBigException)) {
                cause = cause.getCause();
            }
            if (cause instanceof PacketTooBigException) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Upload Error", "The size of the file selected is too large");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                throw e;
            }
        } catch (Exception e){
            throw e;
        }

    }

    public void reload() {
        products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    private void saveImage(String filename, String path, Part image) throws IOException {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            InputStream inputStream = image.getInputStream();
            File file = new File(dir, filename);

            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void changeShowForm(){
        this.showForm =! showForm;
    }

}
