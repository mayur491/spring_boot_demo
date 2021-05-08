package com.example.spring_boot_demo.student;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.spring_boot_demo.exception.ExceptionResponse;
import com.example.spring_boot_demo.exception.UserNotFoundException;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

	private final StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<Object> getStudents() {

		List<Student> students = null;
		try {
			students = studentService.getStudents();
		} catch (Exception e) {
			throw new ExceptionResponse();
		}
		if (students == null) {
			throw new UserNotFoundException();
		}
		return ResponseEntity.ok(students);
	}

	@GetMapping(path = "{studentId}")
	public ResponseEntity<Object> getStudent(@PathVariable Long studentId) {

		Optional<Student> student = Optional.empty();
		try {
			student = studentService.getStudent(studentId);
		} catch (Exception e) {
			throw new ExceptionResponse();
		}
		if (student.isEmpty()) {
			throw new UserNotFoundException(String.format("User with id: %s not found",
					studentId));
		}
		return ResponseEntity.ok(student);
	}

	@PostMapping
	public ResponseEntity<Object> registerNewStudent(@Valid @RequestBody Student student) {

		try {
			studentService.registerNewStudent(student);
		} catch (Exception e) {
			throw new ExceptionResponse();
		}

		if (ObjectUtils.isEmpty(student.getId())) {
			throw new ExceptionResponse();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/id")
				.buildAndExpand(student.getId())
				.toUri();
		return ResponseEntity.created(location)
				.build();
	}

	@DeleteMapping(path = "{studentId}")
	public void deleteStudent(@PathVariable("studentId") Long studentId) {
		studentService.deleteStudent(studentId);
	}

	@PutMapping(path = "{studentId}")
	public void updateStudent(@PathVariable("studentId") Long studentId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String email) {
		studentService.updateStudent(studentId,
				name,
				email);
	}
}
