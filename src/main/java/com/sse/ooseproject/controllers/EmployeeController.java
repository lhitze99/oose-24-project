package com.sse.ooseproject.controllers;

import com.sse.ooseproject.EmployeeRepository;
import com.sse.ooseproject.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }

    @GetMapping("/employees")
    public String employees(Model model,
                            @RequestParam(value = "sort_by", defaultValue = "lastName") String sortBy,
                            @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {

        List<Employee> employees = employeeRepository.findAll();

        Comparator<Employee> comparator;
        switch (sortBy) {
            case "firstName":
                comparator = Comparator.comparing(Employee::getFirstName);
                break;
            case "lastName":
                comparator = Comparator.comparing(Employee::getLastName);
                break;
            case "isProfessor":
                comparator = Comparator.comparing(Employee::isProfessor);
                break;
            default:
                comparator = Comparator.comparing(Employee::getStaffNr);
                break;
        }

        if (!sortAsc) {
            comparator = comparator.reversed();
        }

        List<Employee> sortedEmployees = employees.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        model.addAttribute("employees", sortedEmployees);
        model.addAttribute("sort_by", sortBy);
        model.addAttribute("sort_asc", sortAsc);

        return "employees";
    }
}
