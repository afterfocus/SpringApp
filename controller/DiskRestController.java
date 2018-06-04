package com.afterfocus.springapp.controller;

import com.afterfocus.springapp.model.Disk;
import com.afterfocus.springapp.repository.DiskRepository;
import com.afterfocus.springapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/disk"})
public class DiskRestController {

    private final DiskRepository diskRepository;
    private final PersonRepository personRepository;

    @Autowired
    public DiskRestController(DiskRepository diskRepository, PersonRepository personRepository) {
        this.diskRepository = diskRepository;
        this.personRepository = personRepository;
    }

    @PostMapping
    public Disk create(@RequestBody Disk disk){
        return diskRepository.save(disk);
    }

    @GetMapping(path = {"/{id}"})
    public Disk findOne(@PathVariable("id") int id){
        return diskRepository.findById(id).get();
    }

    @PostMapping(path = {"/update"})
    public void updateDisk(@RequestBody Disk disk) {
        diskRepository.save(disk);
    }

    @DeleteMapping(path ={"/{id}"})
    public Disk delete(@PathVariable("id") int id) {
        Disk disk = diskRepository.findById(id).get();
        diskRepository.delete(disk);
        return disk;
    }

    @PostMapping(path = {"/return"})
    public void returnDisk(@RequestBody Disk disk) {
        diskRepository.returnDisk(disk.getDiskID());
    }

    @PostMapping(path = {"/issue/{pcode}"})
    public void issueDisk(@RequestBody Disk disk, @PathVariable("pcode") int pcode) {
        diskRepository.issueDisk(pcode, disk.getDiskID());
    }

    @GetMapping
    public List<Disk> findAll(){
        return (List<Disk>) diskRepository.findAll();
    }

    @GetMapping(path = {"/findAllById/{id}"})
    public List<Disk> findAllById(@PathVariable("id") int id){
        return (List<Disk>) diskRepository.findAllByPerson_PersonID(id);
    }
}
