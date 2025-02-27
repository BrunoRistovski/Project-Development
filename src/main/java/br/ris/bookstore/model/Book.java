package br.ris.bookstore.model;

import br.ris.bookstore.model.enumerations.Genre;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(length = 5000)
    private String description;
    private String imageUrl;

    private LocalDateTime publicationDate;

    private Double price;

    @ManyToMany(mappedBy = "books",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Nullable
    private List<Author> authors;

    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Nullable
    private List<BookStoreInventory> bookStoreInventoryList;

    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Nullable
    private List<BooksInShoppingCart> booksInShoppingCart;


    public Book(String isbn, String title, Genre genre, String description, String imageUrl, LocalDateTime publicationDate, Double price, @Nullable List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
        this.price = price;
        this.authors = authors;
    }
}
