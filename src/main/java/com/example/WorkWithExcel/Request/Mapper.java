package com.example.WorkWithExcel.Request;

import com.example.WorkWithExcel.Models.Person;

public class Mapper {

    public static Person mapFromRequestToThePerson(PersonRequest request)
    {
        Person person = new Person();
        person.setFullName(request.getFullName());
        person.setAge(request.getAge());
        person.setCourseName(request.getCourseName());
        person.setIDnumber(request.getIdnumber());
        return person;
    }


}
