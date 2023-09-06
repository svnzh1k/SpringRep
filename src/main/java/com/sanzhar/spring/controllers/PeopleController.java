package com.sanzhar.spring.controllers;



import com.sanzhar.spring.dao.PersonDAO;
import com.sanzhar.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO dao;

    @Autowired
    public PeopleController(PersonDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("people",dao.getPeople());
        return "/people/index";
    }

    @GetMapping("/add")
    public String addPage(Model model, Person person){
        model.addAttribute("person", person);
        return "/people/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Person person){
        dao.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ("id") int id){
        dao.remove(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable ("id") int id, Model model){
        model.addAttribute("boolean", dao.hasBooks(id));
        model.addAttribute("personsbook", dao.getPersonsBooks(id));
        model.addAttribute("person", dao.getPerson(id));
        return "/people/person";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable ("id") int id, Model model){
        model.addAttribute("person", dao.getPerson(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable ("id") int id, @ModelAttribute Person person){
        dao.update(id,person);
        return "redirect:/people";
    }

}
