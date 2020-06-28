package com.jihad.spring.files.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jihad.spring.files.excel.helper.ClassExcelHelper;
import com.jihad.spring.files.excel.model.Classes;
import com.jihad.spring.files.excel.repository.ClassRepository;

@Service
public class ClassExcelService {
  @Autowired
  ClassRepository repository;

  public void save(MultipartFile file) {
    try {
      List<Classes> classes = ClassExcelHelper.excelToClasses(file.getInputStream());
      repository.saveAll(classes);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Classes> classes = repository.findAll();

    ByteArrayInputStream in = ClassExcelHelper.classesToExcel(classes);
    return in;
  }

  public List<Classes> getAllClasses() {
    return repository.findAll();
  }
}
