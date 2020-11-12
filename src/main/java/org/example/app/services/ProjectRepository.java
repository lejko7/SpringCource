package org.example.app.services;

import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;
import org.example.web.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectRepository<T> {
    List<Book> retrieveAll();

    void store(Book book);

    boolean removeItem(EBookAttribute bookAttribute, String value);

    List<Book> filterBooks(Long id, String author, String title, Integer size, boolean union);

    void saveFile(MultipartFile file) throws IOException;

    List<FileDto> getAllFiles();
}
