package ru.chembaev.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chembaev.spring.dao.BookDAO;
import ru.chembaev.spring.dao.PersonDAO;
import ru.chembaev.spring.models.Book;
import ru.chembaev.spring.models.Person;
import ru.chembaev.spring.util.PersonValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, PersonValidator booksValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.personValidator = booksValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> ownerInfo = bookDAO.getOwnerInfo(id);

        if (ownerInfo.isPresent()) {
            model.addAttribute("ownerInfo", ownerInfo.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/do")
    public String updateAffilation(@ModelAttribute("person") Person person,
                         @PathVariable("id") int id) {
        bookDAO.updateAffilation(id, person.getId());
        return "redirect:/books" + id;
    }

    @PatchMapping("/{id}/undo")
    public String deleteAffilation(@PathVariable("id") int id) {

        bookDAO.deleteAffilation(id);
        return "redirect:/books" + id;
    }
}
