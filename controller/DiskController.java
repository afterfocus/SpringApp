package com.afterfocus.springapp.controller;

import com.afterfocus.springapp.model.Disk;
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

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DiskController {

    private final DiskRepository diskRepository;
    private final PersonRepository personRepository;

    /**
     * !!!
     * !!! Uncomment the insertData method to fill tables
     * !!!
     */
    @Autowired
    public DiskController(DiskRepository diskRepository, PersonRepository personRepository) {
        this.diskRepository = diskRepository;
        this.personRepository = personRepository;
        //InitializeData.insertData(diskRepository, personRepository);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String infoPage() {
        return "index";
    }

    @RequestMapping(value = "diskList", method = RequestMethod.GET)
    public String diskListPage(@RequestParam(required = false) String errorString, @RequestParam(defaultValue = "-1") int person, Model model) {

        if (person == -1) model.addAttribute("disks", diskRepository.findAll());
        else model.addAttribute("disks", diskRepository.findAllByPerson_PersonID(person));

        model.addAttribute("errorString", errorString);
        return "diskListView";
    }

    @RequestMapping(value = "diskList", method = RequestMethod.POST)
    public String searchDisk(@RequestParam String search, Model model) {
        String errorString = "";
        search = search.toLowerCase();

        List<Disk> searchResult = new ArrayList<>();
        for (Disk d : diskRepository.findAll())
            if (d.getRusTitle().toLowerCase().contains(search) || d.getEngTitle().toLowerCase().contains(search) || Integer.toString(d.getReleaseYear()).contains(search))
                searchResult.add(d);

        model.addAttribute("disks", searchResult);
        model.addAttribute("errorString", errorString);
        return "diskListView";
    }

    @RequestMapping(value = "addDisk", method = RequestMethod.GET)
    public String addDiskPage(@RequestParam(required = false) String errorString, Model model) {
        model.addAttribute("errorString", errorString);
        return "addDiskView";
    }

    @RequestMapping(value = "addDisk", method = RequestMethod.POST)
    public String addDisk(@RequestParam String rusTitle, @RequestParam String engTitle, @RequestParam(defaultValue = "-1") int releaseYear, RedirectAttributes redirectAttributes) {
        if (releaseYear == -1 || releaseYear < 1900 || releaseYear > Year.now().getValue() + 2) {
            redirectAttributes.addAttribute("errorString", "Введено некорректное значение года выпуска фильма.");
            return "redirect:/addDisk";
        } else if (rusTitle.equals("") && engTitle.equals("")) {
            redirectAttributes.addAttribute("errorString", "Название фильма должно быть заполнено хотя-бы на одном языке.");
            return "redirect:/addDisk";
        } else {
            diskRepository.save(new Disk(rusTitle, engTitle, releaseYear));
            return "redirect:/diskList";
        }
    }

    @RequestMapping(value = "deleteDisk", method = RequestMethod.GET)
    public String deleteDisk(@RequestParam int code) {
        diskRepository.deleteById(code);
        return "redirect:/diskList";
    }

    @RequestMapping(value = "editDisk", method = RequestMethod.GET)
    public String editDiskPage(@RequestParam int code, @RequestParam(required = false) String errorString, Model model) {
        try {
            Optional<Disk> disk = diskRepository.findById(code);
            model.addAttribute("disk", disk.get());
            model.addAttribute("person", disk.get().getPerson());
        } catch (ArrayIndexOutOfBoundsException e) {
            errorString = "Диск не найден. Вероятно, он был удален.";
        }
        model.addAttribute("errorString", errorString);
        return "editDiskView";
    }

    @RequestMapping(value = "editDisk", method = RequestMethod.POST)
    public String editDisk(@RequestParam int code,
                           @RequestParam String rusTitle,
                           @RequestParam String engTitle,
                           @RequestParam(defaultValue = "-1") int releaseYear,
                           @RequestParam(defaultValue = "-1") int pcode,
                           @RequestParam(required = false) String surname,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String phonenumber,
                           RedirectAttributes redirectAttributes) {

        if (releaseYear == -1 || releaseYear < 1900 || releaseYear > Year.now().getValue() + 2) {
            redirectAttributes.addAttribute("errorString", "Введено некорректное значение года выпуска фильма.");
            redirectAttributes.addAttribute("code", code);
            return "redirect:/editDisk";
        } else if (rusTitle.equals("") && engTitle.equals("")) {
            redirectAttributes.addAttribute("errorString", "Название фильма должно быть заполнено хотя-бы на одном языке.");
            redirectAttributes.addAttribute("code", code);
            return "redirect:/editDisk";
        } else {
            if (pcode != -1) {
                if (surname.equals("") || name.equals("")) {
                    redirectAttributes.addAttribute("errorString", "Поля 'Фамилия' и 'Имя' должны быть заполнены.");
                    redirectAttributes.addAttribute("code", code);

                } else {
                    Person person = new Person(pcode, surname, name, phonenumber, -1);
                    personRepository.save(person);
                    diskRepository.save(new Disk(code, rusTitle, engTitle, releaseYear, person));
                }
            }
            else diskRepository.save(new Disk(code, rusTitle, engTitle, releaseYear, null));
            return "redirect:/diskList";
        }
    }

    @RequestMapping(value = "issueDisk", method = RequestMethod.GET)
    public String issueDisk(@RequestParam int code, @RequestParam int pcode) {
        diskRepository.issueDisk(pcode, code);
        return "redirect:/diskList";
    }

    @RequestMapping(value = "returnDisk", method = RequestMethod.GET)
    public String returnDisk(@RequestParam int code) {
        diskRepository.returnDisk(code);
        return "redirect:/diskList";
    }
}
