package com.students.dao;

import com.students.domain.Student;
import com.students.exceptions.StudentsDBException;

import java.util.List;

public interface StudentDAO {
    List<Student> getAll() throws StudentsDBException;
    Student getById(String id);
    Student create(Student student);
    Student update(String id, Student student);
    boolean delete(String id);
}
