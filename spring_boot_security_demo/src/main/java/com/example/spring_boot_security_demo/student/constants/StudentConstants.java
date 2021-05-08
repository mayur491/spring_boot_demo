package com.example.spring_boot_security_demo.student.constants;

public final class StudentConstants {

	private StudentConstants() {
		throw new IllegalStateException("Constants class");
	}

	public static final String STUDENT_DOES_NOT_EXIST = "Student with id: %s doesn't exist.";

	public static final String EMAIL_ALREADY_TAKEN = "Email: %s already taken.";

}
