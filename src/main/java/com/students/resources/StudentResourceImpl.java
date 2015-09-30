package com.students.resources;

import com.students.domain.Student;
import com.students.exceptions.StudentsDBException;
import com.students.managers.StudentManager;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/students")
public class StudentResourceImpl implements StudentResource {
    private StudentManager studentManager;

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public Response getAll() {
        List<Student> students;

        try {
            students = studentManager.getAll();
            return Response.status(200).entity(students).build();
        } catch (StudentsDBException e) {
            ObjectMapper objectMapper = new ObjectMapper();

            String message = null;
            try {
                message = objectMapper.writeValueAsString(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return Response.status(500).entity(message).build();
        }
    }

    public Student getById(String id) {
        return studentManager.getById(id);
    }

    public Student create(Student student) {
        return studentManager.create(student);
    }

    public Student update(String id, Student student) {
        return studentManager.update(id, student);
    }

    public Response delete(String id) {
        boolean result = studentManager.delete(id);

        if (result) {
            return Response.status(200).build();
        }

        return Response.status(406).build();
    }
}
