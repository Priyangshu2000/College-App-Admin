package com.example.collegeapp_admin.Faculty;

public class FacultyModel {
    public FacultyModel() {
    }

    private String name;
    private String designation;
    private String email;
    private String userpic;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public FacultyModel(String name, String designation, String email, String userpic, String facultyId) {
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.userpic = userpic;
        this.facultyId = facultyId;
    }

    private String facultyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public FacultyModel(String name, String designation, String email, String userpic) {
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.userpic = userpic;
    }
}