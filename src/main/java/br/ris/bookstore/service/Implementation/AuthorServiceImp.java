package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.Author;
import br.ris.bookstore.model.Book;
import br.ris.bookstore.repository.AuthorRepository;
import br.ris.bookstore.repository.BookRepository;
import br.ris.bookstore.service.AuthorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImp(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> listAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Author createAuthor(String firstName, String lastName, String embg, LocalDate birthday, String biography, String webSite, List<Long> booksIds) {
        List<Book> books;
        if(booksIds == null || booksIds.isEmpty())
            books = new ArrayList<>();
        else
            books = bookRepository.findAllById(booksIds);
        Author author = new Author(firstName,lastName,embg,birthday,biography,webSite,books);
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, String firstName, String lastName, String embg, LocalDate birthday, String biography, String webSite, List<Long> booksIds) {
        Author author = findAuthorById(id);
        List<Book> books;
        if(booksIds == null || booksIds.isEmpty())
            books = new ArrayList<>();
        else
            books = bookRepository.findAllById(booksIds);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setEmbg(embg);
        author.setBirthday(birthday);
        author.setBiography(biography);
        author.setWebSite(webSite);
        author.setBooks(books);
        return authorRepository.save(author);
    }

    @Override
    public Author deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        if (author.getBooks() != null) {
            for (Book book : author.getBooks()) {
                book.getAuthors().remove(author); // Updates the owning side (Author)
            }
        }
        authorRepository.delete(author);
        return author;
    }
}
