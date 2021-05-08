package com.example.spring_boot_security_demo.student.service;

import java.time.LocalDate;
import java.util.List;

import com.example.spring_boot_security_demo.student.entity.Student;

public interface StudentService {

	public List<Student> getStudents();

	public Student registerNewStudent(Student student);

	public void deleteStudent(Long studentId);

	public void updateStudent(Long studentId, String name, String email, LocalDate dateOfBirth);

	public Student getStudent(Long studentId);

}
