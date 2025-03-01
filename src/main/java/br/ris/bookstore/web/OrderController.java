package br.ris.bookstore.web;

import br.ris.bookstore.model.BooksInOrders;
import br.ris.bookstore.model.BooksInShoppingCart;
import br.ris.bookstore.model.User;
import br.ris.bookstore.service.BooksInOrdersService;
import br.ris.bookstore.service.BooksInShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final BooksInOrdersService booksInOrdersService;
    private final BooksInShoppingCartService booksInShoppingCartService;

    public OrderController(BooksInOrdersService booksInOrdersService, BooksInShoppingCartService booksInShoppingCartService) {
        this.booksInOrdersService = booksInOrdersService;
        this.booksInShoppingCartService = booksInShoppingCartService;
    }

    @GetMapping()
    public String viewAllOrders(Model model, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if (user.getOrders() != null)
            model.addAttribute("orders",user.getOrders());
        return "ordersTemplates/viewAllOrders";
    }

    @PostMapping("/create")
    public String createOrder(HttpSession httpSession){

        LocalDateTime orderedTime = LocalDateTime.now();
        List<BooksInShoppingCart> booksInShoppingCarts = booksInShoppingCartService.listAllBooksInShoppingCart();
        User user = (User) httpSession.getAttribute("user");
        booksInOrdersService.createBooksInOrders(orderedTime,booksInShoppingCarts,user,httpSession);
        return "redirect:/books";
    }

    @GetMapping("/details/{id}")
    public String orderDetails(@PathVariable Long id,Model model){
        List<BooksInOrders> booksInOrders = booksInOrdersService.findByOrderId(id);
        model.addAttribute("orderDetail", booksInOrders);
        return "ordersTemplates/orderDetails";
    }
}
