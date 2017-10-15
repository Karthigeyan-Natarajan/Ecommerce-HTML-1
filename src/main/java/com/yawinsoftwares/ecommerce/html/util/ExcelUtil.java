package com.yawinsoftwares.ecommerce.html.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static Map<String, ArrayList<ArrayList<Object>>> read(String fileName) {
		FileInputStream inputStream = null;
		Workbook workbook = null;
		Map<String, ArrayList<ArrayList<Object>>> sheetMap = new HashMap<String, ArrayList<ArrayList<Object>>>();
		try {
			inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			int noofSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < noofSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				ArrayList<ArrayList<Object>> table = sheetMap.get(sheet.getSheetName());
				if (table == null) {
					table = new ArrayList<ArrayList<Object>>();
					sheetMap.put(sheet.getSheetName(), table);
				}
				Iterator<Row> iterator = sheet.iterator();
				int rowNumber = 0;
				while (iterator.hasNext()) {
					Row row = iterator.next();
					if(rowNumber==0) {rowNumber++; continue;}
					if(row==null || row.getCell(0) == null || row.getCell(0).getCellTypeEnum() == CellType.BLANK) break;
					
					ArrayList<Object> tableRow = new ArrayList<Object>();
					table.add(tableRow);
					
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						
						CellType type = cell.getCellTypeEnum();
						if (type == CellType.STRING) {
							tableRow.add(cell.getStringCellValue());
						} else if (type == CellType.NUMERIC) {
							tableRow.add(cell.getNumericCellValue());
						} else if (type == CellType.BOOLEAN) {
							tableRow.add(cell.getBooleanCellValue());
						} else if (type == CellType.BLANK) {
							tableRow.add(null);
						}
					}
					rowNumber++;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {
			}
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}
		return sheetMap;
	}

	public static void main(String[] args) {
		Map<String, ArrayList<ArrayList<Object>>> sheetMap = ExcelUtil.read("products.xlsx");
		System.out.println(sheetMap);
	}
}
