package br.ris.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books_in_shopping_cart")
public class BooksInShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_Id",nullable = false)
    private ShoppingCart shoppingCart;

    private int quantity;

    public BooksInShoppingCart(Book book, ShoppingCart shoppingCart, int quantity) {
        this.book = book;
        this.shoppingCart = shoppingCart;
        this.quantity = quantity;
    }
}
