package org.example.app.services;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.Enums.EBookAttribute;
import org.example.web.dto.Book;
import org.example.web.dto.FileDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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
                return removeById(value);
            case AUTHOR:
                return removeByAuthor(value);
            case TITLE:
                return removeByTitle(value);
            case SIZE:
                return removeBySize(value);
            default:
                return false;
        }
    }

    @Override
    public List<Book> filterBooks(Long id, String author, String title, Integer size, boolean union) {

        List<Book> books = retrieveAll();

        if (union) {
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

    @Override
    public void saveFile(MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "uploads");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File fileToSave = new File(dir.getAbsolutePath() + File.separator + name);
        for (int i = 1; fileToSave.exists(); i++) {
            int lastDotIndex = name.lastIndexOf('.');
            String nameWithSuffix = name.substring(0, lastDotIndex) + "(" + i + ")" + name.substring(lastDotIndex);
            fileToSave = new File(dir.getAbsolutePath() + File.separator + nameWithSuffix);
        }

        name = fileToSave.getName();

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileToSave));
        stream.write(bytes);
        stream.close();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("fileName", name);
        parameterSource.addValue("filePath", "/uploads/" + name);
        parameterSource.addValue("fileType", file.getContentType());
        jdbcTemplate.update("insert into files(fileName, filePath, fileType) values(:fileName, :filePath, :fileType)", parameterSource);

        logger.info("File saved at: " + fileToSave.getAbsolutePath());
    }

    @Override
    public List<FileDto> getAllFiles() {
        return getFiles();
    }

    private List<FileDto> getFiles() {
        return jdbcTemplate.query("select * from files", (ResultSet rs, int rowNum) -> {
            FileDto file = new FileDto();
            file.setFileName(rs.getString("fileName"));
            file.setFilePath(rs.getString("filePath"));
            file.setFileType(rs.getString("fileType"));
            return file;
        });

    }

    private boolean removeBySize(String valueSize) {
        long size;

        try {
            size = Integer.parseInt(valueSize);
        } catch (Exception e){
            return false;
        }

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

    private boolean removeById(String valueId) {
        long id;

        try {
            id = Long.parseLong(valueId);
        } catch (Exception e){
            return false;
        }

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        jdbcTemplate.update("delete from books where id = :id", parameterSource);

        return true;
    }
}
