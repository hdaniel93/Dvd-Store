package haughton.dvdstore.web;

import ch.qos.logback.core.rolling.helper.RollingCalendar;
import haughton.dvdstore.model.Product;
import haughton.dvdstore.model.Role;
import haughton.dvdstore.model.User;
import haughton.dvdstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by danie on 01/05/2017.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/register")
    public String register() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)){
            //user is logged in already,dont show register,redirect to index
            return "redirect:/";
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@RequestParam("username") String username,@RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        if(username.length()<7){
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Username must be atleast 7 characters!", FlashMessage.Status.FAILURE));
            return "redirect:/register";
        }
        if(password.length()<7){
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Password must be atleast 7 characters!", FlashMessage.Status.FAILURE));
            return "redirect:/register";
        }
        User user = new User();
        user.setEnabled(true);
        user.setPassword(password);
        user.setUsername(username);
        Role role = new Role();
          long roleId = 1;
role.setId(roleId);
role.setName("ROLE_USER");
user.setRole(role);

        userService.save(user);
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Your account creation was successful,try logging in!", FlashMessage.Status.SUCCESS));
        return "redirect:/login";
    }

}
