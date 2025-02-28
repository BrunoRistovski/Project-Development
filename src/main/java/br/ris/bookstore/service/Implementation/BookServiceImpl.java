package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.Author;
import br.ris.bookstore.model.Book;

import br.ris.bookstore.model.BookStoreInventory;
import br.ris.bookstore.model.enumerations.Genre;
import br.ris.bookstore.repository.AuthorRepository;
import br.ris.bookstore.repository.BookRepository;
import br.ris.bookstore.repository.BookStoreInventoryRepository;
import br.ris.bookstore.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final BookStoreInventoryRepository bookStoreInventoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookStoreInventoryRepository bookStoreInventoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookStoreInventoryRepository = bookStoreInventoryRepository;
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
    @Transactional
    public Book deleteBook(Long id) {
        Book book = findBookById(id);
//        if (book.getAuthors() != null) {
//            for (Author author : book.getAuthors()) {
//                author.getBooks().remove(book); // Updates the owning side (Author)
//            }
//        }
//        if(book.getBookStoreInventoryList() !=null)
//            book.getBookStoreInventoryList().remove(book);

        bookRepository.delete(book);
        return book;
    }

    @Override
    public List<BookStoreInventory> addBookToShoppingCart(Long bookIds) {

        List<BookStoreInventory> searched = new ArrayList<>();
        List<BookStoreInventory> allBookStoreInventory = bookStoreInventoryRepository.findAll();

        Book book = findBookById(bookIds);

        for(BookStoreInventory bookStoreInventory : allBookStoreInventory){
            if (bookStoreInventory.getBook().equals(book))
                searched.add(bookStoreInventory);
        }
        return searched;
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
