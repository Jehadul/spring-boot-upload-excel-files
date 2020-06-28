package com.jihad.spring.files.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.jihad.spring.files.excel.model.School;

public class SchoolExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "School Code", "School Name", "Gender", "Bording" };
  static String SHEET = "Schools";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static ByteArrayInputStream schoolsToExcel(List<School> schools) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (School school : schools) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(school.getId());
        row.createCell(1).setCellValue(school.getSchoolCode());
        row.createCell(2).setCellValue(school.getSchoolName());
        row.createCell(3).setCellValue(school.getGender());
        row.createCell(4).setCellValue(school.getBoarding());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public static List<School> excelToSchools(InputStream is) {
    try {
      /*Workbook workbook = new XSSFWorkbook(is);
      Sheet sheet = workbook.getSheet(SHEET);*/
    	
      XSSFWorkbook workbook = new XSSFWorkbook(is);
      Sheet sheet = workbook.getSheetAt(0);
      
      Iterator<Row> rows = sheet.iterator();

      List<School> schools = new ArrayList<School>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        School school = new School();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
            school.setId((long) currentCell.getNumericCellValue());
            break;

          case 1:
            school.setSchoolCode(currentCell.getStringCellValue());
            break;

          case 2:
            school.setSchoolName(currentCell.getStringCellValue());
            break;

          case 3:
            school.setGender(currentCell.getStringCellValue());
            break;
            
          case 4:
              school.setBoarding(currentCell.getStringCellValue());
              break;

          default:
            break;
          }

          cellIdx++;
        }

        schools.add(school);
      }

      workbook.close();

      return schools;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
