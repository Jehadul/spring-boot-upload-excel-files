package com.jihad.spring.files.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jihad.spring.files.excel.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
}
