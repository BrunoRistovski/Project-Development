package br.ris.bookstore.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bookstores")
public class BookStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String city;

    private String email;

    public String phoneNumber;

    public String webSite;

    @OneToMany(mappedBy = "bookStore",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Nullable
    List<BookStoreInventory> bookStoreInventoryList;

    public BookStore(String name, String address, String city, String email, String phoneNumber, String webSite) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.webSite = webSite;
    }
}
