package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.*;
import br.ris.bookstore.repository.*;
import br.ris.bookstore.service.BooksInOrdersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // Ensure all operations are within a transaction
public class BooksInOrdersServiceImpl implements BooksInOrdersService {

    private final BooksInOrdersRepository booksInOrdersRepository;
    private final OrderRepository orderRepository;
    private final BooksInShoppingCartRepository booksInShoppingCartRepository;
    private final UserRepository userRepository;

    public BooksInOrdersServiceImpl(BooksInOrdersRepository booksInOrdersRepository,
                                    OrderRepository orderRepository,
                                    BooksInShoppingCartRepository booksInShoppingCartRepository,
                                    UserRepository userRepository) {
        this.booksInOrdersRepository = booksInOrdersRepository;
        this.orderRepository = orderRepository;
        this.booksInShoppingCartRepository = booksInShoppingCartRepository;
        this.userRepository = userRepository;

    }

    @Override
    public List<BooksInOrders> findByOrderId(Long orderId) {
        List<BooksInOrders> all = booksInOrdersRepository.findAll();
        List<BooksInOrders> searched = new ArrayList<>();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        for (BooksInOrders booksInOrders : all) {
            if (booksInOrders.getOrder().equals(order))
                searched.add(booksInOrders);
        }
        return searched;
    }

    @Transactional
    @Override
    public void createBooksInOrders(LocalDateTime orderedTime, List<BooksInShoppingCart> booksInShoppingCarts, User user, HttpSession httpSession) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Fetch the managed User entity from the database
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("User not found in database"));

        // Create and save the new Order with the managed User
        Order order = new Order(managedUser, orderedTime);
        orderRepository.save(order);

        // Update the managed User's orders list
        List<Order> orders = managedUser.getOrders();
        if (orders == null) {
            orders = new ArrayList<>();
            managedUser.setOrders(orders);
        }
        orders.add(order);
        userRepository.save(managedUser);

        for (BooksInShoppingCart booksInShoppingCart : booksInShoppingCarts) {
            if (managedUser.getShoppingCart().equals(booksInShoppingCart.getShoppingCart())) {
                BooksInOrders booksInOrders = new BooksInOrders(booksInShoppingCart.getBook(), order, booksInShoppingCart.getQuantity());
                booksInOrdersRepository.save(booksInOrders);
                booksInShoppingCartRepository.delete(booksInShoppingCart);
            }
        }
        httpSession.setAttribute("user",managedUser);
    }
}