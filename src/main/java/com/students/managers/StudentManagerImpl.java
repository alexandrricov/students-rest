package com.students.managers;

import com.students.dao.StudentDAO;
import com.students.domain.Student;
import com.students.exceptions.StudentsDBException;

import java.util.List;

public class StudentManagerImpl implements StudentManager {
    private StudentDAO studentDao;

    public void setStudentDao(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    public List<Student> getAll() throws StudentsDBException {
        // TODO: set validations
        return studentDao.getAll();
    }

    public Student getById(String id) {
        // TODO: set validations
        return studentDao.getById(id);
    }

    public Student create(Student student) {
        // TODO: set validations
        return studentDao.create(student);
    }

    public Student update(String id, Student student) {
        // TODO: set validations
        return studentDao.update(id, student);
    }

    public boolean delete(String id) {
        // TODO: set validations
        return studentDao.delete(id);
    }
}
