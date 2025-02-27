package br.ris.bookstore.web;

import br.ris.bookstore.service.BookService;
import br.ris.bookstore.service.BookStoreInventoryService;
import br.ris.bookstore.service.BookStoresService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookstores")
public class BookStoreController {

    //TODO da napravam vo details da se pregleduvaat knigite vo eden bookstore i add book prazna mi e

    private final BookStoresService bookStoresService;
    private final BookService bookService;
    private final BookStoreInventoryService bookStoreInventoryService;

    public BookStoreController(BookStoresService bookStoresService, BookService bookService, BookStoreInventoryService bookStoreInventoryService) {
        this.bookStoresService = bookStoresService;
        this.bookService = bookService;
        this.bookStoreInventoryService = bookStoreInventoryService;
    }

    @GetMapping()
    public String viewAllBookStores(Model model){

        model.addAttribute("bookStores",bookStoresService.listAllBookStores());
        return "bookStoresTemplates/viewAllBookStores";
    }

    @GetMapping("/add")
    public String createBookStore(Model model){

        return "bookStoresTemplates/createBookStore";
    }

    @PostMapping("/add")
    public String createBookStore(@RequestParam String name,
                                  @RequestParam String address,
                                  @RequestParam String city,
                                  @RequestParam String email,
                                  @RequestParam String phoneNumber,
                                  @RequestParam String webSite){

        bookStoresService.createBookStore(name, address, city, email, phoneNumber, webSite);
        return "redirect:/bookstores";
    }

    @GetMapping("edit/{id}")
    public String updateBookStore(@PathVariable Long id,Model model){

        model.addAttribute("bookStore",bookStoresService.findBookStoreById(id));
        return "bookStoresTemplates/updateBookStore";
    }

    @PostMapping("/edit")
    public String updateBookStore(@RequestParam Long id,
                                  @RequestParam String name,
                                  @RequestParam String address,
                                  @RequestParam String city,
                                  @RequestParam String email,
                                  @RequestParam String phoneNumber,
                                  @RequestParam String webSite){

        bookStoresService.updateBookStore(id, name, address, city, email, phoneNumber, webSite);
        return "redirect:/bookstores";
    }


    @GetMapping("/details/{id}")
    public String getDetailsForBookStore(@PathVariable Long id,Model model){
        model.addAttribute("bookStore",bookStoresService.findBookStoreById(id));
        model.addAttribute("bookStoreBooks",bookStoreInventoryService.findAllBookStoreInventoryByBookStore(id));
        return "bookStoresTemplates/getDetailsForBookStore";
    }

    @GetMapping("/addBook/{id}")
    public String addBookToBookStore(@PathVariable Long id,Model model){
        model.addAttribute("bookStore",bookStoresService.findBookStoreById(id));
        model.addAttribute("books",bookService.listAllBooks());
        return "bookStoresTemplates/addBookToBookStore";
    }

    @PostMapping("/addBook")
    public String addBookToBookStore(@RequestParam Long bookId,
                                     @RequestParam Long bookStoreId,
                                     @RequestParam int quantity){
        bookStoresService.addBookToBookStore(bookId,bookStoreId,quantity);
        return "redirect:/bookstores";
    }

    @PostMapping("/delete/{id}")
    public String deleteBookStore(@PathVariable Long id){

        bookStoresService.deleteBookStore(id);
        return "redirect:/bookstores";
    }

    public List<Long> getListOfIds(List<Long> ids){
        if (ids == null || ids.isEmpty())
            ids = List.of();
        return ids;
    }

}
