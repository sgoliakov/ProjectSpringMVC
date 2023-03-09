package logic.controllers;

import jakarta.validation.Valid;
import logic.DAO.PeopleDAO;
import logic.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("people", peopleDAO.getPeople());
        return "/peopleControllerView/show_people";
    }

    @GetMapping("/{id}")
    public String showByID(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.getPersonById(id));
        return "/peopleControllerView/show_person";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute(new Person());
        System.out.println("tyt");
        return "/peopleControllerView/new";
    }

    @PostMapping()
    public String addNewPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/peopleControllerView/new";
        }
        peopleDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleDAO.getPersonById(id));
        return "/peopleControllerView/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "//peopleControllerView/edit_person";
        }
        peopleDAO.update(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleDAO.delete(id);

        return "redirect:/people";
    }
}
