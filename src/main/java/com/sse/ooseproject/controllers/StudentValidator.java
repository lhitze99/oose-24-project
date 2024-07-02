package com.sse.ooseproject.controllers;

import com.sse.ooseproject.models.Student;
import com.sse.ooseproject.StudentRepository;
import com.sse.ooseproject.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


@Component
public class StudentValidator implements Validator{

    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;

    @Autowired
    public StudentValidator(StudentRepository studentRepository, InstituteRepository instituteRepository) {
        this.studentRepository = studentRepository;
        this.instituteRepository = instituteRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;

        // Check if all necessary fields are filled
        if (student.getFirstName() == null || student.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "NotEmpty");
        }
        if (student.getLastName() == null || student.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "NotEmpty");
        }
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            errors.rejectValue("email", "NotEmpty");
        }
        if (student.getMatNr() == null || student.getMatNr().isEmpty()) {
            errors.rejectValue("matNr", "NotEmpty");
        }
        if (student.getStudySubject() == null || student.getStudySubject().isEmpty()) {
            errors.rejectValue("studySubject", "NotEmpty");
        }

        // Check if email address is correct
        if (student.getEmail() != null && !Pattern.compile(".+@.+\\..+").matcher(student.getEmail()).matches()) {
            errors.rejectValue("email", "Email");
        }

        // Check if matriculation number already exists
        if (student.getMatNr() != null && studentRepository.findByMatNr(student.getMatNr()).isPresent()) {
            errors.rejectValue("matNr", "Duplicate.student.matNr");
        }

        // Check if the study subject exists
        if (student.getStudySubject() != null && instituteRepository.findByProvidesStudySubject(student.getStudySubject()).isEmpty()) {
            errors.rejectValue("studySubject", "Invalid.studySubject");
        }

    }
}