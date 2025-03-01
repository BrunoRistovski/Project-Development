package br.ris.bookstore.service;

import br.ris.bookstore.model.BooksInOrders;
import br.ris.bookstore.model.BooksInShoppingCart;
import br.ris.bookstore.model.User;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

public interface BooksInOrdersService {

    List<BooksInOrders> findByOrderId(Long orderId);

    void createBooksInOrders(LocalDateTime orderedTime, List<BooksInShoppingCart> booksInShoppingCarts, User user,HttpSession httpSession);

}
