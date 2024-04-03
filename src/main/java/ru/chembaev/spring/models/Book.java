package ru.chembaev.spring.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    private Integer personId;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 3, max = 128, message = "Name should be between 3 and 100 characters")
    private String name;
    @NotEmpty(message = "Author should be not empty")
    @Size(min = 3, max = 256, message = "Author should be between 3 and 100 characters")
    private String author;

    private int publishYear;

    public Book() {
    }

    public Book(int id, Integer personId, String name, String author, int publishYear) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
