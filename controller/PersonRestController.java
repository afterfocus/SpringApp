package com.afterfocus.springapp.controller;

import com.afterfocus.springapp.model.Disk;
import com.afterfocus.springapp.model.Person;
import com.afterfocus.springapp.repository.DiskRepository;
import com.afterfocus.springapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/person"})
public class PersonRestController {

    private final DiskRepository diskRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PersonRestController(DiskRepository diskRepository, PersonRepository personRepository) {
        this.diskRepository = diskRepository;
        this.personRepository = personRepository;
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @GetMapping(path = {"/{id}"})
    public Person findOne(@PathVariable("id") int id) {
        return personRepository.findById(id).get();
    }

    @PostMapping(path = {"/update"})
    public void updatePerson(@RequestBody Person person) {
        personRepository.save(person);
    }

    @DeleteMapping(path = {"/{id}"})
    public Person delete(@PathVariable("id") int id) {
        Person person = personRepository.findById(id).get();
        personRepository.delete(person);
        return person;
    }

    @GetMapping
    public List<Person> findAll() {
        Iterable<Person> persons = personRepository.findAll();
        for (Person p : persons)
            p.setIssuedDisks(diskRepository.countDisksByPerson_PersonID(p.getPersonID()));
        return (List<Person>) persons;
    }
}
