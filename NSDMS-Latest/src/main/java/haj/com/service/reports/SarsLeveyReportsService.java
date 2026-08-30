package haj.com.service.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import haj.com.bean.ExcelSheetBean;
import haj.com.bean.SarsLevyDeviation;
import haj.com.bean.SarsLevyRecon;
import haj.com.constants.HAJConstants;
import haj.com.dao.reports.SarsReportsDAO;
import haj.com.framework.AbstractService;
import haj.com.service.CreateCsvExcel;

public class SarsLeveyReportsService extends AbstractService {

	private SarsReportsDAO dao = new SarsReportsDAO();
	private static double thresholdStatus = 333.33;



	public List<SarsLevyRecon> levyDeviation( int first, int pageSize, Map<String, Object> filters) throws Exception {

		return doLevyStatus(fixSign( ( List<SarsLevyRecon>)dao.levyDeviation(first,pageSize,processFilter(filters))));
	}


	private List<SarsLevyRecon> fixSign(List<SarsLevyRecon> levyDeviation) {
		levyDeviation.forEach(a->{
			fixSign(a);
		});
		return levyDeviation;
	}

	private void fixSign(SarsLevyRecon a) {
		a.setLatest_levy(a.getLatest_levy().doubleValue()<0?a.getLatest_levy().doubleValue()*-1:a.getLatest_levy());
		a.setAverage_levy(a.getAverage_levy().doubleValue()<0?a.getAverage_levy().doubleValue()*-1:a.getAverage_levy());
	}

	private String processFilter(Map<String, Object> filters) {
		String sdlnumber = "%";
		if (filters!=null && filters.containsKey("sdlnumber")) {
			sdlnumber = (filters.get("sdlnumber")).toString().trim()+"%";
		}
		return sdlnumber;
	}

	public Long levyDeviationCount(Map<String, Object> filters) throws Exception {
		return dao.levyDeviationCount(processFilter(filters));
	}

	public Date levyDeviationBaseDate() throws Exception {
		return dao.levyDeviationBaseDate();
	}

	public List<SarsLevyRecon> levyDeviation() throws Exception {
		return dao.levyDeviation();
	}

	public byte[] levyDeviationExcel(Date forMonth) throws Exception {
		 List<SarsLevyDeviation> l =  new ArrayList<SarsLevyDeviation>();
		 List<SarsLevyRecon> l2 = dao.levyDeviation();
		 l2 = doLevyStatus(l2);
		 String status = "";
		 for (SarsLevyRecon s : l2) {
			switch (s.getLevyStatus()) {
			case 1:
				status = "Normal";
				break;
			case 2:
				status = "Inconsistent";
				break;
			case 3:
				status = "Below Threshold";
				break;
			default:
				status = "";
				break;
			}
			l.add(new SarsLevyDeviation(s.getSdlnumber(),s.getLatest_levy()*-1,s.getAverage_levy()*-1,s.getDeviation_amount(),s.getDeviation_percentage(),status));
		 }
		 List<ExcelSheetBean> excelSheets = new ArrayList<ExcelSheetBean>();
		 excelSheets.add(new ExcelSheetBean(l,"Levy Deviation Report", "Levy Deviation Report for "+HAJConstants.sddfMMMMyyyy.format(forMonth)));
		 return CreateCsvExcel.createExcel(excelSheets);
	}


	public int noLeviesForLnumber(String refNo) throws Exception {
		return dao.noLeviesForLnumber(refNo);
	}



	private List<SarsLevyRecon> doLevyStatus(List<SarsLevyRecon> list) throws Exception {
		/*
1. Normal status refers to last 12 levy payments, irrespective of scheme year. So if an employer has made 12 payments at that month, then would be consistent.

2. Inconsistent status refers to last 12 levy payments, irrespective of scheme year. So if an employer has below 12 payments at that month, then would be inconsistent.

3. Below threshold status should look at average monthly contribution less the R333.33 per month. Below threshold status is specific to a scheme year. E.g. company could be normal in one year and below threshold in another.
		 */
		// 1=normal     2=inconsistent    3=below threshold
		for (SarsLevyRecon sarsLevyRecon : list) {
			if ((sarsLevyRecon.getAverage_levy().doubleValue()<0.0?(sarsLevyRecon.getAverage_levy().doubleValue()*-1.0):sarsLevyRecon.getAverage_levy().doubleValue()) < thresholdStatus) {
				sarsLevyRecon.setLevyStatus(3);
			}
			else {
				int noLevies  = noLeviesForLnumber(sarsLevyRecon.getSdlnumber());
				sarsLevyRecon.setNoLeviesSinceStart(noLevies);
				if (noLevies < 12)  {
					sarsLevyRecon.setLevyStatus(2);
				}
				else  {
					sarsLevyRecon.setLevyStatus(1);
				}
			}
		}
		return list;
	}
}
