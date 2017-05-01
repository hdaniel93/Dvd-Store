package haughton.dvdstore.model;

import haughton.dvdstore.web.FlashMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 18/04/2017.
 */
//used for maintaining a "cart" on site using session
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class Cart {
    //these are parallel arrays
    private List<CartLine> lines = new ArrayList<>();

    private double totalPrice;

    public Cart(){

    }
    public FlashMessage add(Product product, int quantity){

        if(quantity>product.getQuantityInStock()){
            //if user tries to add more than we have in stock
            return new FlashMessage("Sorry we dont have enough in stock", FlashMessage.Status.FAILURE);
        }
        for(int i = 0;i<lines.size();i++){
            if(lines.get(i).getProduct().getId() == product.getId()){
                //the product is already in the cart,increase the quantity of the product in cart
                return increaseQuantity(product,quantity,i);
            }
        }
    CartLine cartLine = new CartLine(product,quantity);
        lines.add(cartLine);
        return new FlashMessage("Item added to cart", FlashMessage.Status.SUCCESS);
    }

    public FlashMessage increaseQuantity(Product product,int quantity,int pos){
        if(quantity +lines.get(pos).getQuantity()>product.getQuantityInStock()){
            //if user tries to add more than we have in stock
            return new FlashMessage("Sorry we dont have enough in stock", FlashMessage.Status.FAILURE);
        }

        lines.get(pos).setQuantity(quantity +lines.get(pos).getQuantity());//add to the quantity already in cart
        return new FlashMessage("Item added to cart", FlashMessage.Status.SUCCESS);
    }

    public List<CartLine> getLines() {
        return lines;
    }

    public void setLines(List<CartLine> lines) {
        this.lines = lines;
    }

    public double getTotalPrice() {
        totalPrice = 0;
        for(int i = 0;i<lines.size();i++){
            totalPrice = totalPrice + lines.get(i).getTotalPrice();
        }
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void removeAllItems(){
        lines.clear();
    }
}
