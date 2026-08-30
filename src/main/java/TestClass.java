import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import haj.com.saqa.qualifications.QUALIFICATION;
import haj.com.saqa.qualifications.USQUALIFICATIONLINK;
import haj.com.service.JAXBService;

public class TestClass {
	public static void main(String[] args) {
		/*System.out.println(ConfigDocProcessEnum.COMPANY.getRedirectPage());
		System.out.println(ConfigDocProcessEnum.COMPANY.getRegistrationName());
		System.out.println(ConfigDocProcessEnum.COMPANY.getFriendlyName());
		*/
//		String x = " ";
/*		List<EmployeesImport> employees = new ArrayList<>();
		employees.add(new EmployeesImport());
		System.out.println(haj.com.utils.CSVUtil.writeCSV(employees, ","));
*/		
	
		//read file into stream, try-with-resources
		try  {
			System.out.println("START");
            //GenericDAO dao = new GenericDAO();
			String xml = new String (Files.readAllBytes(new File("/Users/hendrik/Downloads/QUALIFICATION/NQF1/QUALIFICATION.xml").toPath()),Charset.forName("UTF-8")); 
//			 SAXBuilder builder = new SAXBuilder();
//			Document document = (Document) builder.build(new File("/Users/hendrik/Downloads/QUALIFICATION/NQF1/QUALIFICATION.xml"));
//			Element rootNode = document.getRootElement();
//			List<Element> list = rootNode.getChildren("US_QUALIFICATION_LINK");
//			for (Element node : list) {
//				 System.out.println("First UNIT_STANDARD_ID : " + node.getChildText("UNIT_STANDARD_ID"));
//			}
		
			haj.com.saqa.qualifications.SAQAQUALIFICATIONS q = (haj.com.saqa.qualifications.SAQAQUALIFICATIONS )JAXBService.unmarshalltoObject(xml, new haj.com.saqa.qualifications.SAQAQUALIFICATIONS());
			List<QUALIFICATION> l =  q.getQUALIFICATION();
			for (QUALIFICATION ll : l) {
				System.out.println(ll.toString());
				 List<USQUALIFICATIONLINK>  l2 = ll.getUSQUALIFICATIONLINK();
				 for (USQUALIFICATIONLINK usqualificationlink : l2) {
					System.out.println(usqualificationlink.toString());
				}
				break;
			}
			
		//	List<QUALIFICATION> l = q.getQUALIFICATION();
		//	 for (QUALIFICATION qual : l) {
				// System.out.println(qual.getQualificationid() + "\t"+  qual.getQualificationtypedesc() + "\t"+qual.getEtqaacronym() + " \t"+qual.getEtqaname() );
				// dao.create(qual);
				// break;
		//	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);
	}
}