package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import dao.ExcelDAOImpl;

public class ExcelDAOImplTest {
	private ExcelDAOImpl excelDao;

	@Test(timeout = 40000)
	public void testGetRow() throws InvalidFormatException, IOException {
		OPCPackage pkg = OPCPackage.open(new File("D:\\Study\\Year 3\\CSE210\\CW\\test.xlsx"));
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(pkg);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		XSSFRow excel_row = sheet.getRow(1);
		
		excelDao = new ExcelDAOImpl();
		assertEquals("12:44", excelDao.getRow(excel_row));
	}

}
