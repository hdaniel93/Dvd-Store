package haughton.dvdstore.web;

import haughton.dvdstore.model.Cart;
import haughton.dvdstore.model.Comment;
import haughton.dvdstore.model.Product;
import haughton.dvdstore.service.CommentService;
import haughton.dvdstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by danie on 24/03/2017.
 */
@Controller
public class ProductController {
@Autowired
private ProductService productService;
@Autowired
private CommentService commentService;

    // Home page
    @RequestMapping("/")
    public String showIndex(Model model) {
        //get 3 products for slideshow on index
        long long1 = 1;
        long long2 = 2;
        long long4 = 4;
       model.addAttribute("product1",productService.findById(long1));
        model.addAttribute("product2",productService.findById(long2));
        model.addAttribute("product3",productService.findById(long4));
        return "index";
    }

    @RequestMapping(value="/searchProducts", method= RequestMethod.GET)
    public String searchResults(@RequestParam("searchterm") String searchTerm,Model model) {
        List<Product> products = productService.searchByTitle(searchTerm);
        model.addAttribute("searchResults",products);
        return "searchresults";
    }

    @RequestMapping("/product/{productId}")
    public String category(@PathVariable Long productId, Model model) {
        //  Get the product given by Id
        Product product = productService.findById(productId);
        model.addAttribute("product", product);

        //get the comments for this product
        List<Comment> comments = commentService.allCommentsByProductId(productId);
        model.addAttribute("comments",comments);
        return "productdetails";
    }
}
