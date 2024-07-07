package com.sse.ooseproject.controllers;

import com.sse.ooseproject.CourseRepository;
import com.sse.ooseproject.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseController {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public String courses(Model model,
                           @RequestParam(value = "sort_by", defaultValue = "name") String sortBy,
                           @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {


        List<Course> courses = courseRepository.findAll();

        Comparator<Course> comparator;
        switch (sortBy){
            case "name":
                comparator = Comparator.comparing(Course::getName);
                break;
            case "chair":
                comparator = Comparator.comparing(course -> course.getChair().getName());
                break;
            default:
                comparator = Comparator.comparing(Course::getId);
                break;
        }

        if (!sortAsc) {
            comparator = comparator.reversed();
        }

        List<Course> sortedCourses = courses.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        model.addAttribute("courses", sortedCourses);
        model.addAttribute("sort_by", sortBy);
        model.addAttribute("sort_asc", sortAsc);

        return "courses";
    }
}