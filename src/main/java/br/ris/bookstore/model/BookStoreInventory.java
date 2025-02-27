package br.ris.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bookStoreInventory")
public class BookStoreInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId",nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "bookStoreId",nullable = false)
    private BookStore bookStore;

    @Column(nullable = false)
    private int quantity;

    public BookStoreInventory(Book book, BookStore bookStore, int quantity) {
        this.book = book;
        this.bookStore = bookStore;
        this.quantity = quantity;
    }
}
