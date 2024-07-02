package com.sse.ooseproject;

import com.sse.ooseproject.models.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {

    List<Institute> findByName(String name);
    List<Institute> findByProvidesStudySubject(String studySubject);


}