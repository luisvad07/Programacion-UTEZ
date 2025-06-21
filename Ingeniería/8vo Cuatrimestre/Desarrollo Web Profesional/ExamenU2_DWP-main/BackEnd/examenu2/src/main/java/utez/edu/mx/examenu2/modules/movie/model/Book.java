package com.example.demo.modules.movie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;


    @Column(columnDefinition = "TIMESTAMP DEFAULT now()", nullable = true)
    private LocalDate atPublish;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String cover;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String author;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private boolean status = true;

    public Book(String name, String author, LocalDate atPublish, String cover) {
        this.name = name;
        this.author = author;
        this.atPublish = atPublish;
        this.cover = cover;
    }

    public Book(Long id, String name, String author, LocalDate atPublish, String cover) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.atPublish = atPublish;
        this.cover = cover;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", atPublish=" + atPublish +
                ", cover='" + cover + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                '}';
    }
}
