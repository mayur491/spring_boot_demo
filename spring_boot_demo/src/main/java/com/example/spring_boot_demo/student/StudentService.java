package com.example.spring_boot_demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public void registerNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("Email already taken.");
		}
		studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		if (!studentRepository.existsById(studentId)) {
			throw new IllegalStateException("Student with id:" + studentId + " doesn't exist.");
		}
		studentRepository.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId, String name, String email) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("Student with id:" + studentId + " doesn't exist."));
		
		// update name
		if(!ObjectUtils.isEmpty(name) && !Objects.equals(student.getName(), name)) {
			student.setName(name);
		}
		
		// update email
		if(!ObjectUtils.isEmpty(email) && !Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
			if(studentOptional.isPresent()) {
				throw new IllegalStateException("Email already taken.");
			}
			student.setEmail(email);
		}
	}

}
