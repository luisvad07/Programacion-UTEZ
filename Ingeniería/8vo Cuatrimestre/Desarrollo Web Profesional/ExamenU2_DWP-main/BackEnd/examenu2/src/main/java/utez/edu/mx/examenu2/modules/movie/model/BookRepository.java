package com.example.demo.modules.movie.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface BookRepository extends JpaRepository<Book, Long>{
        Page<Book> findAll(Pageable pageable);
        Page<Book> findAllByOrderByAtPublishDesc(Pageable pageable);
        Page<Book> findAllByOrderByAuthorDesc(Pageable pageable);
        Page<Book> findAllByCoverIsNotNull(Pageable pageable);
}
