package com.example.spring_boot_security_demo.student.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.spring_boot_security_demo.student.constants.StudentConstants;
import com.example.spring_boot_security_demo.student.dao.StudentDao;
import com.example.spring_boot_security_demo.student.entity.Student;
import com.example.spring_boot_security_demo.student.service.StudentService;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {

	private final StudentDao studentDao;

	@Autowired
	public StudentServiceImpl(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public List<Student> getStudents() {
		int pageNumber = 0;
		int pageSize = 10;
		String sortBy = "name";
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		return studentDao
				.findAll(pageable)
				.toList();
	}

	public Student registerNewStudent(Student student) {
		Optional<Student> studentOptional = studentDao.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException(String.format(StudentConstants.EMAIL_ALREADY_TAKEN, student.getEmail()));
		}
		return studentDao.save(student);
	}

	public void deleteStudent(Long studentId) {
		if (!studentDao.existsById(studentId)) {
			throw new IllegalStateException(String.format(StudentConstants.STUDENT_DOES_NOT_EXIST, studentId));
		}
		studentDao.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId, String name, String email, LocalDate dateOfBirth) {

		Student student = studentDao.findById(studentId).orElseThrow(
				() -> new IllegalStateException(String.format(StudentConstants.STUDENT_DOES_NOT_EXIST, studentId)));

		// update name
		if (!ObjectUtils.isEmpty(name) && !Objects.equals(student.getName(), name)) {
			student.setName(name);
		}

		// update email
		if (!ObjectUtils.isEmpty(email) && !Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional = studentDao.findStudentByEmail(email);
			if (studentOptional.isPresent()) {
				throw new IllegalStateException(
						String.format(StudentConstants.EMAIL_ALREADY_TAKEN, student.getEmail()));
			}
			student.setEmail(email);
		}
		
		// update dateOfBirth
		if(!ObjectUtils.isEmpty(dateOfBirth) && !Objects.equals(student.getDateOfBirth(), dateOfBirth)) {
			student.setDateOfBirth(dateOfBirth);
		}
	}

	public Student getStudent(Long studentId) {
		Optional<Student> studentOptional = studentDao.findById(studentId);
		if (!studentOptional.isPresent()) {
			throw new IllegalStateException(String.format(StudentConstants.STUDENT_DOES_NOT_EXIST, studentId));
		}
		return studentOptional.get();
	}

}
