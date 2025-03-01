package br.ris.bookstore.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String address;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String password;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private ShoppingCart shoppingCart;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Nullable
    private List<Order> orders;

    public User(String firstName, String lastName, LocalDate birthday, String address, String email, String password, ShoppingCart shoppingCart) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.password = password;
        this.shoppingCart = shoppingCart;
    }
}
