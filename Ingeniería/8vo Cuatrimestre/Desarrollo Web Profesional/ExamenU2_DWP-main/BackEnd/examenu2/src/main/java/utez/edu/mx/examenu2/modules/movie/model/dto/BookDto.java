package com.example.demo.modules.movie.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @NotNull(groups = {update.class, changeStatus.class})
    private Long id;

    @NotNull(groups = {save.class, update.class})
    private String name;

    @NotNull(groups = {save.class, update.class})
    private String author;

    @NotNull(groups = {save.class, update.class})
    private LocalDate atPublish;

    private String cover;

    private Boolean status = true;


    public interface save { };

    public interface update { };
    public interface changeStatus { };
}
