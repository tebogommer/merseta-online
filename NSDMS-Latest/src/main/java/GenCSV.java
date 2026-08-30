import java.util.Date;

import haj.com.service.DataExtractService;

public class GenCSV {

	public static void main(String[] args) {
/*		List<EmployeesImport> employees = new ArrayList<>();
		employees.add(new EmployeesImport());
		System.out.println(haj.com.utils.CSVUtil.writeCSV(employees, ";"));
*/
/*		List<EmployeesImportTraining> employees = new ArrayList<EmployeesImportTraining>();
		employees.add(new EmployeesImportTraining());
		System.out.println(haj.com.utils.CSVUtil.writeCSV(employees, ";"));
*/

		/*

		List<TS2> mandatoryGrantCSVs = new ArrayList<TS2>();
		mandatoryGrantCSVs.add(new TS2());

		System.out.println(haj.com.utils.CSVUtil.writeCSV(mandatoryGrantCSVs, ";"));
*/



	//	System.out.println(haj.com.utils.CSVUtil.writeFixedLength(tempBeans));

        try {
        	DataExtractService s =new DataExtractService();
        	s.extractQCTO("17", "Test@a.com", new Date(), new Date());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
