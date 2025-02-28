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
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "EMBG",length = 13)
    private String embg;

    private LocalDate birthday;

    @Column(length = 5000)
    private String biography;

    @Column(length = 500)
    private String webSite;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Nullable
    private List<Book> books;

    public Author(String firstName, String lastName, String embg, LocalDate birthday, String biography, String webSite, @Nullable List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.embg = embg;
        this.birthday = birthday;
        this.biography = biography;
        this.webSite = webSite;
        this.books = books;
    }
}
