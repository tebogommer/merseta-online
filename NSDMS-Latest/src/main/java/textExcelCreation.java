import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class textExcelCreation {

	public static void main(String[] args){
		runFour();
	}
	
	public static void runFour(){
		Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "Entity ID", "Company Name", "Chamber", "Region Town", "Company Size", "Organisation Type", "Categorisation", "DG Levy (49.5%)", "Mandatory Grant Status", "Co-Funding", "DG Verification Status", "Requested Intervention", "Recommended Intervention", "Recommended Intervention title", "Ofo Code", "Requested Learners", "Requested learners with disability", "Recommended Learners", "Awarded Learners", "Partial Funding Awarded Learners", "Requested Amount", "Recommended Amount", "Award Amount", "Balance", "Partial Funding Award Amount", "Partial Funding Balance", "Disabled Grant Amount", "Total Awarded" });
		data.put("2", new Object[] { "TEST-COMP-1", "Amit", "Shukla" });
		data.put("3", new Object[] { "TEST-COMP-2", "Lokesh", "Gupta" });
		data.put("4", new Object[] { "TEST-COMP-3", "John", "Adwards" });
		data.put("5", new Object[] { "TEST-COMP-4", "Brian", "Schultz" });
		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		
		// generate on file system for test
		FileOutputStream out;
		try {
			out = new FileOutputStream("Test.xlsx");
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// generates byte array 
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try {
//			wb.write(bos);
//			byte[] bytes = bos.toByteArray();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void runThree(){
		Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		Sheet sh = wb.createSheet();
		for(int rownum = 0; rownum < 1000; rownum++){
			Row row = sh.createRow(rownum);
			for(int cellnum = 0; cellnum < 10; cellnum++){
				Cell cell = row.createCell(cellnum);
				String address = new CellReference(cell).formatAsString();
				cell.setCellValue(address);
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runTwo(){
		Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		Sheet sh = wb.createSheet();
		for(int rownum = 0; rownum < 1000; rownum++){
			Row row = sh.createRow(rownum);
			for(int cellnum = 0; cellnum < 10; cellnum++){
				Cell cell = row.createCell(cellnum);
				String address = new CellReference(cell).formatAsString();
				cell.setCellValue(address);
			}
		}

		FileOutputStream out;
		try {
			out = new FileOutputStream("temp.xlsx");
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runOneOne(){
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// need to look at SXSSF, better for memory 
//		SXSSFWorkbook test = new SXSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
		data.put("2", new Object[] { 1, "Amit", "Shukla" });
		data.put("3", new Object[] { 2, "Lokesh", "Gupta" });
		data.put("4", new Object[] { 3, "John", "Adwards" });
		data.put("5", new Object[] { 4, "Brian", "Schultz" });
		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
			File file = new File("howtodoinjava_demo.xlsx");
			workbook.write(out);
			out.close();
			System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
