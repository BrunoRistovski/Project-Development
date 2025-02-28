package br.ris.bookstore.web;

import br.ris.bookstore.model.BookStoreInventory;
import br.ris.bookstore.model.enumerations.Genre;
import br.ris.bookstore.service.AuthorService;
import br.ris.bookstore.service.BookService;
import br.ris.bookstore.service.BookStoreInventoryService;
import br.ris.bookstore.service.BooksInShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    //TODO treba da dodadam bookstores vo html i vo funkciite.

    private final BookService bookService;
    private final AuthorService authorService;

    private final BookStoreInventoryService bookStoreInventoryService;

    private final BooksInShoppingCartService booksInShoppingCartService;

    public BookController(BookService bookService, AuthorService authorService, BookStoreInventoryService bookStoreInventoryService, BooksInShoppingCartService booksInShoppingCartService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookStoreInventoryService = bookStoreInventoryService;
        this.booksInShoppingCartService = booksInShoppingCartService;
    }

    @GetMapping()
    public String viewAllBooks(Model model){
        model.addAttribute("books",bookService.listAllBooks());
        return "bookTemplates/viewAllBooks";
    }

    @GetMapping("/add")
    public String createBook(Model model){
        model.addAttribute("authors",authorService.listAllAuthor());
        return "bookTemplates/createBook";
    }

    @PostMapping("/add")
    public String createBook(@RequestParam String isbn,
                             @RequestParam String title,
                             @RequestParam Genre genre,
                             @RequestParam String description,
                             @RequestParam String imageUrl,
                             @RequestParam LocalDateTime publicationDate,
                             @RequestParam Double price,
                             @RequestParam (required = false) List<Long> authorIds){

        authorIds = getListOfIds(authorIds);
        bookService.createBook(isbn, title, genre, description, imageUrl, publicationDate, price, authorIds);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, Model model){
        model.addAttribute("book", bookService.findBookById(id));
        model.addAttribute("authors",authorService.listAllAuthor());
        return "bookTemplates/updateBook";
    }
    @PostMapping("/edit")
    public String updateBook(@RequestParam Long id,
                             @RequestParam String isbn,
                             @RequestParam String title,
                             @RequestParam Genre genre,
                             @RequestParam String description,
                             @RequestParam String imageUrl,
                             @RequestParam LocalDateTime publicationDate,
                             @RequestParam Double price,
                             @RequestParam (required = false) List<Long> authorIds){

        authorIds = getListOfIds(authorIds);
        bookService.updateBook(id, isbn, title, genre, description, imageUrl, publicationDate, price, authorIds);
        return "redirect:/books";
    }

    @GetMapping("/details/{id}")
    public String detailsForBook(@PathVariable Long id, Model model){
        model.addAttribute("book",bookService.findBookById(id));
        return "bookTemplates/detailsForBook";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/addToShoppingCart/{id}")
    public String addBookToShoppingCart(@PathVariable Long id, Model model){
        model.addAttribute("bookStoreInventories",bookService.addBookToShoppingCart(id));
        return "bookTemplates/addBookToShoppingCart";
    }

    @PostMapping("/addToShoppingCart")
    public String addBookToShoppingCart(@RequestParam Long bookIds,
                                        @RequestParam Long bookStoreIds,
                                        @RequestParam int quantity,
                                        Model model) {
        // Find the inventory to check available quantity
        BookStoreInventory inventory = bookStoreInventoryService.findByBookAndBookStore(bookIds, bookStoreIds);
        if (inventory != null && quantity > inventory.getQuantity()) {
            model.addAttribute("bookStoreInventories", bookService.addBookToShoppingCart(bookIds));
            model.addAttribute("error", "quantity");
            model.addAttribute("maxQuantity", inventory.getQuantity());
            return "bookTemplates/addBookToShoppingCart";
        }

        booksInShoppingCartService.createBooksInShoppingCart(bookIds, bookStoreIds, quantity);
        return "redirect:/shoppingCart";
    }


    public List<Long> getListOfIds(List<Long> ids){
        if (ids == null || ids.isEmpty())
            ids = List.of();
        return ids;
    }
}
