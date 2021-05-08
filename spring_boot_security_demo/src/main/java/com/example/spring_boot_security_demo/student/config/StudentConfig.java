package com.example.spring_boot_security_demo.student.config;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring_boot_security_demo.student.dao.StudentDao;
import com.example.spring_boot_security_demo.student.entity.Student;

@Configuration
public class StudentConfig {

	@Bean
	CommandLineRunner commandLineRunner(StudentDao studentRepository) {
		return args -> {
			Student mayur = new Student("Mayur Somani", "mayur491@gmail.com", LocalDate.of(1996, Month.FEBRUARY, 9));
			Student mohit = new Student("Mohit Somani", "mohit.somani@gmail.com", LocalDate.of(1999, Month.MARCH, 24));
			Student gopal = new Student("Gopal Somani", "gopal.somani@gmail.com", LocalDate.of(1950, Month.JUNE, 30));
			Student veena = new Student("Veena Somani", "veena.somani@gmail.com", LocalDate.of(1965, Month.JANUARY, 4));

			studentRepository.saveAll(List.of(mayur, mohit, gopal, veena));
		};
	}

}
