package br.ris.bookstore.service;

import br.ris.bookstore.model.BookStore;
import br.ris.bookstore.model.BookStoreInventory;

import java.util.List;

public interface BookStoresService {

    List<BookStore> listAllBookStores();
    BookStore findBookStoreById(Long id);
    BookStore createBookStore(String name,String address, String city, String email, String phoneNumber, String webSite);
    BookStore updateBookStore(Long id,String name, String address, String city, String email, String phoneNumber, String webSite);
    BookStore deleteBookStore(Long id);

    BookStoreInventory addBookToBookStore(Long bookId, Long bookStoreId, int quantity);
}
