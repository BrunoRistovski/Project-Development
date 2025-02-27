package br.ris.bookstore.repository;

import br.ris.bookstore.model.BookStoreInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoreInventoryRepository extends JpaRepository<BookStoreInventory,Long> {
}
