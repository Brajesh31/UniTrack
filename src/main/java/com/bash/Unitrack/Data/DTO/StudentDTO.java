package com.bash.Unitrack.Data.DTO;

import com.bash.Unitrack.Data.Models.Student;

public class StudentDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String program;
    private String IndexNumber;
    private String email;

    public StudentDTO(Student user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.program = ""; // or fetch from a subclass if applicable
        this.IndexNumber = ""; // or fetch from a subclass if applicable
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getIndexNumber() {
        return IndexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        IndexNumber = indexNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
