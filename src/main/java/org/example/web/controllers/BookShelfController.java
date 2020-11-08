package org.example.web.controllers;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.Enums.EBookAttribute;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Controller
@RequestMapping(value = "/books")
@AllArgsConstructor
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("bookAttribute", EBookAttribute.values());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(Model model,
                             @RequestParam EBookAttribute bookAttribute,
                             @RequestParam String value) {
        if (bookService.removeBookByValue(bookAttribute, value)) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookAttribute", EBookAttribute.values());
            return "book_shelf";
        }
    }

    @PostMapping("/filter")
    public String filterBook(Model model,
                             @RequestParam(required = false) Long id,
                             @RequestParam(required = false) String author,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) boolean union) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.filterByParam(id, author, title, size, union));
        model.addAttribute("bookAttribute", EBookAttribute.values());
        return "book_shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file) throws IOException {
        bookService.saveFile(file);

        return "redirect:/books/shelf";
    }
}
