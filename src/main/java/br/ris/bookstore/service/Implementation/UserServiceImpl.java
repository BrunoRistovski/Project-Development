package br.ris.bookstore.service.Implementation;

import br.ris.bookstore.model.ShoppingCart;
import br.ris.bookstore.model.User;
import br.ris.bookstore.repository.ShoppingCartRepository;
import br.ris.bookstore.repository.UserRepository;
import br.ris.bookstore.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    public UserServiceImpl(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public User createUser(String firstName, String lastName, LocalDate birthday, String address, String email, String password) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCartRepository.save(shoppingCart);
        User user = new User(firstName,lastName,birthday,address,email,password,shoppingCart);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, String firstName, String lastName, LocalDate birthday, String address, String email, String password) {
        User user = findUserById(id);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
        return user;
    }
}
