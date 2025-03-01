package br.ris.bookstore.repository;

import br.ris.bookstore.model.BooksInOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksInOrdersRepository extends JpaRepository<BooksInOrders,Long> {
}
