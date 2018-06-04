package com.afterfocus.springapp.controller;

import com.afterfocus.springapp.model.Person;
import com.afterfocus.springapp.repository.DiskRepository;
import com.afterfocus.springapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonController {

    private final DiskRepository diskRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(DiskRepository diskRepository, PersonRepository personRepository) {
        this.diskRepository = diskRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "addPerson", method = RequestMethod.GET)
    public String addPersonPage(@RequestParam(required = false) String errorString, Model model) {
        model.addAttribute("errorString", errorString);
        return "addPersonView";
    }

    @RequestMapping(value = "addPerson", method = RequestMethod.POST)
    public String addPerson(@RequestParam String surname, @RequestParam String name, @RequestParam String phonenumber, RedirectAttributes redirectAttributes) {
        if (surname.equals("") || name.equals("")) {
            redirectAttributes.addAttribute("errorString", "Поля 'Фамилия' и 'Имя' должны быть заполнены.");
            return "redirect:/personList";
        } else {
            personRepository.save(new Person(surname, name, phonenumber));
            return "redirect:/personList";
        }
    }

    @RequestMapping(value = "deletePerson", method = RequestMethod.GET)
    public String deletePerson(@RequestParam int code) {
        personRepository.deleteById(code);
        return "redirect:/personList";
    }

    @RequestMapping(value = "editPerson", method = RequestMethod.GET)
    public String editPersonPage(@RequestParam int code, @RequestParam(required = false) String errorString, Model model) {
        try {
            model.addAttribute("person", personRepository.findById(code).get());
        } catch (ArrayIndexOutOfBoundsException e) {
            errorString = "Держатель не найден. Вероятно, он был удален.";
        }
        model.addAttribute("errorString", errorString);
        return "editPersonView";
    }

    @RequestMapping(value = "editPerson", method = RequestMethod.POST)
    public String editPerson(@RequestParam int code, @RequestParam String surname, @RequestParam String name, @RequestParam String phonenumber, RedirectAttributes redirectAttributes) {
        if (surname.equals("") && name.equals("")) {
            redirectAttributes.addAttribute("errorString", "Поля 'Фамилия' и 'Имя' должны быть заполнены.");
            redirectAttributes.addAttribute("code", code);
            return "redirect:/editPerson";
        } else {
            personRepository.save(new Person(code, surname, name, phonenumber, 0));
            return "redirect:/personList";
        }
    }

    @RequestMapping(value = "personList", method = RequestMethod.GET)
    public String personListPage(@RequestParam(required = false) String errorString, Model model) {
        Iterable<Person> persons = personRepository.findAll();
        for(Person p : persons)
            p.setIssuedDisks(diskRepository.countDisksByPerson_PersonID(p.getPersonID()));

        model.addAttribute("persons", persons);
        model.addAttribute("errorString", errorString);
        return "personListView";
    }

    @RequestMapping(value = "personList", method = RequestMethod.POST)
    public String searchPerson(@RequestParam String search, Model model) {
        String errorString = "";
        search = search.toLowerCase();

        List<Person> searchResult = new ArrayList<>();
        for (Person p : personRepository.findAll())
            if (p.getSurname().toLowerCase().contains(search) || p.getName().toLowerCase().contains(search) || p.getPhonenumber().toLowerCase().contains(search)) {
                p.setIssuedDisks(diskRepository.countDisksByPerson_PersonID(p.getPersonID()));
                searchResult.add(p);
            }
        model.addAttribute("persons", searchResult);
        model.addAttribute("errorString", errorString);
        return "personListView";
    }

    @RequestMapping(value = "personsToIssue", method = RequestMethod.GET)
    public String personsToIssuePage(@RequestParam int code, Model model) {
        model.addAttribute("disk", diskRepository.findById(code).get());
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("code", code);
        return "issueDiskView";
    }
}
