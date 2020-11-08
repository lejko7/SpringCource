package org.example.app.services;

import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookServiceImpl(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookByValue(EBookAttribute bookAttribute, String value) {
        return bookRepo.removeItem(bookAttribute, value);
    }

    public List<Book> filterByParam(Long id, String author, String title, Integer size, boolean union) {
        return bookRepo.filterBooks(id, author, title, size, union);
    }

    @Override
    public void saveFile(MultipartFile file) {
        bookRepo.saveFile(file);
    }
}
