package logic.controllers;

import jakarta.validation.Valid;
import logic.DAO.PeopleJdbcTemplateDAO;
import logic.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleJdbcTemplateDAO peopleJdbcTemplateDAO;

    @Autowired
    public PeopleController(PeopleJdbcTemplateDAO peopleJdbcTemplateDAO) {
        this.peopleJdbcTemplateDAO = peopleJdbcTemplateDAO;
    }

    @GetMapping()
    public String showPeople(Model model) {
        model.addAttribute("people", peopleJdbcTemplateDAO.getPeople());
        return "/peopleControllerView/show_people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleJdbcTemplateDAO.getPersonById(id));
        return "/peopleControllerView/show_person";
    }

    @GetMapping("/new_person")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/peopleControllerView/new_person";
    }

    @PostMapping()
    public String addNewPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/peopleControllerView/new_person";
        }
        peopleJdbcTemplateDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showEditor(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleJdbcTemplateDAO.getPersonById(id));
        return "/peopleControllerView/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "//peopleControllerView/edit_person";
        peopleJdbcTemplateDAO.update(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleJdbcTemplateDAO.delete(id);
        return "redirect:/people";
    }
}
