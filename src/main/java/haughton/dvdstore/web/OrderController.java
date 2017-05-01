package haughton.dvdstore.web;

import haughton.dvdstore.model.*;
import haughton.dvdstore.service.OrderService;
import haughton.dvdstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by danie on 28/04/2017.
 */
@Controller
public class OrderController {
    @Autowired
    private Cart cart;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/completeOrder")
    public String completOrder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if((auth instanceof AnonymousAuthenticationToken)){
            //user isnt  logged in already,cant complete order,redirect back to cart
            return "redirect:/cart";
        }
        if(cart.getLines().size()==0){
            //nothing in the cart,cant complete order,redirect to cart,
            return "redirect:/cart";
        }
        return "completeOrder";
    }
    @Transactional
    @RequestMapping(value = "/completeOrder/complete",method = RequestMethod.POST)
    public String completOrderComplete(@RequestParam("address") String address, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if((auth instanceof AnonymousAuthenticationToken)){
            //user isnt  logged in already,cant complete order,redirect back to cart
            return "redirect:/cart";
        }
        if(cart.getLines().size()==0){
            //nothing in the cart,cant complete order,redirect to cart,
            return "redirect:/cart";
        }
        if(address.length()<10){
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Address entered was too short", FlashMessage.Status.FAILURE));
            return "redirect:/completeOrder";
        }
        //logged in user
        User user = (User) auth.getPrincipal();
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        ArrayList<CartLine> cartLines = (ArrayList<CartLine>) cart.getLines();
        Orders order = new Orders();
        order.setAddress(address);
        order.setUser(user);

        order.setPrice(cart.getTotalPrice());
        //get current date time
        Date date = new Date();
        order.setDate(date);
        for(int i = 0;i<cartLines.size();i++){
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(cartLines.get(i).getProduct());
            orderLine.setQuantity(cartLines.get(i).getQuantity());
            orderLine.setOrder(order);
            orderLines.add(orderLine);
        }
        order.setOrderLines(orderLines);

        orderService.save(order);
        for(int i=0;i<cartLines.size();i++){
            productService.reduceQuantityInStock(cartLines.get(i).getProduct().getId(),cartLines.get(i).getQuantity());
        }
        //empty cart
        cart.removeAllItems();

        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Thanks for the order!", FlashMessage.Status.SUCCESS));

        return "redirect:/";
    }

    @RequestMapping("/myAccount")
    public String myAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if((auth instanceof AnonymousAuthenticationToken)){
            //user isnt  logged in already,cant complete order,redirect back to index
            return "redirect:/";
        }
        //logged in user
        User user = (User) auth.getPrincipal();
        List<Orders> ordersList = orderService.findByUserIdOrderByDateDesc(user.getId());
        model.addAttribute("ordersList",ordersList);
        return "myAccount";
    }

    @RequestMapping("/viewOrder/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if((auth instanceof AnonymousAuthenticationToken)){
            //user isnt  logged in already,redirect
            return "redirect:/";
        }
        Orders order =orderService.findById(orderId);
        //logged in user
        User user = (User) auth.getPrincipal();

        if(order.getUser().getId()!= user.getId()){
            //order trying to be viewed does not belong to the logged in user,not allowed,also handles if order doesnt exist
            return "redirect:/";
        }
        //we get here,user is able to
        model.addAttribute("order",order);
        return "viewOrder";
    }
}




