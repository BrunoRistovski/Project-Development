package br.ris.bookstore.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shopping_Cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "shoppingCart",cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "shoppingCart",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Nullable
    private List<BooksInShoppingCart> booksInShoppingCart;
}
