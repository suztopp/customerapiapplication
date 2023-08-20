package com.example.CustomerApplication.model;

import java.time.LocalDate;

public class CustomerDTO {

    String firstname;
    String lastName;
    LocalDate birthDate;

    public CustomerDTO() {
    }

    public CustomerDTO(String firstname, String lastName, LocalDate birthDate) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}

