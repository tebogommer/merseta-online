package haj.com.saqa.unitstandards;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import haj.com.datatakeon.GenericDAO;
import haj.com.service.JAXBService;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadUnitStandards.
 */
public class LoadUnitStandards {

	/**
	 * Load US.
	 *
	 * @param file
	 *            the file
	 */
	public static void loadUS(String file) {
		try {

			GenericDAO dao = new GenericDAO();
			String xml = new String(Files.readAllBytes(new File("/Users/wesleypirie/Downloads/Wesley/down/" + file).toPath()), Charset.forName("UTF-8"));
			xml = xml.replaceAll("[\u0000-\u001f]", "");

			SAQAUNITSTANDARDS q = (SAQAUNITSTANDARDS) JAXBService.unmarshalltoObject(xml, new SAQAUNITSTANDARDS());
			List<UNITSTANDARD> l = q.getUNITSTANDARD();
			for (UNITSTANDARD us : l) {
				if (dao.findID(us.unitstandardid).size() == 0) {
					dao.create(us);
					List<SPECIFICOUTCOME> l2 = us.getSPECIFICOUTCOME();
					for (SPECIFICOUTCOME specificoutcome : l2) {
						specificoutcome.setUnitstandard(us);
						dao.create(specificoutcome);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void loadAll() {
		File inputFile = new File("/Users/wesleypirie/Downloads/Wesley/down/");
		for (File file : inputFile.listFiles()) {
			if (file.isDirectory()) {
				if (Integer.parseInt(file.getName()) < 13) {
					loadUS("/" + file.getName() + "/UNITSTANDARD.xml");
				}
			}
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println("START");
		// loadUS("NQF1/UNITSTANDARD.xml");
		// loadUS("Level TBA Pre-2009 was L4/UNITSTANDARD.xml");
		// loadUS("Level TBA Pre-2009 was L5/UNITSTANDARD.xml");
		// loadUS("Level TBA Pre-2009 was L6/UNITSTANDARD.xml");
		// loadUS("Level TBA Pre-2009 was L7/UNITSTANDARD.xml");
		// loadUS("Level TBAPre-2009 BelowL1/UNITSTANDARD.xml");
		// loadUS("Level TBAPre-2009 was L8+/UNITSTANDARD.xml");
		// loadUS("NQF8/UNITSTANDARD.xml");
		// loadUS("NQF9/UNITSTANDARD.xml");
		// loadUS("NQF10/UNITSTANDARD.xml");

		loadAll();

		System.out.println("DONE");
		System.exit(0);
	}

}
