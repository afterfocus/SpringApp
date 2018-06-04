package com.afterfocus.springapp.repository;

import com.afterfocus.springapp.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer>{
}
