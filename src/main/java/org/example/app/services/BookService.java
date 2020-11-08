package org.example.app.services;

import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    public List<Book> getAllBooks();

    public void saveBook(Book book);

    public boolean removeBookByValue(EBookAttribute bookAttribute, String value);

    public List<Book> filterByParam(Long id, String author, String title, Integer size, boolean union);

    void saveFile(MultipartFile file);
}
