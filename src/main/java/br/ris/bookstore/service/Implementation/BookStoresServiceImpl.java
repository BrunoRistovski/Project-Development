package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.Book;
import br.ris.bookstore.model.BookStore;
import br.ris.bookstore.model.BookStoreInventory;
import br.ris.bookstore.repository.BookRepository;
import br.ris.bookstore.repository.BookStoreInventoryRepository;
import br.ris.bookstore.repository.BookStoresRepository;
import br.ris.bookstore.service.BookStoresService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStoresServiceImpl implements BookStoresService {

    private final BookStoresRepository bookStoresRepository;
    private final BookStoreInventoryRepository bookStoreInventoryRepository;
    private final BookRepository bookRepository;

    public BookStoresServiceImpl(BookStoresRepository bookStoresRepository, BookStoreInventoryRepository bookStoreInventoryRepository, BookRepository bookRepository) {
        this.bookStoresRepository = bookStoresRepository;
        this.bookStoreInventoryRepository = bookStoreInventoryRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public List<BookStore> listAllBookStores() {
        return bookStoresRepository.findAll();
    }

    @Override
    public BookStore findBookStoreById(Long id) {
        return bookStoresRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public BookStore createBookStore(String name,String address, String city, String email, String phoneNumber, String webSite) {

        BookStore bookStore = new BookStore(name,address,city,email,phoneNumber,webSite);
        return bookStoresRepository.save(bookStore);
    }

    @Override
    public BookStore updateBookStore(Long id, String name,String address, String city, String email, String phoneNumber, String webSite) {

        BookStore bookStore = findBookStoreById(id);

        bookStore.setName(name);
        bookStore.setAddress(address);
        bookStore.setCity(city);
        bookStore.setEmail(email);
        bookStore.setPhoneNumber(phoneNumber);
        bookStore.setWebSite(webSite);


        return bookStoresRepository.save(bookStore);
    }

    @Override
    public BookStore deleteBookStore(Long id) {
        BookStore bookStore = findBookStoreById(id);
        bookStoresRepository.delete(bookStore);
        return bookStore;
    }

    @Override
    public BookStoreInventory addBookToBookStore(Long bookId, Long bookStoreId, int quantity) {

        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        BookStore bookStore = findBookStoreById(bookStoreId);

        BookStoreInventory bookStoreInventory = new BookStoreInventory(book,bookStore,quantity);
        return bookStoreInventoryRepository.save(bookStoreInventory);
    }

//    public List<Book> getBooks(List<Long> bookIds){
//        List<Book> books;
//        if(bookIds == null || bookIds.isEmpty())
//            books = new ArrayList<>();
//        else
//            books = bookRepository.findAllById(bookIds);
//        return books;
//    }
}
