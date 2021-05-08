package com.example.spring_boot_security_demo.student.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot_security_demo.student.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {

	// SELECT s FROM student s WHERE s.email=?
	@Query("SELECT s FROM Student s WHERE s.email=?1")
	Optional<Student> findStudentByEmail(String email);

}
