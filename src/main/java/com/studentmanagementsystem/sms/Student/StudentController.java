package com.studentmanagementsystem.sms.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@EnableJpaRepositories
public class StudentController {
    @Autowired private UserService userService;

    @GetMapping("/students")
    public String getStudents(@RequestParam(required = false, name = "order-age", defaultValue = "ASC") String orderAge, @RequestParam(required = false, name = "order-name", defaultValue = "ASC") String orderName, Model model) {
        List<Student> students = userService.getStudents(orderAge, orderName);
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/student/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "studentNewFrom";
    }

    @PostMapping("/students")
    public String createStudent(Student student, RedirectAttributes re) {
        userService.createStudent(student);
        re.addFlashAttribute("message", "Student student " + student.getName() + " has been created");
        return "redirect:/students";
    }

    @DeleteMapping("/students/{number}")
    public String deleteStudent(@PathVariable("number") Integer studentNumber, RedirectAttributes re) {
        userService.removeStudent(studentNumber);
        re.addFlashAttribute("message", "success delete student " + studentNumber);
        return "redirect:/students";
    }

    @GetMapping("/student/edit/{number}")
    public String modifyForm(@PathVariable("number") Integer studentNumber, Model model, RedirectAttributes re) {
        try {
            Student student = userService.getByStudentNumber(studentNumber);
            model.addAttribute("student", student);
            System.out.println("student number " + studentNumber);
            return "student_modify_form";
        } catch (Exception e) {
            re.addFlashAttribute("message", e.getMessage());
            return "redirect:/students";
        }
    }

    @PutMapping("/students/{number}")
    public String modifyStudent(@PathVariable("number") Integer studentNumber, Student student, RedirectAttributes re) {
        student.setStudentNumber(studentNumber);
        userService.modifyStudent(student);
        re.addFlashAttribute("message", "success modify student with number " + studentNumber);
        return "redirect:/students";
    }
}
