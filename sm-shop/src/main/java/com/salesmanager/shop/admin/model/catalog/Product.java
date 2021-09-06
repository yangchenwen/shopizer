package com.salesmanager.shop.admin.model.catalog;

import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */

    //provides wrapping to the main product entity
    @Valid
    private com.salesmanager.core.model.catalog.product.Product product;

    @Valid
    private List<ProductDescription> descriptions = new ArrayList<ProductDescription>();

    @Valid
    private ProductAvailability availability = null;

    @Valid
    private ProductPrice price = null;

    private MultipartFile image = null;

    private ProductImage productImage = null;

    @NotEmpty
    private String productPrice = "0";

    private String dateAvailable;

    private ProductDescription description = null;

    public String getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public com.salesmanager.core.model.catalog.product.Product getProduct() {
        return product;
    }

    public void setProduct(com.salesmanager.core.model.catalog.product.Product product) {
        this.product = product;
    }

    public List<ProductDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<ProductDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public ProductAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(ProductAvailability availability) {
        this.availability = availability;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public ProductDescription getDescription() {
        return description;
    }

    public void setDescription(ProductDescription description) {
        this.description = description;
    }

}
