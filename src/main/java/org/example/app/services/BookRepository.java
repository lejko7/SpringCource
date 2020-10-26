package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> books = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return books;
    }

    @Override
    public void store(Book book) {
        if (!book.getAuthor().equals("") || book.getId() != null || book.getSize() != null || !book.getTitle().equals("")) {
            book.setId((long) book.hashCode());
            logger.info("store new book: " + book);
            books.add(book);
        } else {
            logger.info("Failed to store book! All fields are empty.");
        }
    }

    @Override
    public boolean removeItem(EBookAttribute bookAttribute, String value) {
        switch (bookAttribute) {
            case ID:
                return removeById(Long.parseLong(value));
            case AUTHOR:
                return removeByAuthor(value);
            case TITLE:
                return removeByTitle(value);
            case SIZE:
                return removeBySize(Integer.parseInt(value));
            default:
                return false;
        }
    }

    @Override
    public List<Book> filterBooks(Long id, String author, String title, Integer size, boolean union) {
        if (union){
            return books.stream().filter(book -> book.getId().equals(id) &&
                    (book.getAuthor().contains(author)) &&
                    (book.getTitle().contains(title)) &&
                    (book.getSize().equals(size))).collect(Collectors.toList());
        }

        return books.stream()
                .filter(book -> (id == null || book.getId().equals(id)) &&
                        (author.equals("") || book.getAuthor().contains(author)) &&
                        (title.equals("") || book.getTitle().contains(title)) &&
                        (size == null || book.getSize().equals(size)))
                .collect(Collectors.toList());
    }

    private boolean removeBySize(int size) {
        List<Book> booksToRemove = new ArrayList<>();
        int countDeleted = 0;
        for (Book book : books) {
            if (book.getSize().equals(size)) {
                logger.info("remove book completed: " + book);
                booksToRemove.add(book);
                countDeleted++;
            }
        }

        books.removeAll(booksToRemove);

        return countDeleted > 0;
    }

    private boolean removeByTitle(String title) {
        List<Book> booksToRemove = new ArrayList<>();
        int countDeleted = 0;
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                logger.info("remove book completed: " + book);
                booksToRemove.add(book);
                countDeleted++;
            }
        }

        books.removeAll(booksToRemove);

        return countDeleted > 0;
    }

    private boolean removeByAuthor(String author) {
        List<Book> booksToRemove = new ArrayList<>();
        int countDeleted = 0;
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                logger.info("remove book completed: " + book);
                booksToRemove.add(book);
                countDeleted++;
            }
        }

        books.removeAll(booksToRemove);

        return countDeleted > 0;
    }

    private boolean removeById(long id) {
        List<Book> booksToRemove = new ArrayList<>();
        for (Book book : retrieveAll()) {
            if (book.getId().equals(id)) {
                logger.info("remove book completed: " + book);
                booksToRemove.add(book);
            }
        }

        books.removeAll(booksToRemove);
        return false;
    }
}
