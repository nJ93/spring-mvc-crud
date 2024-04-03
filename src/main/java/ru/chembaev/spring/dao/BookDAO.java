package ru.chembaev.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;
import ru.chembaev.spring.models.Book;
import ru.chembaev.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (name, author, publish_year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getPublishYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, publish_year=?, person_id=? WHERE id=?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getPublishYear(), updatedBook.getPersonId(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public Optional<Person> getOwnerInfo(int id) {
        return jdbcTemplate.query("SELECT p.* FROM Book b INNER JOIN Person p ON p.id = b.person_id WHERE b.id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream()
                .findAny();
    }

    public void updateAffilation(int bookId, int personId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", personId, bookId);
    }

    public void deleteAffilation(int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?", bookId);
    }
}
