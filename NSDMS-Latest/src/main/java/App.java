import java.io.File;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

public class App {

	public static void main(String[] args) {
		System.out.println("Start");
		try {

			byte[] b = null;

			// Loading an existing PDF document
			File file1 = new File("/Users/hendrik/Desktop/p1.pdf");
			PDDocument doc1 = PDDocument.load(file1);

			File file2 = new File("/Users/hendrik/Desktop/p2.pdf");
			PDDocument doc2 = PDDocument.load(file2);

			// Instantiating PDFMergerUtility class
			PDFMergerUtility PDFmerger = new PDFMergerUtility();

			// Setting the destination file
			PDFmerger.setDestinationFileName("/Users/hendrik/Desktop/merged.pdf");

			// ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// PDFmerger.setDestinationStream(bos);
			// PDFmerger.addSource(new ByteArrayInputStream(b));
			// adding the source files
			PDFmerger.addSource(file1);
			PDFmerger.addSource(file2);

			// Merging the two documents
			PDFmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

			System.out.println("Documents merged");
			// Closing the documents
			doc1.close();
			doc2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("end");
		System.exit(0);
	}

}