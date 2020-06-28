package com.jihad.spring.files.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jihad.spring.files.excel.model.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
