package br.ris.bookstore.service;

import br.ris.bookstore.model.BookStoreInventory;

import java.util.List;

public interface BookStoreInventoryService {

    List<BookStoreInventory> listAllBookStoreInventory();
    List<BookStoreInventory> findAllBookStoreInventoryByBookStore(Long bookStoreId);
}
