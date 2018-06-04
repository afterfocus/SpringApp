package com.afterfocus.springapp;

import com.afterfocus.springapp.model.Disk;
import com.afterfocus.springapp.model.Person;
import com.afterfocus.springapp.repository.DiskRepository;
import com.afterfocus.springapp.repository.PersonRepository;

public class InitializeData {

    public static void insertData(DiskRepository diskRepository, PersonRepository personRepository) {
        diskRepository.deleteAll();
        personRepository.deleteAll();
        personRepository.save(new Person("Концова", "Яна", "+7(917)175-30-90"));
        personRepository.save(new Person("Дунюшкин", "Николай", "227-94-92"));
        personRepository.save(new Person("Воронов", "Артём", "210-50-59"));
        personRepository.save(new Person("Краснобаев", "Алексей", "+7(927)745-24-25"));
        personRepository.save(new Person("Корцев", "Матвей", "+7(937)120-10-77"));
        personRepository.save(new Person("Дуняшин", "Егор", "+7(937)223-55-40"));
        personRepository.save(new Person("Потапов", "Сергей", "+7(927)780-32-13"));
        personRepository.save(new Person("Галанова", "Дарья", "212-52-59"));
        personRepository.save(new Person("Костюшкина", "Мария", "+7(927)735-30-25"));
        personRepository.save(new Person("Троц", "Виктория", "+7(937)122-12-77"));
        diskRepository.save(new Disk("Иван Васильевич меняет профессию", "-", 1973));
        diskRepository.save(new Disk("Интерстеллар", "Interstellar", 2014));
        diskRepository.save(new Disk("Криминальное чтиво", "Pulp Fiction", 1994));
        diskRepository.save(new Disk("Крёстный отец", "The Godfather", 1972));
        diskRepository.save(new Disk("Леон", "Leon", 1994));
        diskRepository.save(new Disk("Список Шиндлера", "Schindler''s List", 1993));
        diskRepository.save(new Disk("Форрест Гамп", "Forrest Gump", 1994));
        diskRepository.save(new Disk("1+1", "Intouchables", 2011));
        diskRepository.save(new Disk("Бойцовский клуб", "Fight Club", 1999));
        diskRepository.save(new Disk("Властелин колец: Возвращение Короля", "The Lord of the Rings: The Return of the King", 2003));
        diskRepository.save(new Disk("Достучаться до небес", "Knockin'' on Heaven''s Door", 1997));
        diskRepository.save(new Disk("Жизнь прекрасна", "La vita e bella", 1997));
        diskRepository.save(new Disk("Зелёная миля", "The Green Mile", 1999));
        diskRepository.save(new Disk("Игры разума", "A Beautiful Mind", 2001));
        diskRepository.save(new Disk("Король Лев", "The Lion King", 1994));
        diskRepository.save(new Disk("Начало", "Inception", 2010));
        diskRepository.save(new Disk("Операция «Ы» и другие приключения Шурика", "-", 1965));
        diskRepository.save(new Disk("Побег из Шоушенка", "The Shawshank Redemption", 1994));
        diskRepository.save(new Disk("Престиж", "The Prestige", 2006));
        diskRepository.save(new Disk("Гладиатор", "Gladiator", 2000));
        diskRepository.save(new Disk("Матрица", "The Matrix", 1999));
        diskRepository.issueDisk(1, 12);
        diskRepository.issueDisk(7, 30);
        diskRepository.issueDisk(5,14);
        diskRepository.issueDisk(4,15);
        diskRepository.issueDisk(7,18);
        diskRepository.issueDisk(10, 21);
        diskRepository.issueDisk(4,23);
        diskRepository.issueDisk(5, 24);
        diskRepository.issueDisk(10, 26);
        diskRepository.issueDisk(4, 28);
        diskRepository.issueDisk(1, 17);

    }
}
