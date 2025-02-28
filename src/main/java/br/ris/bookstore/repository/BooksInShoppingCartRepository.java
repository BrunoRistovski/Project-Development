package br.ris.bookstore.repository;

import br.ris.bookstore.model.BooksInShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksInShoppingCartRepository extends JpaRepository<BooksInShoppingCart,Long> {

}
