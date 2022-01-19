package com.studentmanagementsystem.sms.Student;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Repository extends JpaRepository<Student, Integer> {

    @Query(value="SELECT s FROM Student s WHERE CONCAT(s.studentNumber, '') LIKE %:studentNumber% AND LOWER(s.name) LIKE %:name% OR s.age = :age", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort,
            @Param("name") String name,
            @Param("age") Integer age,
            @Param("studentNumber") Integer studentNumber
    );

    @Query(value="SELECT s FROM Student s WHERE CONCAT(s.studentNumber, '') = :studentNumber", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort,
            @Param("studentNumber") String studentNumber
    );

    @Query(value="SELECT s FROM Student s WHERE LOWER(s.name) LIKE %:name% AND s.age = :age", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort,
            @Param("name") String name,
            @Param("age") Integer age
    );


    @Query(value="SELECT s FROM Student s WHERE s.age = :age", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort,
            @Param("age") Integer age
    );

    @Query(value="SELECT s FROM Student s", nativeQuery = false)
    public List<Student> getStudents(
            Sort sort
    );

    @Query(value="SELECT s FROM Student s WHERE LOWER(s.name) LIKE %:name%", nativeQuery = false)
    public List<Student> getStudentsByName(
            Sort sort,
            @Param("name") String name
    );

    @Query(value = "SELECT COUNT(*) FROM student s WHERE s.sex = 1", nativeQuery = true)
    Integer countMaleStudents();

    @Query(value = "SELECT COUNT(*) FROM student s WHERE s.sex = 0", nativeQuery = true)
    Integer countFemaleStudents();

    long countBySex(String sex);
}
