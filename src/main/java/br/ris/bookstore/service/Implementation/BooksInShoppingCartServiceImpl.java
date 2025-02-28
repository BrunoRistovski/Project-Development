package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.Book;
import br.ris.bookstore.model.BooksInShoppingCart;
import br.ris.bookstore.model.ShoppingCart;
import br.ris.bookstore.repository.BookRepository;
import br.ris.bookstore.repository.BooksInShoppingCartRepository;
import br.ris.bookstore.repository.ShoppingCartRepository;
import br.ris.bookstore.service.BooksInShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksInShoppingCartServiceImpl implements BooksInShoppingCartService {

    private final BooksInShoppingCartRepository booksInShoppingCartRepository;
    private final BookRepository bookRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    public BooksInShoppingCartServiceImpl(BooksInShoppingCartRepository booksInShoppingCartRepository, BookRepository bookRepository, ShoppingCartRepository shoppingCartRepository) {
        this.booksInShoppingCartRepository = booksInShoppingCartRepository;
        this.bookRepository = bookRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<BooksInShoppingCart> listAllBooksInShoppingCart() {
        return booksInShoppingCartRepository.findAll();
    }

    @Override
    public BooksInShoppingCart createBooksInShoppingCart(Long bookIds, Long shoppingCartIds, int quantity) {

        Book book = bookRepository.findById(bookIds).orElseThrow(RuntimeException::new);
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartIds).orElseThrow(RuntimeException::new);

        BooksInShoppingCart booksInShoppingCart = new BooksInShoppingCart(book,shoppingCart,quantity);
        return booksInShoppingCartRepository.save(booksInShoppingCart);
    }

    @Override
    public BooksInShoppingCart deleteBooksInShoppingCart(Long id) {
        BooksInShoppingCart booksInShoppingCart = booksInShoppingCartRepository.findById(id).orElseThrow(RuntimeException::new);
        booksInShoppingCartRepository.delete(booksInShoppingCart);
        return booksInShoppingCart;
    }
}
