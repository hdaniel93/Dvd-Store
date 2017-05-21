package haughton.dvdstore.model;

/**
 * Created by danie on 18/05/2017.
 */
//pojo for sending via ajax
public class AddToCartPojo {
    private String productId;
    private String quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
