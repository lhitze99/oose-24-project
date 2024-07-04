package com.sse.ooseproject;

import com.sse.ooseproject.controllers.StudentValidator;
import com.sse.ooseproject.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OoseProjectApplicationTests {

	private StudentValidator studentValidator;
	private StudentRepository studentRepository;
	private InstituteRepository instituteRepository;

	@BeforeEach
	void setUp() {
		studentRepository = Mockito.mock(StudentRepository.class);
		instituteRepository = Mockito.mock(InstituteRepository.class);
		studentValidator = new StudentValidator(studentRepository, instituteRepository);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void allFieldsFilled() {
		Student student = new Student(	null,
										"Böhme",
										"malin.boehme@web.de",
										"7349793",
										"Computer Science");
		Errors errors = new BeanPropertyBindingResult(student, "student");

		studentValidator.validate(student, errors);
		assertTrue(errors.hasErrors());
	}

	@Test
	public void emailAddressCorrect () {
		Student student = new Student(	"Malin",
										"Böhme",
										"malin.boehme.web.de",
										"7349793",
										"Computer Science");
		Errors errors = new BeanPropertyBindingResult(student, "student");

		studentValidator.validate(student, errors);
		assertTrue(errors.hasErrors());
	}

	@Test
	public void newMatriculationNumber () {
		Student student1 = new Student(	"Malin",
										"Böhme",
										"malin.boehme.web.de",
										"7349793",
										"Computer Science");
		Student student2 = new Student(	"Stefanie",
										"Marx",
										"stefanie.marx99@web.de",
										"7349793",
										"Computer Science");
		Errors errors = new BeanPropertyBindingResult(student2, "student");

		studentValidator.validate(student2, errors);
		assertTrue(errors.hasErrors());
	}

	@Test
	public void existingStudySubject () {
		Student student = new Student(	"Malin",
										"Böhme",
										"malin.boehme.web.de",
										"7349793",
										"Informatik");
		Errors errors = new BeanPropertyBindingResult(student, "student");

		studentValidator.validate(student, errors);
		assertTrue(errors.hasErrors());
	}

	@Test
	public void correctEntry () {
		Student student = new Student(	"Malin",
										"Böhme",
										"malin.boehme.web.de",
										"7349793",
										"Computer Science");
		Errors errors = new BeanPropertyBindingResult(student, "student");

		studentValidator.validate(student, errors);
		assertTrue(errors.hasErrors());
	}

}
