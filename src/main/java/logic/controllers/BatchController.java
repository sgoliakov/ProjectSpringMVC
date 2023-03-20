package logic.controllers;

import logic.DAO.PeopleJdbcTemplateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test_batch_update")
public class BatchController {
    private final PeopleJdbcTemplateDAO peopleJdbcTemplateDAO;

    @Autowired
    public BatchController(PeopleJdbcTemplateDAO peopleJdbcTemplateDAO) {
        this.peopleJdbcTemplateDAO = peopleJdbcTemplateDAO;
    }

    @GetMapping
    public String index() {
        return "/batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        peopleJdbcTemplateDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatch() {
        peopleJdbcTemplateDAO.withBatchUpdate();
        return "redirect:/people";
    }
}
