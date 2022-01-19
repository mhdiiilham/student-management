package com.studentmanagementsystem.sms.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@EnableJpaRepositories
public class StudentController {
    @Autowired private StudentService studentService;

    @GetMapping("/students")
    public String getStudents(
            @RequestParam(required = false, name = "order-age", defaultValue = "ASC") String orderAge,
            @RequestParam(required = false, name = "order-name", defaultValue = "ASC") String orderName,
            @RequestParam(required = false, name = "student-name", defaultValue = "") String studentName,
            @RequestParam(required = false, name = "student-age", defaultValue = "0") String studentAgeQuery,
            @RequestParam(required = false, name = "student-number", defaultValue = "0") String studentNumberQuery,
            Model model
    ) {
        Integer studentAge = Integer.parseInt(studentAgeQuery);
        Integer studentNumber = Integer.parseInt(studentNumberQuery);
        List<Student> students = studentService.getStudents(
                orderAge,
                orderName,
                studentName.toLowerCase(),
                studentAge,
                studentNumber
        );

        if (studentNumber > 0) {
            studentName = "";
            studentAge = 0;
        }

        model.addAttribute("orderAge", orderAge);
        model.addAttribute("orderName", orderName);
        model.addAttribute("students", students);
        model.addAttribute("studentName", studentName);
        model.addAttribute("studentAge", studentAge);
        model.addAttribute("studentNumber", studentNumber);
        model.addAttribute("studentCount", students.size());
        return "students";
    }

    @GetMapping("/student/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_new_from";
    }

    @PostMapping("/students")
    public String createStudent(Student student, RedirectAttributes re) {
        try {
            studentService.createStudent(student);
            re.addFlashAttribute(
                    "message",
                    "Student student " + student.getName() + " has been created"
            );
            return "redirect:/students";
        } catch (Exception e) {
            re.addFlashAttribute("error", e.getMessage());
            return  "redirect:/student/new";
        }
    }

    @DeleteMapping("/students/{number}")
    public String deleteStudent(@PathVariable("number") Integer studentNumber, RedirectAttributes re) {
        studentService.removeStudent(studentNumber);
        re.addFlashAttribute("message", "success delete student with ID " + studentNumber);
        return "redirect:/students";
    }

    @GetMapping("/student/edit/{number}")
    public String modifyForm(@PathVariable("number") Integer studentNumber, Model model, RedirectAttributes re) {
        try {
            Student student = studentService.getByStudentNumber(studentNumber);
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
        try {
            student.setStudentNumber(studentNumber);
            studentService.modifyStudent(student);
            re.addFlashAttribute("message", "success modify student with number " + studentNumber);
            return "redirect:/students";
        } catch (Exception err) {
            re.addFlashAttribute("error", err.getMessage());
            return "redirect:/students/edit/" + studentNumber;
        }
    }

    @GetMapping("/students/statistic")
    public String showStudentStatistic(Model model) {
        StudentStatistic statistic = studentService.getStudentStatistic();
        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("male", statistic.getTotalMale());
        graphData.put("female", statistic.getTotalFemale());
        model.addAttribute("chartData", graphData);
        return "statistic";
    }
}
