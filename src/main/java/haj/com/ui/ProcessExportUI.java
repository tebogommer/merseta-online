package haj.com.ui;

import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;

@ManagedBean(name = "processexportUI")
@ViewScoped
public class ProcessExportUI extends AbstractUI {

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {

	}

	public void postProcessXLS(Object document) {
		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
		// XSSFRow header = sheet.getRow(0);

		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
//		IndexedColors.DARK_BLUE.getIndex()
		font.setColor(new XSSFColor(new Color(204, 133, 31)));
		
		font.setBold(true);
		font.setItalic(false);

		XSSFFont redFont = wb.createFont();
		redFont.setFontHeightInPoints((short) 14);
		redFont.setColor(new XSSFColor(new Color(125, 112, 8)));
		redFont.setBold(true);
		redFont.setItalic(false);
		
		XSSFFont defaultFont = wb.createFont();
		defaultFont.setFontHeightInPoints((short) 10);
		defaultFont.setColor(new XSSFColor(new Color(238, 49, 36)));
		defaultFont.setBold(false);
		defaultFont.setItalic(false);

		XSSFCellStyle cellStyle = wb.createCellStyle();

		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getStringCellValue().contains("#BOLD#")) {
					cellStyle = wb.createCellStyle();
					cellStyle.setFont(font);
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					String cellVal = cell.getStringCellValue().replaceAll("#BOLD#", "");
					cell.setCellValue(cellVal);
					cell.setCellStyle(cellStyle);
				} else if (cell.getStringCellValue().contains("#BOLD_RED#")) {
					cellStyle = wb.createCellStyle();
					cellStyle.setFont(redFont);
					cellStyle.setBorderBottom(BorderStyle.MEDIUM);
					String cellVal = cell.getStringCellValue().replaceAll("#BOLD_RED#", "");
					cell.setCellValue(cellVal);
					cell.setCellStyle(cellStyle);
				} else {
					cellStyle = wb.createCellStyle();
					cellStyle.setFont(defaultFont);
					cell.setCellStyle(cellStyle);
				}
			}
		}
		autoSizeColumns(wb);
	}

	private void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(sheet.getFirstRowNum());
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {

	}

}
