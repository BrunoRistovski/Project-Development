package br.ris.bookstore.service;

import br.ris.bookstore.model.Book;
import br.ris.bookstore.model.enumerations.Genre;

import java.time.LocalDateTime;
import java.util.List;

public interface BookService {

    List<Book> listAllBooks();
    Book findBookById(Long id);
    Book createBook(String isbn, String title, Genre genre, String description,String imageUrl, LocalDateTime publicationDate, Double price,List<Long> authorsIds);
    Book updateBook(Long id,String isbn, String title, Genre genre, String description,String imageUrl, LocalDateTime publicationDate, Double price,List<Long> authorsIds);
    Book deleteBook(Long id);
}
