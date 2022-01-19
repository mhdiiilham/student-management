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
public class StudentService {
    @Autowired private Repository repo;
    private Repository repo1;

    public List<Student> getStudents(
            String orderAge,
            String orderName,
            String name,
            Integer age,
            Integer studentNumber
    ) {
        List<Order> orders = new ArrayList<>();
        Direction orderAgeDirection = Direction.ASC;
        Direction orderNameDirection = Direction.ASC;

        if (orderAge.equals("DESC")) {
            orderAgeDirection = Direction.DESC;
        }

        if (orderName.equals("DESC")) {
            orderNameDirection = Direction.DESC;
        }

        orders.add(new Order(orderAgeDirection, "age"));
        orders.add(new Order(orderNameDirection, "name"));

        if (studentNumber > 0) {
            return repo.getStudents(Sort.by(orders), studentNumber.toString());

        }  if (!name.isEmpty() && age == 0) {
            return repo.getStudentsByName(Sort.by(orders), name);

        } else if (age > 0 && !name.isEmpty()) {
            return repo.getStudents(Sort.by(orders), name, age);

        } else if (age > 0 ) {
            return repo.getStudents(Sort.by(orders), age);

        }

        return repo.getStudents(Sort.by(orders));
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

    public StudentStatistic getStudentStatistic() {
        StudentStatistic statistic = new StudentStatistic();
        Integer totalMale = Math.toIntExact(repo.countBySex("1"));
        Integer totalFemale = Math.toIntExact(repo.countBySex("0"));

        statistic.setTotalMale(totalMale);
        statistic.setTotalFemale(totalFemale);

        return  statistic;
    }
}
