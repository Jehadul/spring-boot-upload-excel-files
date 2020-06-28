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

import com.jihad.spring.files.excel.model.Classes;
import com.jihad.spring.files.excel.model.School;

public class ClassExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Class Code", "Class Name", "Classes Id" };
  static String SHEET = "Classes";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static ByteArrayInputStream classesToExcel(List<Classes> classes) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Classes clases : classes) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(clases.getId());
        row.createCell(1).setCellValue(clases.getClassCode());
        row.createCell(2).setCellValue(clases.getClassName());
        row.createCell(3).setCellValue(clases.getSchool().getId());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public static List<Classes> excelToClasses(InputStream is) {
    try {
      /*Workbook workbook = new XSSFWorkbook(is);
      Sheet sheet = workbook.getSheet(SHEET);*/
    	
      XSSFWorkbook workbook = new XSSFWorkbook(is);
      Sheet sheet = workbook.getSheetAt(0);
      
      Iterator<Row> rows = sheet.iterator();

      List<Classes> classes = new ArrayList<Classes>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Classes clases = new Classes();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
            clases.setId((long) currentCell.getNumericCellValue());
            break;

          case 1:
            clases.setClassCode(currentCell.getStringCellValue());
            break;

          case 2:
            clases.setClassName(currentCell.getStringCellValue());
            break;

          case 3:
        	School school = new School();
        	school.setId((long) currentCell.getNumericCellValue());
            clases.setSchool(school);
            break;
            
          default:
            break;
          }

          cellIdx++;
        }

        classes.add(clases);
      }

      workbook.close();

      return classes;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
