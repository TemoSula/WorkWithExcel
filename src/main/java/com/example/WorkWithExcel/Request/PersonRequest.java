package com.example.WorkWithExcel.Request;

public class PersonRequest {

    private String fullName;
    private int age;
    private String courseName;
    private long Idnumber;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getIdnumber() {
        return Idnumber;
    }

    public void setIdnumber(long idnumber) {
        Idnumber = idnumber;
    }
}
