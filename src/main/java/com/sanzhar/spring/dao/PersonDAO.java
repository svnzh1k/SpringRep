package com.sanzhar.spring.dao;

import com.sanzhar.spring.models.Book;
import com.sanzhar.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ClientInfoStatus;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person (name,age) VALUES (?,?)", person.getName(),person.getAge());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name=?, age = ? WHERE id=?", person.getName(),person.getAge(),person.getId());
    }

    public void remove(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public List<Book> getPersonsBooks(int id){
        return jdbcTemplate.query("select book.name, author,year from book join person on book.person_id=person.id where person_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean hasBooks(int id){
        if(jdbcTemplate.query("select book.name, author,year from book join person on book.person_id=person.id where person_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null) == null){
            return false;
        }
        return true;
    }

}
