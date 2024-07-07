package com.sse.ooseproject.controllers;

import com.sse.ooseproject.StudentRepository;
import com.sse.ooseproject.models.Student;
import com.sse.ooseproject.models.Institute;
import com.sse.ooseproject.InstituteRepository;
import com.sse.ooseproject.controllers.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;
    private final StudentValidator studentValidator;

    @Autowired
    public StudentController(StudentRepository studentRepository, InstituteRepository instituteRepository, StudentValidator studentValidator) {
        this.studentRepository = studentRepository;
        this.instituteRepository = instituteRepository;
        this.studentValidator = studentValidator;
    }

        @GetMapping("/students")
        public String students (Model model,
                                @RequestParam(value = "sort_by", defaultValue = "lastName") String sortBy,
                                @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc){


            List<Student> students = studentRepository.findAll();

            Comparator<Student> comparator;
            switch (sortBy) {
                case "firstName":
                    comparator = Comparator.comparing(Student::getFirstName);
                    break;
                case "lastName":
                    comparator = Comparator.comparing(Student::getLastName);
                    break;
                case "studySubject":
                    comparator = Comparator.comparing(Student::getStudySubject);
                    break;
                default:
                    comparator = Comparator.comparing(Student::getMatNr);
                    break;
            }

            if (!sortAsc) {
                comparator = comparator.reversed();
            }

            List<Student> sortedStudents = students.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            model.addAttribute("students", sortedStudents);
            model.addAttribute("sort_by", sortBy);
            model.addAttribute("sort_asc", sortAsc);

            return "students";
        }

        @GetMapping("/student/new")
        public String newStudent (Model model){
            return setupStudentForm(null, model, "new");
        }

        @GetMapping("/student/edit")
        public String editStudent (@RequestParam("id") Long id, Model model){
            return setupStudentForm(id, model, "edit");
        }

        private String setupStudentForm (Long id, Model model, String pageType){
            Student student = (id == null) ? new Student() : studentRepository.findById(id).orElse(null);
            List<String> studySubjects = instituteRepository.findAll()
                    .stream()
                    .map(Institute::getProvidesStudySubject)
                    .collect(Collectors.toList());

            model.addAttribute("student", student);
            model.addAttribute("page_type", pageType);
            model.addAttribute("study_subjects", studySubjects);
            return "edit_student";
        }

        @PostMapping("/student/new")
        public String createStudent (@ModelAttribute("student") Student student, BindingResult result, Model model){
            studentValidator.validate(student, result);

            if (result.hasErrors()) {
                model.addAttribute("message_type", "error");
                model.addAttribute("message", "Fehler beim Erstellen des Studenten. Bitte überprüfen Sie Ihre Eingaben.");
                return setupStudentForm(null, model, "new");
            }

            studentRepository.save(student);
            model.addAttribute("message_type", "success");
            model.addAttribute("message", "Student erfolgreich erstellt.");

            return setupStudentForm(null, model, "new");
            //return "redirect:/students";
        }

        @PostMapping("/student/edit")
        public String updateStudent(@ModelAttribute("student") Student student,
                                    BindingResult bindingResult, Model model) {

        studentValidator.validate(student, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("message_type", "error");
            model.addAttribute("message", "Fehler beim Bearbeiten des Studenten. Bitte überprüfen Sie Ihre Eingaben.");
            return setupStudentForm(student.getId(), model, "edit");
        }

        studentRepository.save(student);
        model.addAttribute("message_type", "success");
        model.addAttribute("message", "Student erfolgreich bearbeitet.");

        return "redirect:/students";
        }

    }
