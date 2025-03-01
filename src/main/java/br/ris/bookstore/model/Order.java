package br.ris.bookstore.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User user;

    private LocalDateTime orderedTime;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Nullable
    private List<BooksInOrders> booksInOrders;

    public Order(User user, LocalDateTime orderedTime) {
        this.user = user;
        this.orderedTime = orderedTime;
    }
}
