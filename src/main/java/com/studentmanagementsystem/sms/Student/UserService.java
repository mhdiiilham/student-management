package com.studentmanagementsystem.sms.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private Repository repo;
    private Repository repo1;

    public List<Student> getStudents(String orderAge, String orderName) {
        List<Order> orders = new ArrayList<>();
        Direction orderAgeDirection = Direction.ASC;
        Direction orderNameDirection = Direction.ASC;

        if (orderAge == "DESC") orderAgeDirection = Direction.DESC;
        if (orderName == "DESC") orderNameDirection = Direction.DESC;

        orders.add(new Order(orderAgeDirection, "age"));
        orders.add(new Order(orderNameDirection, "name"));


        List<Student> students = repo.getStudents(Sort.by(orders));
        return students;
    }

    public void createStudent(Student student) {
        repo.save(student);
    }

    public void removeStudent(Integer studentNumber) {
        repo.deleteById(studentNumber);
    }

    public Student getByStudentNumber(Integer studentNumber) throws Exception {
        Optional<Student> student = repo.findById(studentNumber);
        if (student.isPresent()) {
            return student.get();
        }

        throw new Exception("student with student number " + student + " not found");
    }

    public void modifyStudent(Student student) {
        repo.save(student);
    }
}