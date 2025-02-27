package br.ris.bookstore.service;

import br.ris.bookstore.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> listAllUsers();
    User findUserById(Long id);
    User createUser(String firstName, String lastName, LocalDate birthday,String address,String email,String password);
    User updateUser(Long id,String firstName, String lastName, LocalDate birthday,String address,String email,String password);
    User deleteUser(Long id);
}
