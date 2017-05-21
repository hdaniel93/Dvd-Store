package haughton.dvdstore.web;

import haughton.dvdstore.model.*;
import haughton.dvdstore.service.CommentService;
import haughton.dvdstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by danie on 04/04/2017.
 */
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ProductService productService;



    @RequestMapping(value="/addComment", method= RequestMethod.POST)
    public @ResponseBody NewCommentPojo searchResults(@RequestBody AddCommentPojo addCommentPojo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if(user==null){
            //user isnt logged in
            return new NewCommentPojo("error","","");
        }
        Product product = productService.findById(Long.valueOf(addCommentPojo.getProductId()));
        if(product == null){
            //if the productid supplied doesnt belong to a product in our database
            return new NewCommentPojo("error","","");
        }
        if(addCommentPojo.getText().length() < 10){
            //comment was too short
            return new NewCommentPojo("tooshort","","");
        }

        commentService.addComment(user.getId(), Long.valueOf(addCommentPojo.getProductId()),addCommentPojo.getText());
        //if we get here comment was saved
        return new NewCommentPojo("success",addCommentPojo.getText(),user.getUsername());
    }


    @RequestMapping(value = "/product/{productId}/addComment", method = RequestMethod.POST)
    public String addComment(@PathVariable Long productId, @RequestParam("text") String text, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) auth.getPrincipal();
    if(user==null){
        //user isnt logged in
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("An error occured!", FlashMessage.Status.FAILURE));
    }
    Product product = productService.findById(productId);
    if(product == null){
        //if the productid supplied doesnt belong to a product in our database
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("An error occured!", FlashMessage.Status.FAILURE));
        return "redirect:/product/" + productId;
    }
    if(text.length() < 10){
        //comment was too short
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("You need to have no less than 10 characters in your comment!", FlashMessage.Status.FAILURE));
        return "redirect:/product/" + productId;
    }


        commentService.addComment(user.getId(),productId,text);
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Comment added!", FlashMessage.Status.SUCCESS));
        return "redirect:/product/" + productId;

    }

}
