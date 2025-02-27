package br.ris.bookstore.web;

import br.ris.bookstore.model.User;
import br.ris.bookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reg")
    private String createUser(){
        return "userTemplates/createUser";
    }

    @PostMapping("/reg")
    private String createUser(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam LocalDate birthday,
                              @RequestParam String address,
                              @RequestParam String email,
                              @RequestParam String password){
        userService.createUser(firstName, lastName, birthday, address, email, password);
        return "redirect:/users/logIn";
    }

    @GetMapping("/logIn")
    private String logInUser(){
        return "userTemplates/logInUser";
    }

    @PostMapping("/logIn")
    private String logInUser(@RequestParam String email,
                             @RequestParam String password,
                             HttpSession session) {
        List<User> users = userService.listAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                return "redirect:/books";
            }
        }
        return "redirect:/users/logIn";
    }

    @GetMapping("/logOff")
    private String logOff(HttpSession session) {
        session.invalidate();
        return "redirect:/users/logIn";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,Model model){

        model.addAttribute("user",userService.findUserById(id));
        return "userTemplates/editUser";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam Long id,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam LocalDate birthday,
                           @RequestParam String address,
                           @RequestParam (required = false)String email,
                           @RequestParam (required = false)String password) {

        User user = userService.findUserById(id);
        if (email == null || email.isEmpty())
            email = user.getEmail();
        if (password == null || email.isEmpty())
            password = user.getPassword();

        userService.updateUser(id, firstName, lastName, birthday, address, email, password);
        return "redirect:/users/details/" + id;
    }

    @GetMapping("/details/{id}")
    public String getDetailsForUser(@PathVariable Long id,Model model){
        model.addAttribute("user",userService.findUserById(id));
        return "userTemplates/getDetailsForUser";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/users/reg";
    }
}
