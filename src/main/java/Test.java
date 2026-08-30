import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import haj.com.dao.reports.SarsReportsDAO;
import haj.com.entity.Company;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.sars.SarsEmployerDetail;
import haj.com.service.MailDef;
import haj.com.utils.GenericUtility;
import haj.com.utils.Initialize;

public class Test {

	public void log(Object... tags) {
		if (tags != null && tags.length > 0)
			if (tags[0] instanceof MailEnum) {
				System.out.println(tags[0]);
			}
		for (int i = 2; i < tags.length; i += 2)
			if (tags[i - 1] != null)
				System.out.println(tags[i - 1].toString() + "\t" + tags[i]);
	}

	public void test(MailDef d) {
		System.out.println(d.getMailEnum().getFriendlyName());
		for (Entry<MailTagsEnum, Object> e : d.getTagMap().entrySet()) {
			System.out.println(e.getKey().getTagName() + "\t" + e.getValue().getClass().getName());
		}
	}

	public static void main(String[] args) {
		 System.out.println("Start");
		try {
			/*
			SarsEmployerDetailService service =  new SarsEmployerDetailService();
			service.populateNewFromSARS(182l);
			*/
			// printTree(0, new File("/Users/hendrik/Documents/bitbucket/mersetaweb/MerSETA/src/main/webapp"));
/*			Files.newDirectoryStream(Paths.get("/Users/hendrik/Documents/bitbucket/mersetaweb/MerSETA/src/main/webapp"),
			        path -> path.toString().endsWith(".java"))
			        .forEach(System.out::println);
*/
			/*			    Path source = Paths.get("/Users/hendrik/Documents/bitbucket/mersetaweb/MerSETA/src/main/webapp");
        Files.walk(source).filter(Files::isRegularFile).forEach(
	        		//System.out::println
	        		 a->{

	        			if  (a.getFileName().toString().endsWith(".xhtml")) {
	        				System.out.println(a);
	        			}


	        		 }
	        		);
*/

//		    List<String> l = GenericUtility.allFilesRecursivelyWithExtension("/Users/hendrik/Documents/bitbucket/mersetaweb/MerSETA/src/main/webapp", "xhtml");
//		    l.forEach(System.out::println);

			int x = 	new SarsReportsDAO().noLeviesForLnumber("L380791816");
			System.out.println(x);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		System.exit(0);
	}

	private static void checkBarCode() {
		try {
			// Interleaved2Of5Bean bean = new Interleaved2Of5Bean();
			Code39Bean bean = new Code39Bean();
			bean.setHeight(10d);
			bean.doQuietZone(false);
			OutputStream out = new java.io.FileOutputStream(new File("output.png"));
			BitmapCanvasProvider provider = new BitmapCanvasProvider(out, "image/x-png", 110, BufferedImage.TYPE_BYTE_GRAY, false, 0);
			bean.generateBarcode(provider, "0ec61fc4-4a6e-4902-828e-4456b738d812");
			provider.finish();
			BufferedImage barcodeImage = provider.getBufferedImage();
			File file = new File("/Users/wesley/myfile.png");
			OutputStream outputStream = new FileOutputStream(file);
			// Write your data
			ImageIO.write(barcodeImage, "png", outputStream);
			outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// outputStream.close();

	}

	public boolean compareSarsEmployerToCustomer(SarsEmployerDetail sarsEmployerDetail, Company company) {
		 Initialize.initStringsIfNull(sarsEmployerDetail);
		 Initialize.initStringsIfNull(company);
		return (sarsEmployerDetail.getRegisteredNameOfEntity().trim().toUpperCase()+sarsEmployerDetail.getTradingName().trim().toUpperCase()).hashCode() == (company.getCompanyName().trim().toUpperCase()+company.getTradingName().trim().toUpperCase()).hashCode();
	}



	public List<String> scanFolder() throws Exception {
		List<String> l = new ArrayList<String>();
		 Files.list(Paths.get(""))
	        .filter(Files::isRegularFile)
	        .forEach(a-> {
	        	    l.add(a.toString());
	        });
		return l;
	}



	static void printTree(int depth, File file) throws IOException {
	    StringBuilder indent = new StringBuilder();
	    String name = file.getName();

	    for (int i = 0; i < depth; i++) {
	        indent.append(".");
	    }

	    //Pretty print for directories
	    if (file.isDirectory()) {
	        System.out.println(indent.toString() + "|");
	        if(isPrintName(name)){
	            System.out.println(indent.toString() + "*" + file.getName() + "*");
	        }
	    }
	    //Print file name
	    else if(isPrintName(name)) {
	        System.out.println(indent.toString() + file.getName());
	    }
	    //Recurse children
	    if (file.isDirectory()) {
	        File[] files = file.listFiles();
	        for (int i = 0; i < files.length; i++){
	            printTree(depth + 4, files[i]);
	        }
	    }
	}

	//Exclude some file names
	static boolean isPrintName(String name){
	    if (name.charAt(0) == '.') {
	        return false;
	    }
	    if (name.contains("svn")) {
	        return false;
	    }
	    //.
	    //. Some more exclusions
	    //.
	    return true;
	}
}
