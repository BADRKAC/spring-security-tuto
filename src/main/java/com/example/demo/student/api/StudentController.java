package com.example.demo.student.api;


import com.example.demo.student.entities.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
          new Student(1,"Lilia"),
            new Student(2,"Anna"),
            new Student(3,"Badr")
    );

    @GetMapping(path="{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
      return STUDENTS.stream().
              filter(s -> studentId.equals(s.getStudentId())).
              findFirst().
              orElseThrow(()-> new IllegalStateException("Student "+ studentId + "doesn't exist"));
    }
}
