package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.Book;
import br.ris.bookstore.model.BookStore;
import br.ris.bookstore.model.BookStoreInventory;
import br.ris.bookstore.repository.BookRepository;
import br.ris.bookstore.repository.BookStoreInventoryRepository;
import br.ris.bookstore.repository.BookStoresRepository;
import br.ris.bookstore.service.BookStoreInventoryService;
import br.ris.bookstore.service.BookStoresService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookStoreInventoryServiceImpl implements BookStoreInventoryService {

    private final BookStoreInventoryRepository bookStoreInventoryRepository;
    private final BookStoresRepository bookStoresRepository;

    private final BookRepository bookRepository;

    public BookStoreInventoryServiceImpl(BookStoreInventoryRepository bookStoreInventoryRepository, BookStoresRepository bookStoresRepository, BookRepository bookRepository) {
        this.bookStoreInventoryRepository = bookStoreInventoryRepository;
        this.bookStoresRepository = bookStoresRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookStoreInventory> listAllBookStoreInventory() {
        return bookStoreInventoryRepository.findAll();
    }

    @Override
    public List<BookStoreInventory> findAllBookStoreInventoryByBookStore(Long bookStoreId) {

        BookStore bookStore = bookStoresRepository.findById(bookStoreId).orElseThrow(RuntimeException::new);
        List<BookStoreInventory> bookStoreInventoryList = listAllBookStoreInventory();

        List<BookStoreInventory> searchedList = new ArrayList<>();
        for(BookStoreInventory bookStoreInventory : bookStoreInventoryList){
            if (bookStoreInventory.getBookStore().equals(bookStore))
                searchedList.add(bookStoreInventory);
        }
        return searchedList;
    }

    @Override
    public BookStoreInventory findByBookAndBookStore(Long bookIds, Long bookStoreIds) {

        BookStoreInventory searched = new BookStoreInventory();
        List<BookStoreInventory> allBookStoreInventory = bookStoreInventoryRepository.findAll();

        Book book = bookRepository.findById(bookIds).orElseThrow(RuntimeException::new);
        BookStore bookStore = bookStoresRepository.findById(bookStoreIds).orElseThrow(RuntimeException::new);

        for(BookStoreInventory bookStoreInventory : allBookStoreInventory)
            if(bookStoreInventory.getBook().equals(book) && bookStoreInventory.getBookStore().equals(bookStore))
                searched = bookStoreInventory;

        return searched;
    }
}
