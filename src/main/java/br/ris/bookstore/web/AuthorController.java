package br.ris.bookstore.web;

import br.ris.bookstore.service.AuthorService;
import br.ris.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String viewAllAuthors(Model model){
        model.addAttribute("authors",authorService.listAllAuthor());
        return "authorTemplates/viewAllAuthors";
    }

    @GetMapping("/add")
    public String addAuthor (Model model){
        model.addAttribute("books",bookService.listAllBooks());
        return "authorTemplates/addAuthor";
    }

    @PostMapping("/add")
    public String addAuthor(@RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String embg,
                            @RequestParam LocalDate birthday,
                            @RequestParam String biography,
                            @RequestParam String webSite,
                            @RequestParam (required = false)List<Long> booksIds){

        booksIds = getListOfIds(booksIds);
        authorService.createAuthor(firstName,lastName,embg,birthday,biography,webSite,booksIds);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id, Model model){
        model.addAttribute("author",authorService.findAuthorById(id));
        model.addAttribute("books",bookService.listAllBooks());
        return "authorTemplates/updateAuthor";
    }
    @PostMapping("/edit")
    public String updateAuthor(@RequestParam Long id,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String embg,
                               @RequestParam LocalDate birthday,
                               @RequestParam String biography,
                               @RequestParam String webSite,
                               @RequestParam (required = false)List<Long> booksIds){

        booksIds = getListOfIds(booksIds);
        authorService.updateAuthor(id, firstName, lastName, embg, birthday, biography, webSite, booksIds);
        return "redirect:/authors";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

    public List<Long> getListOfIds(List<Long> ids){
        if (ids == null || ids.isEmpty())
            ids = List.of();
        return ids;
    }

}

