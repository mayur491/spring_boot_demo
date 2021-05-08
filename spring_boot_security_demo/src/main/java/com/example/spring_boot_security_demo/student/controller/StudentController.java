package com.example.spring_boot_security_demo.student.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
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

import com.example.spring_boot_security_demo.student.entity.Student;
import com.example.spring_boot_security_demo.student.service.StudentService;

@Controller
@RestController
@RequestMapping(path = "api/v1/student")

// to ensure @PreAuthorize is used
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	private final StudentService studentService;

	@Autowired
	public StudentController(@Qualifier("studentServiceImpl") StudentService studentService) {
		this.studentService = studentService;
	}

	// @PreAuthorize
	// hasRole('ROLE_'), hasAnyRole('Role_'), hasAuthority('permission'),
	// hasAnyAuthority('permission')

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	public List<Student> getStudents() {
		logger.info("In StudentController.getStudents()");
		return studentService.getStudents();
	}

	@GetMapping(path = "{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE', 'ROLE_STUDENT')")
	public List<Student> getStudent(@PathVariable("studentId") Long studentId) {
		List<Student> list = new ArrayList<>(1);
		logger.info("In StudentController.getStudent({})",
				studentId);
		list.add(studentService.getStudent(studentId));
		return list;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public ResponseEntity<Object> registerNewStudent(@Valid @RequestBody Student student) {
		List<Student> list = new ArrayList<>(1);
		logger.info("In StudentController.registerNewStudent({})",
				student);
		list.add(studentService.registerNewStudent(student));
		
		/*
		 * This creates a response header "location" which contains the actual location
		 * of the resource.
		 */
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(student.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Long studentId) {
		logger.info("In StudentController.deleteStudent({})",
				studentId);
		studentService.deleteStudent(studentId);
	}

	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") Long studentId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) LocalDate dateOfBirth) {
		logger.info("In StudentController.updateStudent({}, {}, {}, {})",
				studentId,
				name,
				email,
				dateOfBirth);
		studentService.updateStudent(studentId,
				name,
				email,
				dateOfBirth);
	}

}
