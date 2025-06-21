package com.example.demo.modules.movie.service;

import com.example.demo.modules.movie.model.BookRepository;
import com.example.demo.modules.movie.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository IBookRepository;

    public BookService(BookRepository iBookRepository) {
        IBookRepository = iBookRepository;
    }

    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        return this.IBookRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = { SQLException.class, Exception.class})
    public Book save(Book book){
        return this.IBookRepository.saveAndFlush(book);
    }

    @Transactional(rollbackFor = { SQLException.class, Exception.class})
    public Book update(Book book){
        return this.IBookRepository.saveAndFlush(book);
    }


    @Transactional(rollbackFor = { SQLException.class, Exception.class})
    public Book changeStatus(Book book){
        Book payload = this.IBookRepository.findById(book.getId()).get();
        payload.setStatus(!payload.isStatus());
        return this.IBookRepository.saveAndFlush(payload);
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id){
        return this.IBookRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public Page<Book> findAllByOrderAtPublishDesc(Pageable pageable){
        return this.IBookRepository.findAllByOrderByAtPublishDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> findAllByOrderAuthorDesc(Pageable pageable){
        return this.IBookRepository.findAllByOrderByAuthorDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> findAllByCoverIsNotEmpty(Pageable pageable){
        return this.IBookRepository.findAllByCoverIsNotNull(pageable);
    }

}
