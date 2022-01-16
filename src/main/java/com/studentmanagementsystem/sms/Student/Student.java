package com.studentmanagementsystem.sms.Student;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private int studentNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1)
    private String sex;

    @Column(nullable = false, length = 2)
    private String age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "birth_day")
    private LocalDate birthDay;

    @Column(nullable = false, name = "class")
    private String studentClass;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "enroll_date")
    private LocalDate enrollDate;

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }
}
