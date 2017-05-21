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

    public FlashMessage removeCartLine(long productId){
        for(int i = 0;i<lines.size();i++){
            if(lines.get(i).getProduct().getId() == productId){
            lines.remove(i);
                return new FlashMessage(lines.get(i).getProduct().getTitle() + " removed", FlashMessage.Status.SUCCESS);
            }
        }
        //if we get here,the productId isnt in the incart the
        return new FlashMessage("An error occured", FlashMessage.Status.FAILURE);
    }

    public FlashMessage increaseQuantity(Product product,int quantity,int pos){
        if(quantity +lines.get(pos).getQuantity()>product.getQuantityInStock()){
            //if user tries to add more than we have in stock
            return new FlashMessage("Sorry we dont have enough in stock", FlashMessage.Status.FAILURE);
        }

        lines.get(pos).setQuantity(quantity +lines.get(pos).getQuantity());//add to the quantity already in cart
        return new FlashMessage("Item added to cart", FlashMessage.Status.SUCCESS);
    }
    //decreases quantity of given product by one
    public FlashMessage  decreaseQuantityOfOneCartLine(long productId){
        for(int i = 0;i<lines.size();i++){
            if(lines.get(i).getProduct().getId() == productId){
                if(lines.get(i).getQuantity()==1){
                    //if the item only has a quantity of one in the cart,remove it
                    //save title to display in flash message
                    String title = lines.get(i).getProduct().getTitle();
                    lines.remove(i);
                    return new FlashMessage(title +" removed", FlashMessage.Status.SUCCESS);
                }
                //decrease quantity by one
                lines.get(i).setQuantity(lines.get(i).getQuantity() -1);
                return new FlashMessage("Cart Updated", FlashMessage.Status.SUCCESS);

            }
        }
        return new FlashMessage("An error occured", FlashMessage.Status.FAILURE);
        //if we get here product supplied isnt in cart

    }

    public FlashMessage  increaseQuantityOfOneCartLine(long productId){
        for(int i = 0;i<lines.size();i++){
            if(lines.get(i).getProduct().getId() == productId){
                if(1 +lines.get(i).getQuantity()> lines.get(i).getProduct().getQuantityInStock()){
                    //if user tries to add more than we have in stock
                    return new FlashMessage("Sorry we dont have enough in stock", FlashMessage.Status.FAILURE);
                }
                //increase quantity by one
                lines.get(i).setQuantity(lines.get(i).getQuantity() +1);
                return new FlashMessage("Cart Updated", FlashMessage.Status.SUCCESS);
            }
        }
        return new FlashMessage("An error occured", FlashMessage.Status.FAILURE);
        //if we get here product supplied isnt in cart

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
