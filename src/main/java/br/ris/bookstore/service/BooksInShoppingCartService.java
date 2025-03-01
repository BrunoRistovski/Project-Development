package br.ris.bookstore.service;

import br.ris.bookstore.model.BooksInShoppingCart;
import br.ris.bookstore.model.ShoppingCart;

import java.util.List;

public interface BooksInShoppingCartService {

    List<BooksInShoppingCart> listAllBooksInShoppingCart();

    BooksInShoppingCart createBooksInShoppingCart(Long bookIds, Long shoppingCartIds, int quantity);

    BooksInShoppingCart deleteBooksInShoppingCart(Long id);

}