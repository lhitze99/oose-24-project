package com.sse.ooseproject.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrollmentId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course_id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student_id;

    // Default constructor
    public EnrollmentId() {}

    // Constructor
    public EnrollmentId(Course course_id, Student student_id) {
        this.course_id = course_id;
        this.student_id = student_id;
    }

    // Getters and Setters
    public Course getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Course course_id) {
        this.course_id = course_id;
    }

    public Student getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Student student_id) {
        this.student_id = student_id;
    }

    /*// equals() and hashCode() methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(course_id, that.course_id) && Objects.equals(student_id, that.student_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_id, student_id);
    }*/
}
