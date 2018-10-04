package edu.birzeit.entity;

public class Department {
    int id;
    String name;
    String headOfDepartment;
    String faculty;

    public Department() {
        // TODO Auto-generated constructor stub
    }

    public Department(String name, String headOfDepartment, String faculty) {
        super();
        this.name = name;
        this.headOfDepartment = headOfDepartment;
        this.faculty = faculty;
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

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

}
