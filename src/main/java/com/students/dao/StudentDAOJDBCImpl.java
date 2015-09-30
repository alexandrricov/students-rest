package com.students.dao;

import com.students.domain.Student;
import com.students.exceptions.StudentsDBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDAOJDBCImpl implements StudentDAO {
    static final String DB_URL = "jdbc:mysql://localhost:3307/institution";
    static final String USER = "root";
    static final String PASS = "root";

    public List<Student> getAll() throws StudentsDBException {
        List<Student> students = new ArrayList<>();
        Student student;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement("SELECT id, name, birthday, groupId FROM students");
            rs = ps.executeQuery();

            while (rs.next()) {
                student = new Student();
                student.setId(rs.getString("students.id"));
                student.setName(rs.getString("students.name"));
                student.setBirthday(rs.getDate("students.birthday"));
                student.setGroupId(rs.getString("students.groupId"));

                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            throw new StudentsDBException("Students DB connection failed.", e);
        } finally {
            closeResources(connection, ps, rs);
        }

        return students;
    }

    public Student getById(String id) {
        Student student = null;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement("SELECT id, name, birthday, groupId FROM students WHERE id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setId(rs.getString("students.id"));
                student.setName(rs.getString("students.name"));
                student.setBirthday(rs.getDate("students.birthday"));
                student.setGroupId(rs.getString("students.groupId"));
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        } finally {
            closeResources(connection, ps, rs);
        }

        return student;
    }

    public Student create(Student student) {
        Connection conn = null;
        PreparedStatement ps = null;

        student.setId(UUID.randomUUID().toString());

        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO students (id, name, birthday, groupId) VALUES(?, ?, ?, ?)");
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDate(3, new java.sql.Date(student.getBirthday().getTime()));
            ps.setString(4, student.getGroupId());
            ps.executeUpdate();

            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return null;
    }

    public Student update(String id, Student student) {
        Connection conn = null;
        PreparedStatement ps = null;

        if (student.getId() == null) {
            student.setId(id);
        }

        try {
            conn = getConnection();
            ps = conn.prepareStatement("UPDATE students SET name = ?, birthday = ?, groupId = ? WHERE id = ?");
            ps.setString(1, student.getName());
            ps.setDate(2, new java.sql.Date(student.getBirthday().getTime()));
            ps.setString(3, student.getGroupId());
            ps.setString(4, id);
            ps.executeUpdate();

            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return null;
    }

    public boolean delete(String id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM students WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return false;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
