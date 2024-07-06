package com.sse.ooseproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    private String semester;

    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;

    // Default constructor
    public Enrollment() {}

    // Constructor
    public Enrollment(EnrollmentId id, String semester) {
        this.id = id;
        this.semester = semester;
    }

    // Getters and Setters
    public EnrollmentId getId() {
        return id;
    }

    public void setId(EnrollmentId id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
