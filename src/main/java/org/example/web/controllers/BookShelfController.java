package org.example.web.controllers;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.example.CustomException.EmptyResourceException;
import org.example.app.Enums.EBookAttribute;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.io.IOException;

@Controller
@RequestMapping(value = "/books")
@AllArgsConstructor
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @GetMapping("/shelf")
    public String books(Model model,
                        @RequestParam(required = false) Boolean saveError,
                        @RequestParam(required = false) Boolean removeError,
                        @RequestParam(required = false) Boolean filterError,
                        @RequestParam(required = false) Boolean saveFileError) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("bookAttribute", EBookAttribute.values());
        model.addAttribute("filesList", bookService.getAllFiles());
        model.addAttribute("saveError", saveError);
        model.addAttribute("removeError", removeError);
        model.addAttribute("filterError", filterError);
        model.addAttribute("saveFileError", saveFileError);
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Model model, @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/books/shelf?saveError=true";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(Model model,
                             @RequestParam EBookAttribute bookAttribute,
                             @RequestParam String value) {
        if (bookService.removeBookByValue(bookAttribute, value)) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookAttribute", EBookAttribute.values());
            model.addAttribute("filesList", bookService.getAllFiles());
            return "book_shelf";
        } else {
            return "redirect:/books/shelf?removeError=true";
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
        model.addAttribute("filesList", bookService.getAllFiles());
        return "book_shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file) throws IOException {

        if (file == null || file.isEmpty() || file.getName().equals("") || file.getBytes().length == 0){
            return "redirect:/books/shelf?saveFileError=true";
        }

        bookService.saveFile(file);

        return "redirect:/books/shelf";
    }
}
