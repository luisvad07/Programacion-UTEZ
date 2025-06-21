package com.example.demo.modules.movie.controller;

import com.example.demo.modules.movie.model.Book;
import com.example.demo.modules.movie.model.dto.BookDto;
import com.example.demo.modules.movie.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/books")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public Page<Book> getAll(Pageable pageable) {
        return this.bookService.findAll(pageable);
    }

    @PostMapping("/")
    public Book save(@Validated({BookDto.save.class}) @RequestBody BookDto movie) {
        Book paylaod = new Book(
                movie.getName(),
                movie.getAuthor(),
                movie.getAtPublish(),
                movie.getCover()
        );
        return this.bookService.save(paylaod);
    }


    @PutMapping("/update/")
    public Book update(@Validated({BookDto.update.class}) @RequestBody BookDto movie) {
        Book paylaod = new Book(
                movie.getId(),
                movie.getName(),
                movie.getAuthor(),
                movie.getAtPublish(),
                movie.getCover()
        );
        return this.bookService.update(paylaod);
    }
    @PatchMapping("/change-status/")
    public Book changeStatus(@Validated({BookDto.changeStatus.class}) @RequestBody BookDto Movie) {
        Book paylaod = new Book();
        paylaod.setId(Movie.getId());
        return this.bookService.changeStatus(paylaod);
    }

    @GetMapping("/order-by-publish-date/")
    public Page<Book> findAllByOrderAtPublishDesc(Pageable pageable) {
        return this.bookService.findAllByOrderAtPublishDesc(pageable);
    }

    @GetMapping("/order-by-author/")
    public Page<Book> findAllByOrderAuthorDesc(Pageable pageable) {
        return this.bookService.findAllByOrderAuthorDesc(pageable);
    }

    @GetMapping("/has-cover/")
    public Page<Book> findAllByCoverIsNotEmpty(Pageable pageable) {
        return this.bookService.findAllByCoverIsNotEmpty(pageable);
    }



}
