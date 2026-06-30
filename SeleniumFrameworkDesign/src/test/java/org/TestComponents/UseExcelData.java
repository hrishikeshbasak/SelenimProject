package org.TestComponents;

import java.io.IOException;
import java.util.ArrayList;

public class UseExcelData {

	public static void main(String[] args) throws IOException {
		ExcelDataReader read = new ExcelDataReader();
		ArrayList dataList = read.getData("purchase");

		System.out.println(dataList.get(0));
		System.out.println(dataList.get(1));
		System.out.println(dataList.get(2));
		System.out.println(dataList.get(3));
	}

}
