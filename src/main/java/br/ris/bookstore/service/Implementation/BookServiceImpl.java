package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.Author;
import br.ris.bookstore.model.Book;
import br.ris.bookstore.model.BookStore;
import br.ris.bookstore.model.BookStoreInventory;
import br.ris.bookstore.model.enumerations.Genre;
import br.ris.bookstore.repository.AuthorRepository;
import br.ris.bookstore.repository.BookRepository;
import br.ris.bookstore.repository.BookStoreInventoryRepository;
import br.ris.bookstore.repository.BookStoresRepository;
import br.ris.bookstore.service.BookService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Book createBook(String isbn, String title, Genre genre, String description,String imageUrl, LocalDateTime publicationDate, Double price, List<Long> authorsIds) {

        List<Author> authors = getAuthors(authorsIds);

        Book book = new Book(isbn,title,genre,description,imageUrl,publicationDate,price,authors);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, String isbn, String title, Genre genre, String description,String imageUrl, LocalDateTime publicationDate, Double price, List<Long> authorsIds) {

        Book book = findBookById(id);
        List<Author> authors = getAuthors(authorsIds);

        book.setIsbn(isbn);
        book.setTitle(title);
        book.setGenre(genre);
        book.setDescription(description);
        book.setImageUrl(imageUrl);
        book.setPublicationDate(publicationDate);
        book.setPrice(price);
        book.setAuthors(authors);

        return bookRepository.save(book);
    }

    @Override
    public Book deleteBook(Long id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
        return book;
    }

    public List<Author> getAuthors(List<Long> authorsIds){
        List<Author> authors;
        if(authorsIds == null || authorsIds.isEmpty())
            authors = new ArrayList<>();
        else
            authors = authorRepository.findAllById(authorsIds);
        return authors;
    }

//    public List<BookStoreInventory> getBookStoreInventory(List<Long> bookStoreInventoryListIds){
//        List<BookStoreInventory> bookStoreInventoryList;
//        if(bookStoreInventoryListIds == null || bookStoreInventoryListIds.isEmpty())
//            bookStoreInventoryList = new ArrayList<>();
//        else
//            bookStoreInventoryList = bookStoreInventoryRepository.findAllById(bookStoreInventoryListIds);
//        return bookStoreInventoryList;
//    }
}
