package logic.controllers;

import jakarta.validation.Valid;
import logic.DAO.PeopleHibernateDAO;
import logic.models.Person;
import logic.utill.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hibernate")
public class PeopleControllerHibernate {
    private final PeopleHibernateDAO peopleHibernateDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleControllerHibernate(PeopleHibernateDAO peopleHibernateDAO, PersonValidator personValidator) {
        this.peopleHibernateDAO = peopleHibernateDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", peopleHibernateDAO.getPeople());
        return "/people/show_people";
    }

    @GetMapping("/person/{id}")
    public String getPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleHibernateDAO.getPerson(id));
        return "/people/show_person";
    }

    @GetMapping("/new_person")
    public String showFormNewPerson(@ModelAttribute("person") Person person) {
        return "/people/new_person";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/people/new_person";
        peopleHibernateDAO.save(person);
        return "redirect:/hibernate";
    }

    @GetMapping("/edit/{id}")
    public String showFormEditPerson(@PathVariable("id") int id,
                                     Model model) {
        model.addAttribute("person", peopleHibernateDAO.getPerson(id));
        return "/people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/people/edit_person";
        peopleHibernateDAO.update(id, person);
        return "redirect:/hibernate";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleHibernateDAO.delete(id);
        return "redirect:/hibernate";
    }
}
