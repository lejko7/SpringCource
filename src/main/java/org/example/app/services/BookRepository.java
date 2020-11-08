package org.example.app.services;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Book> retrieveAll() {
        return jdbcTemplate.query("select * from books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId((long) rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
    }

    @Override
    public void store(Book book) {
        if (!book.getAuthor().equals("") || book.getSize() != null || !book.getTitle().equals("")) {
            logger.info("store new book: " + book);
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("author", book.getAuthor());
            parameterSource.addValue("title", book.getTitle());
            parameterSource.addValue("size", book.getSize());
            jdbcTemplate.update("insert into books(author, title, size) values(:author, :title, :size)", parameterSource);
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

        List<Book> books = retrieveAll();

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
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", size);
        jdbcTemplate.update("delete from books where size = :size", parameterSource);

        return true;
    }

    private boolean removeByTitle(String title) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", title);
        jdbcTemplate.update("delete from books where title = :title", parameterSource);

        return true;
    }

    private boolean removeByAuthor(String author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", author);
        jdbcTemplate.update("delete from books where author = :author", parameterSource);

        return true;
    }

    private boolean removeById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        jdbcTemplate.update("delete from books where id = :id", parameterSource);

        return true;
    }
}
