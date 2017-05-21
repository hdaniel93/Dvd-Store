package haughton.dvdstore.model;

/**
 * Created by danie on 19/05/2017.
 */
public class AddCommentPojo {
    private String productId;
    private String text;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
