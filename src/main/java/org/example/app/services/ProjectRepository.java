package org.example.app.services;

import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;

import java.util.List;

public interface ProjectRepository<T> {
    List<Book> retrieveAll();

    void store(Book book);

    boolean removeItem(EBookAttribute bookAttribute, String value);

    List<Book> filterBooks(Long id, String author, String title, Integer size);
}
