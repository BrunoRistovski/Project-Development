package br.ris.bookstore.web;

import br.ris.bookstore.service.BooksInShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shoppingCart")
public class BooksInShoppingCartController {

    private final BooksInShoppingCartService booksInShoppingCartService;

    public BooksInShoppingCartController(BooksInShoppingCartService booksInShoppingCartService) {
        this.booksInShoppingCartService = booksInShoppingCartService;
    }

    @GetMapping()
    public String listAllBooksInShoppingCart(Model model){
        model.addAttribute("booksInShoppingCart",booksInShoppingCartService.listAllBooksInShoppingCart());
        return "booksInShoppingCartTemplates/listAllBooksInShoppingCart";
    }

    @PostMapping("/deleteBookInShoppingCart/{id}")
    public String deleteBookInShoppingCart(@PathVariable Long id){
        booksInShoppingCartService.deleteBooksInShoppingCart(id);
        return "redirect:/shoppingCart";
    }



}
