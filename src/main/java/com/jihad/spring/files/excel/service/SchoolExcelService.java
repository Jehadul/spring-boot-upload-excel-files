package com.jihad.spring.files.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jihad.spring.files.excel.helper.SchoolExcelHelper;
import com.jihad.spring.files.excel.model.School;
import com.jihad.spring.files.excel.repository.SchoolRepository;

@Service
public class SchoolExcelService {
  @Autowired
  SchoolRepository repository;

  public void save(MultipartFile file) {
    try {
      List<School> schools = SchoolExcelHelper.excelToSchools(file.getInputStream());
      repository.saveAll(schools);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<School> schools = repository.findAll();

    ByteArrayInputStream in = SchoolExcelHelper.schoolsToExcel(schools);
    return in;
  }

  public List<School> getAllSchools() {
    return repository.findAll();
  }
}
