package haughton.dvdstore.web;

import haughton.dvdstore.model.AddToCartPojo;
import haughton.dvdstore.model.Cart;
import haughton.dvdstore.model.Product;
import haughton.dvdstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by danie on 18/04/2017.
 */
@Controller
@Scope("session")
public class CartController {
    @Autowired
    private Cart cart;
    @Autowired
    ProductService productService;

    @RequestMapping(value="/cart", method= RequestMethod.GET)
    public String searchResults(Model model) {
    model.addAttribute("cartLines",cart.getLines());
    model.addAttribute("cartTotalPrice",cart.getTotalPrice());
        return "cart";
    }
    @ResponseBody
    @RequestMapping(value="/addToCart", method= RequestMethod.POST)
    public String searchResults(@RequestBody AddToCartPojo addToCartPojo) {
        Product product = productService.findById(Long.parseLong(addToCartPojo.getProductId()));
        if(product == null){
            //if the productid supplied doesnt belong to a product in our database
            return "failure";
        }
        FlashMessage result = cart.add(product,Integer.parseInt(addToCartPojo.getQuantity()));
          return  result.getStatus().toString();
    }


//no longer in use,old method that didnt use ajax and had to reload page
    @RequestMapping(value = "/product/{productId}/addToCart", method = RequestMethod.POST)
    public String addToCart(@PathVariable Long productId,@RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes){
        Product product = productService.findById(productId);
        if(product == null){
            //if the productid supplied doesnt belong to a product in our database
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("An error occured!", FlashMessage.Status.FAILURE));
            return "redirect:/index";
        }
        //try add item to cart
        FlashMessage result = cart.add(product,quantity);
        redirectAttributes.addFlashAttribute("flash",result);
        return "redirect:/product/" + productId;
    }

    @RequestMapping(value = "/cart/remove/{productId}", method = RequestMethod.POST)
    public String removeCartLine(@PathVariable Long productId, RedirectAttributes redirectAttributes){


        FlashMessage result = cart.removeCartLine(productId);
        redirectAttributes.addFlashAttribute("flash",result);
        return "redirect:/cart";
    }
    //following method is for "subtract" button in cart,reduces quantity of product by one
    @RequestMapping(value = "/cart/decrease/{productId}", method = RequestMethod.POST)
    public String decreaseQuantityOfOneCartLine(@PathVariable Long productId, RedirectAttributes redirectAttributes){
        FlashMessage result = cart.decreaseQuantityOfOneCartLine(productId);
        redirectAttributes.addFlashAttribute("flash",result);
        return "redirect:/cart";
    }

    //following method is for "plus" button in cart,increases quantity of product by one
    @RequestMapping(value = "/cart/increase/{productId}", method = RequestMethod.POST)
    public String increaseQuantityOfOneCartLine(@PathVariable Long productId, RedirectAttributes redirectAttributes){
        FlashMessage result = cart.increaseQuantityOfOneCartLine(productId);
        redirectAttributes.addFlashAttribute("flash",result);
        return "redirect:/cart";
    }


}
