package ru.chembaev.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chembaev.spring.models.Book;
import ru.chembaev.spring.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public Optional<Person> show(String fio) {
        return jdbcTemplate.query("SELECT * FROM Person p WHERE p.fio = ?", new BeanPropertyRowMapper<>(Person.class), fio)
                .stream()
                .findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (fio, year_of_birth) VALUES(?, ?)", person.getFio(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET fio=?, year_of_birth=? WHERE id=?", updatedPerson.getFio(),
                updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }
}
