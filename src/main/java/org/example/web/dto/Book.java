package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Book {

    private Long id;
    @NotNull
    private String author;
    @NotNull
    private String title;
    @Digits(integer = 5, fraction = 0)
    private Integer size;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
