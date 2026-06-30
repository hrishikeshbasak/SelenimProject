package org.TestComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader {

	public ArrayList<String> getData(String testcases) throws IOException {
		ArrayList<String> list = new ArrayList<String>();

		String excelFilePath = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "resources", "TestData.xlsx").toString();

		try (FileInputStream fileInputStream = new FileInputStream(excelFilePath); 
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

			int totalSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < totalSheets; i++) {

				if (workbook.getSheetName(i).equalsIgnoreCase("Testdata")) {
					XSSFSheet sheet = workbook.getSheetAt(i);

					Iterator<Row> rows = sheet.iterator();
					Row firstRow = rows.next();
					Iterator<Cell> cell = firstRow.cellIterator();

					int k = 0;
					int column = 0;
					while (!cell.hasNext()) {
						Cell cellValue = cell.next();
						if (cellValue.getStringCellValue().equalsIgnoreCase("TestCases")) {
							column = k;
						}
						k++;
					}
					// This is used to format the cell value to string, regardless of the cell type (numeric, string, etc.)
					DataFormatter formatter = new DataFormatter();
					while (rows.hasNext()) {
						Row r = rows.next();
						if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcases)) {
							Iterator<Cell> cv = r.cellIterator();
							while (cv.hasNext()) {
							String	formattedValue = formatter.formatCellValue(cv.next());
								list.add(formattedValue);
							}
							break;
						}
					}
				}
			}
			return list;
		}
	}
}
