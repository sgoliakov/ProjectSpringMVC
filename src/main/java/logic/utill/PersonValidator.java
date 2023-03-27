package logic.utill;

import logic.DAO.PeopleJdbcTemplateDAO;
import logic.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleJdbcTemplateDAO peopleJdbcTemplateDAO;

    @Autowired
    public PersonValidator(PeopleJdbcTemplateDAO peopleJdbcTemplateDAO) {
        this.peopleJdbcTemplateDAO = peopleJdbcTemplateDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleJdbcTemplateDAO.getPerson(person.getMail()).isPresent()) {
            errors.rejectValue("mail", "", "this mail  is already taken");
        }
    }
}
