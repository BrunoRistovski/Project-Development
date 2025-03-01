package br.ris.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books_in_orders")
public class BooksInOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Book book;

    @ManyToOne()
    private Order order;

    private int quantity;


    public BooksInOrders(Book book, Order order, int quantity) {
        this.book = book;
        this.order = order;
        this.quantity = quantity;
    }
}
