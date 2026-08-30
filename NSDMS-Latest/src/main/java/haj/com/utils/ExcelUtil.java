/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVUtil.
 */
public class ExcelUtil {

	public static void main(String[] args) throws Exception {
		streamWorkbook();
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private static void streamWorkbook() throws Exception {

		InputStream is = new FileInputStream(new File("/Users/wesleypirie/Desktop/Files/Nameng_Inc/test.xlsx"));
		BufferedWriter my_csv = new BufferedWriter(new FileWriter("/Users/wesleypirie/Desktop/Files/convertedCSVFile.csv"));
		Workbook workbook = StreamingReader
							.builder()
							.rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
							.bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
							.open(is); // InputStream or File for XLSX file (required)

		
		
		for (Sheet sheet : workbook) {
			System.out.println(sheet.getSheetName());
			int i = 0;
			for (Row r : sheet) {
				if (i > 6) {
					for (Cell cell : r) {
						
						DataFormatter formatter = new DataFormatter();
						String cellValue = formatter.formatCellValue(cell);
						if (cellValue.contains(",")) cellValue = cellValue.replaceAll(",", "");
						my_csv.write(cellValue.trim());
						my_csv.write(";");
					}
					my_csv.newLine();
				}
				i++;
			}
		}

		my_csv.flush();
		my_csv.close();
	}

	
	private void fixHeaders() {
		// TODO Auto-generated method stub

	}
	
	public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
		return asStream(sourceIterator, false);
	}

	public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
		Iterable<T> iterable = () -> sourceIterator;
		return StreamSupport.stream(iterable.spliterator(), parallel);
	}

}
