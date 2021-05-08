package com.example.spring_boot_security_demo.student.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about the Student.")
@Entity
@Table
public class Student implements Serializable {

	private static final long serialVersionUID = 3635793179121206700L;
	
	@Id
	@SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")

	private Long id;

	@ApiModelProperty(notes = "Name should have atleast 2 characters.")
	@Size(min = 2, message = "Name should have atleast 2 characters.")
	private String name;
	
	private String email;
	
	@ApiModelProperty(notes = "Date of birth should be in the past.")
	@Past(message = "Date of birth should be in the past.")
	private LocalDate dateOfBirth;
	
	@Transient
	private Integer age;

	public Student() {
		super();
	}

	public Student(Long id, String name, String email, LocalDate dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
	}

	public Student(String name, String email, LocalDate dateOfBirth) {
		super();
		this.name = name;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getAge() {
		if (this.dateOfBirth != null) {
			return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
		}
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", age="
				+ age + "]";
	}

}
