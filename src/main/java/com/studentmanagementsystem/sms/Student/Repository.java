package com.studentmanagementsystem.sms.Student;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Repository extends JpaRepository<Student, Integer> {

    @Query(value="SELECT s FROM Student s WHERE LOWER(s.name) LIKE %:name%", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort,
            @Param("name") String name
    );

    @Query(value="SELECT s FROM Student s WHERE s.age = :age", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort,
            @Param("age") Integer age
    );
}
