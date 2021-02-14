package com.example.spring_boot_demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student mayur = new Student("Mayur Somani", "mayur491@gmail.com", LocalDate.of(1996, Month.FEBRUARY, 9));
			Student mohit = new Student("Mohit Somani", "mohit.somani@gmail.com", LocalDate.of(1999, Month.MARCH, 24));
			studentRepository.saveAll(List.of(mayur, mohit));
		};
	}

}
