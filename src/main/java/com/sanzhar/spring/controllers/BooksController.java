package com.sanzhar.spring.controllers;

import com.sanzhar.spring.dao.BookDAO;
import com.sanzhar.spring.dao.PersonDAO;
import com.sanzhar.spring.models.Book;
import com.sanzhar.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO dao;
    private final PersonDAO daop;

    @Autowired
    public BooksController(BookDAO dao, PersonDAO daop) {
        this.dao = dao;
        this.daop = daop;
    }

    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("books",dao.getBooks());
        return "/books/index";
    }

    @GetMapping("/add")
    public String addPage(Model model, Book book){
        model.addAttribute("book", book);
        return "/books/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Book book){
        dao.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        dao.remove(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable ("id") int id, Model model, @ModelAttribute ("person") Person person){
        model.addAttribute("book", dao.getBook(id));
        if (dao.getOwner(id) == null){
            model.addAttribute("people", daop.getPeople());
        }
        else{
            model.addAttribute("owner", dao.getOwner(id));
        }
        return "/books/book";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable ("id") int id, Model model){
        model.addAttribute("book", dao.getBook(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable ("id") int id, @ModelAttribute Book book){
        dao.update(id,book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable ("id") int id){
        dao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/assign")
    public String assign(@PathVariable ("id") int id, @ModelAttribute Person person){
        dao.assign(person.getId(), id);
        return "redirect:/books/" + id;
    }
}
