package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import haj.com.bean.SchemeYearBean;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

public class SchemeYearService extends AbstractService {
	
    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public final static String SCHEME_YEAR_START = "-04-01";  

/*
   SARS scheme year = 1 April to 31 March (but reflects the starting year as the scheme year e.g. 1 April 2016 to 31 March 2017 = 2016 scheme year
   Merseta scheme year = 1 April to 31 March (but reflects the ending year as the scheme year e.g. 1 April 2016 to 31 March 2017 = 2017 scheme year
 */

	public List<Date> schemeYearDatesSARS(Integer schemeYear) throws Exception {
		List<Date> l = new ArrayList<Date>();
		Date start = sdf.parse(schemeYear+SCHEME_YEAR_START);
		l.add(start);
		for (int i = 0; i < 11; i++) {
			l.add(GenericUtility.addMonthsToDate(l.get(i), 1)) ;
			
		}
		return l;
	}
	
	public List<Date> schemeYearDatesSETA(Integer schemeYear) throws Exception { 
		return schemeYearDatesSARS(schemeYear-1);
	}
	
	public SchemeYearBean sarsSchemeYear(Integer schemeYear) throws Exception  {
		SchemeYearBean syb = new SchemeYearBean();
		List<Date> l = schemeYearDatesSARS(schemeYear);
		syb.setSchemeYear(schemeYear);
		syb.setStartDate(l.get(0));
		syb.setEndDate(GenericUtility.getLasttDayOfMonth(l.get(11)));
		return syb;
	}
	
	
	public SchemeYearBean setaSchemeYear(Integer schemeYear) throws Exception  { 
		return sarsSchemeYear(schemeYear-1);
	}
	
	 public static void main( String[] args ) {
		 try {
			 SchemeYearService s = new SchemeYearService();
			 SchemeYearBean syb = s.setaSchemeYear(2019);
//			 System.out.println(syb.toString());
			 
			 identifySchemeDatesByDateSelected();
		/*	 List<Date> l = s.schemeYearDatesSETA(2017);
			 l.forEach(a-> {
				 System.out.println(s.sdf.format(a));
			 });
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }

	private static void identifySchemeDatesByDateSelected() throws Exception {
		SchemeYearService sTwo = new SchemeYearService();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
		SimpleDateFormat dayAndMonthDateFormat = new SimpleDateFormat("dd MM");
		SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");
		boolean passed = false;
		
		Date dateSelected = GenericUtility.getStartOfDay(sdf.parse("01 03 2017")); // selected date
		Date calculatedDate = null; // caluclated Date for Fin Year
		
		String dateAndMonth = dayAndMonthDateFormat.format(dateSelected);
		Integer year = Integer.parseInt(yearDateFormat.format(dateSelected));
		SchemeYearBean syb = sTwo.setaSchemeYear(year);
		calculatedDate = sdf.parse(dateAndMonth + " " + syb.getSchemeYear().toString());
		System.out.println("Fin Year: " + syb.getSchemeYear());
		System.out.println("Start Date: " + sdf.format(syb.getStartDate()));
		System.out.println("End Date: " + sdf.format(syb.getEndDate()));
		System.out.println("Calculated Date: " + sdf.format(calculatedDate));
		
		// Guaranteed passes 1
		if (calculatedDate.equals(syb.getStartDate()) || calculatedDate.equals(syb.getEndDate())) {
			System.out.println("In Fin Year: Equals Passed");
			passed = true;
		}
		System.out.println(calculatedDate.after(syb.getStartDate()));
		
		// Guaranteed passes 2
		if (calculatedDate.after(syb.getStartDate()) && calculatedDate.before(syb.getEndDate())) {
			System.out.println("In Fin Year: Between Fin Year Dates");
			passed = true;
		}	
		
		if (!passed) {
			System.out.println("Did Not Pass");
		}
	}
}
