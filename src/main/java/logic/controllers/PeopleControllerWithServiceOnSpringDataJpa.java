package logic.controllers;

import jakarta.validation.Valid;
import logic.models.Person;
import logic.services.ItemsService;
import logic.services.PeopleService;
import logic.utill.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service")
public class PeopleControllerWithServiceOnSpringDataJpa {
    private final PeopleService peopleService;
    private final ItemsService itemsService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleControllerWithServiceOnSpringDataJpa(PeopleService peopleService, ItemsService itemsService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.itemsService = itemsService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        itemsService.findItemsByName("sony");
        itemsService.findItemsByOwner(peopleService.findAll().get(0));
        peopleService.test();
        return "/people/show_people";
    }

    @GetMapping("/person/{id}")
    public String getPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
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
        peopleService.save(person);
        return "redirect:/service";
    }

    @GetMapping("/edit/{id}")
    public String showFormEditPerson(@PathVariable("id") int id,
                                     Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "/people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/people/edit_person";
        peopleService.update(id, person);
        return "redirect:/service";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/service";
    }
}
