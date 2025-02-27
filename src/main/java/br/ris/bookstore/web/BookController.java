package br.ris.bookstore.web;

import br.ris.bookstore.model.enumerations.Genre;
import br.ris.bookstore.service.AuthorService;
import br.ris.bookstore.service.BookService;
import br.ris.bookstore.service.BookStoresService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    //TODO treba da dodadam bookstores vo html i vo funkciite.

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
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

    public List<Long> getListOfIds(List<Long> ids){
        if (ids == null || ids.isEmpty())
            ids = List.of();
        return ids;
    }
}
