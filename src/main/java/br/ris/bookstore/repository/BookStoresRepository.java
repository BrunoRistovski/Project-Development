package br.ris.bookstore.repository;

import br.ris.bookstore.model.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoresRepository extends JpaRepository<BookStore,Long> {
}
