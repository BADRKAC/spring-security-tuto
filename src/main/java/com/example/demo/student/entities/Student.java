package com.example.demo.student.entities;


public class Student {

    private final Integer studentId;
    private final String studentName;

    public Integer getStudentId() {
        return studentId;
    }
    public String getStudentName() {
        return studentName;
    }

    public Student(Integer studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
