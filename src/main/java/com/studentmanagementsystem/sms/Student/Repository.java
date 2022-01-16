package com.studentmanagementsystem.sms.Student;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repository extends JpaRepository<Student, Integer> {

    @Query(value="SELECT s FROM Student s", nativeQuery = false)
    public List<Student> getStudents(Sort sort);
}
