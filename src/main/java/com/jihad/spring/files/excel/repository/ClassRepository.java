package com.jihad.spring.files.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jihad.spring.files.excel.model.Classes;

public interface ClassRepository extends JpaRepository<Classes, Long> {
}
