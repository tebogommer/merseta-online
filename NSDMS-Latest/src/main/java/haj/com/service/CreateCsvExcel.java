package haj.com.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import haj.com.annotations.TechFiniumAnnotation;
import haj.com.bean.ExcelSheetBean;
import haj.com.constants.HAJConstants;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;



/**
 * Write CSV or Excel files based on List of Beans
 * 
 * @author Hendrik
 * @version 1.0
 */
//@SuppressWarnings("deprecation")
public class CreateCsvExcel extends AbstractService {

	protected static final Log logger = LogFactory.getLog(CreateCsvExcel.class);
	//private static final String path = HAJConstants.DOC_PATH;

	/**
	 * Supply a list of a simple POJO with @MraHeading annotations and a CSV
	 * file will be created
	 * 
	 * @param list
	 * @param fileName
	 *            name of file to be created
	 * @throws Exception
	 */
	public static void writeCsv(List<?> list, String fileName) throws Exception {
	
		 Map<String,Map<String,String>> headings = TechFiniumAnnotation.processBean(list);
		  String output = "";
		  
			for (Entry<String, Map<String, String>> e : headings.entrySet()) {
				Map<String, String> heading = e.getValue();
				output += heading.get("heading") + ",";
			}

		output = GenericUtility.finishLine(output);
		for (Object object : list) {
			Map<String, Object> values = TechFiniumAnnotation.getFieldValues(object,headings);
			output = processLine(output, headings, values);
		}
		//FileUtils.writeStringToFile(new File(path + fileName + ".csv"), output);
	
	}
	
	public static byte[] writeCsv(List<?> list) throws Exception {
		
		 Map<String,Map<String,String>> headings = TechFiniumAnnotation.processBean(list);
		  String output = "";
		  
			for (Entry<String, Map<String, String>> e : headings.entrySet()) {
				Map<String, String> heading = e.getValue();
				output += heading.get("heading") + ",";
			}

		output = GenericUtility.finishLine(output);
		for (Object object : list) {
			Map<String, Object> values = TechFiniumAnnotation.getFieldValues(object,headings);
			output = processLine(output, headings, values);
		}
		
	//	return output.getBytes(Charset.forName("UTF-8"));
		return output.getBytes();
	} 

	/**
	 * 
	 * @param output
	 *            resulting line
	 * @param headings
	 *            headings map
	 * @param values
	 *            values map
	 * @return
	 */
	private static String processLine(String output,
			Map<String, Map<String, String>> headings, Map<String, Object> values) {
		String t = "";
		
		for (Entry<String, Map<String, String>> e : headings.entrySet()) {		
			if (values.get(e.getKey()) instanceof String) {
				t += GenericUtility.removeSpecialChars((String) values.get(e
						.getKey())) + ",";
			}
			else {
				t += convertToString(values.get(e.getKey())) + ",";
			}
		
		}
		return output + GenericUtility.finishLine(t);
	}

	/**
	 * Supply a list of a simple POJO with @MraHeading annotations and a Excel
	 * file will be created
	 * 
	 * @param list
	 * @param sheetName
	 *            to be created
	 * @param fileName
	 *            to be created
	 * @throws Exception
	 */
	/*public static void writeExcel(List<?> list, String sheetName,
			String fileName) throws Exception {
		writeExcel(list, sheetName, fileName, null);
	}*/

	/**
	 * Supply a list of a simple POJO with @MraHeading annotations and a Excel
	 * file will be created with a heading
	 * 
	 * @param list
	 * @param sheetName
	 *            to be created
	 * @param fileName
	 *            to be created
	 * @param headerName
	 *            in file
	 * @throws Exception
	 */
	// @SuppressWarnings("deprecation")
	/*public static void writeExcel(List<?> list, String sheetName,
			String fileName, String headerName) throws Exception {
		Workbook workbook = writeExcelWorkbookCommon(list, sheetName, headerName);
		// Create the file
		FileOutputStream outputStream = new FileOutputStream(path + fileName
				+ ".xlsx");
		workbook.write(outputStream);
		outputStream.close();

	}*/

	/**
	 * Supply a list of a simple POJO with @MraHeading annotations 
	 * or supply a List of ExcelSheetBean for multiple pages 
	 * and a Excel file will be created 
	 * 
	 * @param list or List of ExcelSheetBean
	 * @return byte Array of Excel
	 * @throws Exception
	 */
	public static byte[] createExcel(List<?> list)
			throws Exception {
		if (list==null || list.size()==0)
		{
			return null;
		} else
		{
			if (list.get(0) instanceof ExcelSheetBean)
			{
				Workbook workbook = writeExcelWorkbookCommon((List<ExcelSheetBean>) list);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				workbook.write(os);
				os.close();

				return os.toByteArray();
			} else
				return createExcel(list, "Sheet1", null);
		}
	}
	
	/**
	 * Supply a list of a simple POJO with @MraHeading annotations and a Excel
	 * file will be created
	 * 
	 * @param list
	 * @param sheetName
	 *            to be created
	 * @return byte Array of Excel
	 * @throws Exception
	 */
	public static byte[] createExcel(List<?> list, String sheetName)
			throws Exception {
		return createExcel(list, sheetName, null);
	}

	/**
	 * Supply a list of a simple POJO with @MraHeading annotations and a Excel
	 * file will be created with a heading
	 * 
	 * @param list
	 * @param sheetName
	 *            to be created
	 * @param headerName
	 *            in file
	 * @return byte Array of Excel
	 * @throws Exception
	 */
	public static byte[] createExcel(List<?> list, String sheetName,
			String headerName) throws Exception {
		List<ExcelSheetBean> excelSheets = new ArrayList<ExcelSheetBean>();
		excelSheets.add(new ExcelSheetBean(list,sheetName,headerName));
		Workbook workbook = writeExcelWorkbookCommon(excelSheets);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		os.close();

		return os.toByteArray();
	}
		
	/*
	 * Common routine to create a Excel Workbook (file)
	 */
	private static Workbook writeExcelWorkbookCommon(List<ExcelSheetBean> excelSheets) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		for (ExcelSheetBean excelSheet : excelSheets) {
			writeExcelSheetCommon(workbook, excelSheet.getSheetList(), excelSheet.getSheetName(),excelSheet.getSheetHeaderName());
		}
		
		return workbook;
		
	}

		
	/*
	 * Common routine to create Excel Sheet
	 */
	private static void writeExcelSheetCommon(Workbook workbook ,List<?> list, String sheetName,
		String headerName) throws Exception {
		Sheet sheet = workbook.createSheet(sheetName);
			
		// Set Print Header & Footer
		Footer footer = sheet.getFooter();
		Header header = sheet.getHeader();
		header.setCenter(sheetName);
		header.setRight(HeaderFooter.date());
	//	footer.setLeft("Momentum Retirement Administrators");
		footer.setRight("Page " + HeaderFooter.page() + " of "
				+ HeaderFooter.numPages());

		// Set font for Title & Column Headers
		Font boldFont = workbook.createFont();
		Font headerFont = workbook.createFont();
		CellStyle boldStyle = workbook.createCellStyle();
		CellStyle titleStyle = workbook.createCellStyle();
		//boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);
		headerFont.setFontHeightInPoints((short) 14);
		titleStyle.setFont(headerFont);

		// Write Title
		int rowCount = 0;
		if (headerName != null) { // This is to cater for the other version of
									// the method
			Row headerRow = sheet.createRow(0);
			Cell headerCell = headerRow.createCell(0);
			headerCell.setCellValue(headerName);
			headerCell.setCellStyle(titleStyle);
			// Merge cells for heading
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0,
					10));
			rowCount = 2;
		}

		// Write Column Header
		Row row = sheet.createRow(rowCount);
		Map<String, Map<String, String>> headings = TechFiniumAnnotation.processBean(list);
		// System.out.println();
		int columnCount = 0;
		for (Entry<String, Map<String, String>> e : headings.entrySet()) {
			Map<String, String> heading = e.getValue();
			// Only print columns that is not suppressed	
			if (!"true".equals(heading.get("suppress"))) {
				Cell cell = row.createCell(columnCount);
				// if (heading.containsKey("heading")
				cell.setCellValue(heading.get("heading"));
				// Setting the column headers bold
				cell.setCellStyle(boldStyle);
				//sheet.autoSizeColumn(columnCount);
				// Set Freeze Pane for header
				sheet.createFreezePane(0, rowCount + 1, 0, rowCount + 1);
				columnCount++;
			}
		}

		// Set Print Column Header to repeat on every printed page
		sheet.setRepeatingRows(CellRangeAddress.valueOf("1:" + (rowCount + 1)));
		
		// Process the data list lines
		int rowFirst = rowCount +1;
		// Store Column Format for Formula Row
		Map<String, Object> valuesFormula = null;
		CellStyle cellStyle = workbook.createCellStyle();
		boldFont.setBold(false);
		cellStyle.setFont(boldFont);
		for (Object object : list) {
			Map<String, Object> values = TechFiniumAnnotation.getFieldValues(object,headings);
			rowCount++;
			processLine(rowCount, sheet, headings, values, workbook,cellStyle);
			if (valuesFormula == null)
			    valuesFormula = values;
		}
		// Write Column Formulas
		//System.out.println("Write Column Formulas");
		columnCount = 0;
		rowCount++;
		boldFont.setBold(true);
		// Format Totals
		//CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(boldFont);
		
		//CreationHelper createHelper = workbook.getCreationHelper();
		Row rowTotal = sheet.createRow(rowCount);
		// Loop through Column Headings 
		for (Entry<String, Map<String, String>> t : headings.entrySet()) {
			Map<String, String> heading = t.getValue();
			// Only print columns that is not suppressed
			if (!"true".equals(heading.get("suppress"))) {
				Cell cellFormula = rowTotal.createCell(columnCount);
				CellReference cellFormulaRangeFrom = new CellReference(rowFirst,columnCount);
				CellReference cellFormulaRangeTo = new CellReference(rowCount-1,columnCount);
				if (heading.containsKey("formula")) {
					String cellFormulaReference = heading.get("formula") + "('"
							+ sheetName + "'!"+cellFormulaRangeFrom.formatAsString()+":"+cellFormulaRangeTo.formatAsString()+")";
					formatExcelColumn(cellFormula, valuesFormula.get(t.getKey()), workbook, false,cellStyle);
					cellFormula.setCellFormula(cellFormulaReference);
				}
				sheet.autoSizeColumn(columnCount);
				columnCount++;
			}
		}

	}

	/**
	 * Write Excel data
	 * 
	 * @param rowCount
	 *            Starting row to write
	 * @param sheet
	 *            to write to
	 * @param headings
	 * @param values
	 */

	private static void processLine(int rowCount, Sheet sheet,
			Map<String, Map<String, String>> headings,
			Map<String, Object> values, Workbook wb,CellStyle cellStyle) {
		Row row = sheet.createRow(rowCount);
		int columnCount = 0;
		
		for (Entry<String, Map<String, String>> e : headings.entrySet()) {
			Map<String, String> heading = e.getValue();
			// Only print columns that is not suppressed
			if (!"true".equals(heading.get("suppress"))) {
				Cell cell = row.createCell(columnCount);
				//logger.info(e.getKey() + " " + values.get(e.getKey()));
				if ("true".equals(heading.get("textwrap"))) {
					formatExcelColumn(cell, values.get(e.getKey()), wb, true,cellStyle);
				} else {
					formatExcelColumn(cell, values.get(e.getKey()), wb, false,cellStyle);
				}
				columnCount++;
			}
		}

	}

	/*
	 * Format the cell to correct types
	 */

	private static void formatExcelColumn(Cell cell, Object value, Workbook wb, boolean wrap,CellStyle cellStyle) {
		
		if (value == null) {
			cell.setCellType(CellType.BLANK);
	    // String
		} else if (value instanceof String) {
		//	CellStyle cellStyle = wb.createCellStyle();
		//	CreationHelper createHelper = wb.getCreationHelper();
			if (wrap == true) {
				cellStyle.setWrapText(true);
			}
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(value.toString());
		// BigDecimal
		} else if (value instanceof BigDecimal) {
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(((BigDecimal) value).doubleValue());
			//CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
					"# ##0.00####;[Red]-# ##0.00####"));
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
		// BigInteger
		} else if (value instanceof BigInteger) {
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(((BigInteger) value).intValue());
		// Boolean
		} else if (value instanceof Boolean) {
			cell.setCellType(CellType.BOOLEAN);
			cell.setCellValue(((Boolean) value).booleanValue());
		// Character
		} else if (value instanceof Character) {
		//	CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(value.toString());
		// Date
		} else if (value instanceof Date) {
			cell.setCellValue((Date) value);
		//	CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
					"yyyy.mm.dd"));
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
		// Double
		} else if (value instanceof Double) {
		//	CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(((Double) value).doubleValue());
		}
		// Float
		else if (value instanceof Float) {
			//CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(((Float) value).doubleValue());
		}
		// Integer
		else if (value instanceof Integer) {
			//CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(((Integer) value).intValue());
		}
		// Locale
		else if (value instanceof Locale) {
			//CellStyle cellStyle = wb.createCellStyle();
			// createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(((Locale) value).toString());
		}
		// Long
		else if (value instanceof Long) {
			//CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(((Long) value).longValue());
		}
		// Short
		else if (value instanceof Short) {
			//CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(((Short) value).shortValue());
		}
		else {
			//CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("" + value);
		}

	}


	
	 private static String convertToString(Object value)
	  {
		String ret = null; 
	    if (value == null)
	     ret = "";
	    else if (value instanceof BigDecimal)
	      ret =  ((BigDecimal) value).toString();
	    else if (value instanceof BigInteger)
	    	ret =  ((BigInteger) value).toString();
	 //   else if (value instanceof byte[])
	 //     query.setBinary(key, (byte[]) value);
	    else if (value instanceof Boolean)
	    	ret =  ( (Boolean) value).toString();
	 //   else if (value instanceof Byte)
	 //     query.setByte(key, (Byte) value);
	  //  else if (value instanceof Calendar)
	 //     query.setCalendarDate(key, (Calendar) value);
	    else if (value instanceof Character)
	    	ret =  ((Character) value).toString();
	    else if (value instanceof Date) {
	    	ret = HAJConstants.sdf.format((Date) value);
	    	//ret =  ((Date) value).toString();
	    }
	    else if (value instanceof Double)
	    	ret =  ""+((Double) value).doubleValue();
	    else if (value instanceof Float)
	    	ret =  ""+((Float) value).floatValue();
	    else if (value instanceof Integer)
	    	ret =  ""+((Integer) value).intValue();
	    else if (value instanceof Locale)
	    	ret =  ((Locale) value).toString();
	    else if (value instanceof Long)
	    	ret =  ""+((Long) value).longValue();
	    else if (value instanceof Short)
	    	ret =  ""+((Short) value).shortValue();
	    else if (value instanceof String)
	    	ret =  ( (String) value);
	    else
	      ret = "";
	   return ret;
	  }
	 
	 
}
