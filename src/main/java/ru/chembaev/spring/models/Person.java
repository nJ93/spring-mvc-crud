package ru.chembaev.spring.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 100, message = "Name should be between 2 and 30 characters")
    private String fio;

    @Min(value = 1900, message = "Year of birth should be greater than 0")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int id, String fio, int yearOfBirth) {
        this.id = id;
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
