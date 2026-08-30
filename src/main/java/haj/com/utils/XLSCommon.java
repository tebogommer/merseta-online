package haj.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import haj.com.dao.UsersDAO;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class XLSCommon.
 */
public class XLSCommon extends AbstractService {

	/** The all sheets map. */
	public static Map<String,String> allSheetsMap = createAllSheetsMap();
	 
 	/** The Constant logger. */
 	private static final Log logger = LogFactory.getLog(XLSCommon.class);
	 
 	/** The dao. */
 	private static UsersDAO dao = new UsersDAO();
	 

	/**
	 * Gets the sheet.
	 *
	 * @param fname the fname
	 * @param sheetNo the sheet no
	 * @return the sheet
	 * @throws Exception the exception
	 */
	public static XSSFSheet getSheet(String fname, int sheetNo) throws Exception {
         return getWorkBook(fname).getSheetAt(sheetNo);
	}

	/**
	 * Creates the all sheets map.
	 *
	 * @return the map
	 */
	private static Map<String, String> createAllSheetsMap() {
		Map<String,String> m =  new HashMap<String,String>();
		m.put("Cover Page","Cover Page");
		m.put("Contents Page","Contents Page");
		m.put("Section A_Admin Details","Section A_Admin Details");
		m.put("Section B1_Employee Summary","Section B1_Employee Summary");
		m.put("Section B2_Interns Summary","Section B2_Interns Summary");
		m.put("Section B3_LGSETA funded Intern","Section B3_LGSETA funded Intern");
		m.put("Section C_Qualification Profile","Section C_Qualification Profile");
		m.put("Section D1_New Employee Recruit","Section D1_New Employee Recruit");
		m.put("Section D2_Employee Turnover","Section D2_Employee Turnover");
		m.put("Section D3_Scarce Skills","Section D3_Scarce Skills");
		m.put("Section E1-E2_Train Expen","Section E1-E2_Train Expen");
		m.put("Section E3-E5_ATR Summary","Section E3-E5_ATR Summary");
		m.put("Section E6-E9_ATR Intervent","Section E6-E9_ATR Intervent");
		m.put("Section E10_Trained Employees","Section E10_Trained Employees");
		m.put("Section E11_Trained Unemployed","Section E11_Trained Unemployed");
		m.put("E12_Name of Learning Int Employ","E12_Name of Learning Int Employ");
		m.put("E13_Name of Learning Int Unempl","E13_Name of Learning Int Unempl");
		m.put("Section F1_Planned Train Budget","Section F1_Planned Train Budget");
		m.put("Section F2-F4_Skills Dev Summ","Section F2-F4_Skills Dev Summ");
		m.put("Section F5-F8_Planned Tra Int","Section F5-F8_Planned Tra Int");
		m.put("Section F9_Planned Train Empl","Section F9_Planned Train Empl");
		m.put("Section F10 Planned Train Unemp","Section F10 Planned Train Unemp");
		m.put("Section G1-G4_PIVOTAL Summ","Section G1-G4_PIVOTAL Summ");
		m.put("Section G5_PIVOTAL Empl","Section G5_PIVOTAL Empl");
		m.put("Section G6_PIVOTAL Unem","Section G6_PIVOTAL Unem");
		m.put("Section H1-H4_Planned PIVOTAL","Section H1-H4_Planned PIVOTAL");
		m.put("Section H5_Planned PIVOTAL Emp","Section H5_Planned PIVOTAL Emp");
		m.put("Section H6_Planned PIVOTAL Unem","Section H6_Planned PIVOTAL Unem");
		m.put("Section I_General Comments","Section I_General Comments");
		m.put("Section J_Summary","Section J_Summary");
		m.put("Section K_Declaration","Section K_Declaration");
		m.put("Sheet1","Sheet1");
		m.put("Sheet3","Sheet3");
		m.put("Sheet2","Sheet2");
		m.put("Sheet4","Sheet4");
		m.put("Sheet5","Sheet5");
		m.put("Sheet6","Sheet6");
		m.put("Sheet7","Sheet7");
		return m;
	}

	
	/**
	 * Gets the shee new.
	 *
	 * @param fname the fname
	 * @param name the name
	 * @return the shee new
	 * @throws Exception the exception
	 */
	public static XSSFSheet getSheeNew(String fname, String name) throws Exception {
		XSSFWorkbook wb =getWorkBook(fname);
		for (int i =0;  i<wb.getNumberOfSheets(); i++) {
			if (name.trim().equalsIgnoreCase(wb.getSheetName(i).trim())) {
//				System.out.println("File = " + fname + "Sheet Index = " + i);
				return wb.getSheetAt(i);
			}
		}
		logger.info("Sheet \"" + name + "\" not found in Workbook \"" + fname + "\"");
		throw new Exception("Could not find sheet!");
		
	}
	
	/**
	 * Gets the work book new.
	 *
	 * @param fname the fname
	 * @return the work book new
	 * @throws Exception the exception
	 */
	public static HSSFWorkbook getWorkBookNew(String fname)  throws Exception { 
		 FileInputStream file = new FileInputStream(new File(fname));
       return new HSSFWorkbook(file);
	}
	
	/**
	 * Gets the work book.
	 *
	 * @param fname the fname
	 * @return the work book
	 * @throws Exception the exception
	 */
	public static XSSFWorkbook getWorkBook(String fname)  throws Exception { 
		 FileInputStream file = new FileInputStream(new File(fname));
        return new XSSFWorkbook(file);
	}

	
    /**
     * Debug.
     *
     * @param sheet the sheet
     * @throws Exception the exception
     */
    public static void debug(XSSFSheet sheet) throws Exception {
    	 Iterator<Row> rowIterator = sheet.iterator();
    	 while (rowIterator.hasNext()) 
         {
	
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            System.out.print("Row:"+row.getRowNum() + "==>  ");
            while (cellIterator.hasNext()) {
            	Cell cell = cellIterator.next();
            	System.out.print("("+cell.getColumnIndex() +  ")"+cell.toString() + "\t");
            }
            System.out.println("");
         }
    }
    

	/**
	 * Gets the sheet.
	 *
	 * @param fname the fname
	 * @param name the name
	 * @return the sheet
	 * @throws Exception the exception
	 */
	public static XSSFSheet getSheet(String fname, String name) throws Exception {
		XSSFWorkbook wb =getWorkBook(fname);
		for (int i =0;  i<wb.getNumberOfSheets(); i++) {
			if (name.trim().equalsIgnoreCase(wb.getSheetName(i).trim())) {
//				System.out.println("File = " + fname + "Sheet Index = " + i);
				return wb.getSheetAt(i);
			}
		}
		logger.info("Sheet \"" + name + "\" not found in Workbook \"" + fname + "\"");
		throw new Exception("Could not find sheet!");
		
	}
	
	/**
	 * Gets the sheet.
	 *
	 * @param wb the wb
	 * @param name the name
	 * @return the sheet
	 * @throws Exception the exception
	 */
	public static XSSFSheet getSheet(XSSFWorkbook wb, String name) throws Exception {
		for (int i =0;  i<=wb.getNumberOfSheets(); i++) {
			if (name.trim().equalsIgnoreCase(wb.getSheetName(i).trim())) {
				return wb.getSheetAt(i);
			}
		}
		throw new Exception("Could not find sheet!");
		
	}
	
	/**
	 * All sheets.
	 *
	 * @param fname the fname
	 * @return the XSSF sheet
	 * @throws Exception the exception
	 */
	public static XSSFSheet allSheets(String fname) throws Exception {
		XSSFWorkbook wb =getWorkBook(fname);
		
		
		for (int i = 0;  i<=(wb.getNumberOfSheets()-1); i++) {
			
			System.out.println("m.put(\""+wb.getSheetName(i).trim()+"\",\""+wb.getSheetName(i).trim()+"\");");
		}
		return null;
		//throw new Exception("Could not find sheet!");
		
	}
	
	/**
	 * Check sheets.
	 *
	 * @param fname the fname
	 * @throws Exception the exception
	 */
	public static void checkSheets(String fname) throws Exception {
		XSSFWorkbook wb =getWorkBook(fname);
		String otherSheets="";
		boolean error = false;
		for (int i = 0;  i<=(wb.getNumberOfSheets()-1); i++) {
			if (allSheetsMap.containsKey(wb.getSheetName(i).trim())) {}
			else {
				if (sheetNotEmpty(wb,wb.getSheetName(i).trim())) {
				 otherSheets += wb.getSheetName(i).trim() + ";";
				 error=true;
				}
			}
		}
		if (error) throw new Exception(otherSheets);
	}
	
	/**
	 * Sheet not empty.
	 *
	 * @param wb the wb
	 * @param sheetName the sheet name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	private static boolean sheetNotEmpty(XSSFWorkbook wb, String sheetName) throws Exception {
		XSSFSheet sheet  = getSheet(wb,sheetName);
		if (sheet!=null) {
			Iterator<Row> rowIterator = sheet.iterator();
			int i = 0;
	    	 while (rowIterator.hasNext()) 
	         {
		            Row row = rowIterator.next();
	                i++;
	         }
	    	 if (i!=0) return true;
		}
		return false;
	}

	/**
	 * Pre for numeric.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String preForNumeric(String s) {
		if (s==null) return "0";
		s = s.replaceAll("[^0-9]", "");
		return furtherCheck(s);
	}
	
	/**
	 * Further check.
	 *
	 * @param s the s
	 * @return the string
	 */
	private static String furtherCheck(String s) {
		if (s.length()==0) return "0";
		else return s;
	}

//	public static void error(String file, XSSFSheet sheet, String error) {
//		try {
//			dao.create(new Errors((file.substring(file.lastIndexOf('/')+1).trim()),
//								 sheet.getSheetName().trim(),error.trim()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
 * Gets the double.
 *
 * @param cell the cell
 * @return the double
 */
public static double getDouble(Cell cell) {
		if (cell==null) return 0.0d;
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) return cell.getNumericCellValue();
		else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
  	      switch(cell.getCachedFormulaResultType()) {
          case Cell.CELL_TYPE_NUMERIC:
               return cell.getNumericCellValue();
           case Cell.CELL_TYPE_STRING: {
        	  return  Double.valueOf(preForNumeric(cell.getStringCellValue())).doubleValue();
        	  
           }
        	  
      }
		
	 }
		return 0;
	}
	
	/**
	 * Round.
	 *
	 * @param val the val
	 * @return the double
	 */
	public static double round(double val) { 
		return round(val,2);
	}
	
	/**
	 * Round.
	 *
	 * @param val the val
	 * @param scale the scale
	 * @return the double
	 */
	public static double round(double val, int scale) {
		BigDecimal m = BigDecimal.valueOf(val);
		m = m.setScale(scale,BigDecimal.ROUND_HALF_UP);
		return m.doubleValue();
	}
	
	/**
	 * Trim fname.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String trimFname(String s) {
		if (s.lastIndexOf('/') >-1) {
			return s.substring(s.lastIndexOf('/')+1);
		}
		else return s;
	}
	
	/**
	 * Removes the num.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String removeNum(String s) {
		s = s.replaceAll("[0-9]","");
		s = s.replaceAll("-", "");
		return s.trim();
	}

	
}
