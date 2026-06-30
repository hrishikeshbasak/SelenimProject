package org.TestComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader {

	public ArrayList<String> getData(String testcases) throws IOException {
		ArrayList<String> list = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//org//resources//TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {

			if (workbook.getSheetName(i).equalsIgnoreCase("Testdata")) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cell = firstRow.cellIterator();

				int k = 0;
				int column = 0;
				while (cell.hasNext()) {
					Cell cellValue = cell.next();
					if (cellValue.getStringCellValue().equalsIgnoreCase("TestCases")) {
						column = k;
					}
					k++;
				}
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcases)) {
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							list.add(cv.next().getStringCellValue());
						}
						break;
					}
				}
			}
		}
		workbook.close();
		fis.close();
		return list;
	}
}
