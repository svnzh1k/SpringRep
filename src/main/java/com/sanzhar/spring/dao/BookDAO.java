package com.sanzhar.spring.dao;

import com.sanzhar.spring.models.Book;
import com.sanzhar.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id){
        return jdbcTemplate.query("SELECT * FROM book where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES (?,?,?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name=?, author = ?, year = ? WHERE id=?", book.getName(), book.getAuthor(),book.getYear(),id);
    }

    public void remove(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public Person getOwner(int id){
        return jdbcTemplate.query("SELECT person.id, person.name, age FROM person join book on person.id = book.person_id where book.id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }


    public void release(int id) {
        jdbcTemplate.update("UPDATE book set person_id = null where id = ?", id);
    }

    public void assign(int idperson, int idbook) {
        jdbcTemplate.update("UPDATE book set person_id = ? where id = ?", idperson,idbook);
    }
}
