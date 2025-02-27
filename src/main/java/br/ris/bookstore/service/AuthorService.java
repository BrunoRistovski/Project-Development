package br.ris.bookstore.service;

import br.ris.bookstore.model.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {
    List<Author> listAllAuthor();
    Author findAuthorById(Long id);
    Author createAuthor(String firstName, String lastName, String embg, LocalDate birthday, String biography, String webSite,List<Long> booksIds);
    Author updateAuthor(Long id,String firstName, String lastName, String embg, LocalDate birthday, String biography, String webSite,List<Long> booksIds);
    Author deleteAuthor(Long id);
}
