package haj.com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.SarsLevyRecon;
import haj.com.bean.SarsLevyReportBean;
import haj.com.bean.SarsLevyStandardDeviationDataBean;
import haj.com.bean.SarsLevyStandardDeviationMonthBean;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.sars.SarsLevyDetails;
import haj.com.sars.SarsMandatoryLevyDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLevyDetailsDAO.
 */
public class SarsLevyDetailsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception global exception
	 * @see    SarsLevyDetails
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> allSarsLevyDetails() throws Exception {
		return (List<SarsLevyDetails>)super.getList("select o from SarsLevyDetails o");
	}
	
	
	/**
	 * All sars levy details no FK.
	 *
	 * @param forDate the for date
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> allSarsLevyDetailsNoFK(Date forDate) throws Exception {
		String hql = "select o from SarsLevyDetails o left join fetch o.sarsFiles sf where o.sarsEmployerDetail is null and o.arrivalDate1 = :forDate ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("forDate", forDate);	
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	/**
	 * All sars levy details no FK.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> allSarsLevyDetailsNoFK() throws Exception {
		String hql = "select o from SarsLevyDetails o left join fetch o.sarsFiles sf where o.sarsEmployerDetail is null  ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}

	/**
	 * Get all SarsLevyDetails between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception global exception
	 * @see    SarsLevyDetails
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> allSarsLevyDetails(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsLevyDetails o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsLevyDetails>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception global exception
	 * @see    SarsLevyDetails
	 */
	public SarsLevyDetails findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsLevyDetails o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsLevyDetails)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SarsLevyDetails by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception global exception
	 * @see    SarsLevyDetails
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> findByName(String description) throws Exception {
	 	String hql = "select o from SarsLevyDetails o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by employer.
	 *
	 * @param empId the emp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> findByEmployer(Long empId) throws Exception {
	 	String hql = "select o from SarsLevyDetails o where o.sarsEmployerDetail.id = :empId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("empId", empId);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by month and ref.
	 *
	 * @param monthId the month id
	 * @param refNo the ref no
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> findByMonthAndRef(Long monthId,String refNo) throws Exception {
	 	String hql = "select o from SarsLevyDetails o where o.sarsFiles.id = :monthId and o.refNo = :refNo " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("monthId", monthId);
	    parameters.put("refNo", refNo);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	

	public Long numberOfLevies(String refNo, Date fromDate, Date toDate,String inClause) throws Exception {
	 	String hql = "select count(o) from haj.com.sars.SarsLevyDetails o " + 
	 			"where o.arrivalDate1 between :fromDate and :toDate " + 
	 			"and o.refNo = :refNo " + 
	 			"and o.mandatoryLevy < 0 " + 
	 			"and o.schemeYear in "+inClause ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    return (Long)super.getUniqueResult(hql, parameters);
	}
	
	public SarsLevyDetails levyForSchemeYear(Integer setaSchemeYear, Integer sarsSchemeYear)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("setaSchemeYear", setaSchemeYear);
	    parameters.put("sarsSchemeYear", sarsSchemeYear);
	    return (SarsLevyDetails)super.getUniqueNQResult("NQ_NATIVE_levyForSchemeYear", parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> levyForSchemeYearPerSDL(Integer setaSchemeYear, Integer sarsSchemeYear)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("setaSchemeYear", setaSchemeYear);
	    parameters.put("sarsSchemeYear", sarsSchemeYear);
	    return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_leviesPerSDLNrForSchemeYear", parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> paymentsThatHasNoMandatoryLevy(Integer setaSchemeYear)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("setaSchemeYear", setaSchemeYear);
	    return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_payments_that_has_no_mandatory_levy", parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> approvedPaymentsThatHasNotBeenPaid(Integer setaSchemeYear)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("setaSchemeYear", setaSchemeYear);
	    return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_approved_payments_that_has_not_been_paid", parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> latestLevy(String refNo)  throws Exception {
		String hql = "select a " + 
				"from SarsLevyDetails a " + 
				"where a.arrivalDate1 >= (select max(b.arrivalDate1) from SarsLevyDetails b) " + 
				"and a.refNo = :refNo ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> gpLevyForMonth(Integer sarsFileId)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sarsFileId", sarsFileId);
	    return ( List<SarsLevyDetails>)super.getNQList("NQ_NATIVE_GP_levyForMonth", parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> leviesBySchemeYearByChamber(Integer schemeYear)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
	    return ( List<SarsLevyDetails>)super.getNQList("NQ_NATIVE_GP_levyForSchemeYearByChamber", parameters);
	}
	
	public boolean levyNumberExist(String refNo)  throws Exception { 
		String sqlString = "select exists(select * from sars_employer_detail where ref_no= :refNo limit 1)";
		java.math.BigInteger x = (java.math.BigInteger)getSession().createNativeQuery(sqlString).setParameter("refNo", refNo).getSingleResult();
		 return x.intValue() == 1;

	}
	
	
	public SarsLevyDetails levyForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("fromDateInv", fromDateInv);
	    parameters.put("toDateInv", toDateInv);	  
	    parameters.put("sarsSchemeYear",sarsSchemeYear);
	    parameters.put("setaSchemeYear",setaSchemeYear);
	    return (SarsLevyDetails)super.getUniqueNQResult("NQ_NATIVE_SARS_levyForPeriod", parameters);
	}
	
	
	

	
	@SuppressWarnings("unchecked")
	public List<SarsMandatoryLevyDetails> findDetailByPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear) throws Exception {
	 	String hql = "select new haj.com.sars.SarsMandatoryLevyDetails( o.arrivalDate1, o.refNo, o.mandatoryLevy, o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total, o.schemeYear) " + 
	 			"from SarsLevyDetails o " + 
	 			"where o.refNo in ( select b.vendorId from TS2 b where b.postingDateD between :fromDateInv and :toDateInv and b.schemeYear = :setaSchemeYear) " + 
	 			"and o.arrivalDate1 between :fromDate and :toDate and o.schemeYear = :sarsSchemeYear " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("fromDateInv", fromDateInv);
	    parameters.put("toDateInv", toDateInv);	
	    parameters.put("sarsSchemeYear", sarsSchemeYear);
	    parameters.put("setaSchemeYear", setaSchemeYear);
		return (List<SarsMandatoryLevyDetails>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> levyDeviationForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear, int startingAt, int maxPerPage, String sdlNumber)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("fromDateInv", fromDateInv);
	    parameters.put("toDateInv", toDateInv);	  
	    parameters.put("sarsSchemeYear",sarsSchemeYear);
	    parameters.put("setaSchemeYear",setaSchemeYear);
	    parameters.put("sdlNumber",sdlNumber);
	    return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_levy_deviation_report_by_period", parameters,  startingAt,  maxPerPage);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> levyDeviationForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear,  String sdlNumber)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("fromDateInv", fromDateInv);
	    parameters.put("toDateInv", toDateInv);	  
	    parameters.put("sarsSchemeYear",sarsSchemeYear);
	    parameters.put("setaSchemeYear",setaSchemeYear);
	    parameters.put("sdlNumber",sdlNumber);
	    return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_levy_deviation_report_by_period", parameters);
	}
	

	public Integer countLevyDeviationForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear,String sdlNumber)  throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("fromDateInv", fromDateInv);
	    parameters.put("toDateInv", toDateInv);	  
	    parameters.put("sarsSchemeYear",sarsSchemeYear);
	    parameters.put("setaSchemeYear",setaSchemeYear);
	    java.math.BigInteger l = (java.math.BigInteger)  getSession().createNativeQuery("select count(distinct ref_no)  " + 
	    		"from sars_levy_detail     "
	    		+ " where arrival_date_1 between :fromDate and :toDate " + 
	    		" and ref_no in (select b.vendor_id from t_s2 b where b.posting_date_d between :fromDateInv and :toDateInv and b.scheme_year = :setaSchemeYear	 )" + 
	    		" and scheme_year = :sarsSchemeYear	and ref_no like :sdlNumber " )
	        .setParameter("fromDate", fromDate)
	        .setParameter("toDate", toDate)
	        .setParameter("fromDateInv", fromDateInv)
	        .setParameter("toDateInv", toDateInv)
	        .setParameter("sarsSchemeYear",sarsSchemeYear)
	    		.setParameter("setaSchemeYear",setaSchemeYear)
	    		.setParameter("sdlNumber",sdlNumber)
	    		.getSingleResult();
	   return l.intValue();
	    
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> levyPerChamberForSchemeYear(Integer schemeYear)  throws Exception { 
		String hql = "select f.description, sum(a.mandatoryLevy)  " + 
				"from SarsLevyDetails a, SarsEmployerDetail b , SICCode c, Chamber f " + 
				"where c.code = b.sicCode2 " + 
				"and a.sarsFiles.id = b.sarsFiles.id " + 
				"and a.refNo = b.refNo " + 
				"and c.chamber.id = f.id " + 
				"and a.schemeYear = :schemeYear " + 
				"group by f.description";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
	    return ( List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public SarsLevyDetails find1stByArrivalDate(Date arrivalDate) throws Exception {
		SarsLevyDetails sld = null;
		String hql = "select o from SarsLevyDetails o left join fetch o.sarsFiles sf where date(o.arrivalDate1) = date(:arrivalDate) ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("arrivalDate", arrivalDate);	
		List<SarsLevyDetails> l = (List<SarsLevyDetails>)super.getList(hql, parameters,1);
		for (SarsLevyDetails sarsLevyDetails : l) {
			sld = sarsLevyDetails;
			break;
		}
		return sld;
	}
	
	/*
	 * Report: SARS Report By Scheme Year Summary
	 * Summarize all entries in SARS levy details, groups by Scheme year and ignores entries with no scheme years. 
	 */
	public List<SarsLevyReportBean> schemeYearSummaryReport() throws Exception {
		String sql = "select " + 
				"	sld.scheme_year as schemeYear " + 
				"	, ROUND(sum(sld.mandatory_levy),2) as mandatoryLevy " + 
				"	, ROUND(sum(sld.discretionary_levy),2) as discretionaryLevy " + 
				"	, ROUND(sum(sld.admin_levy),2) as adminLevy " + 
				"	, ROUND(sum(sld.interest),2) as interest " + 
				"	, ROUND(sum(sld.penalty),2) as penalty " + 
				"	, ROUND(sum(sld.total),2) as total " + 
				"from sars_levy_detail sld " + 
				"where sld.scheme_year is not null " + 
				"group by sld.scheme_year";
		return (List<SarsLevyReportBean>)super.nativeSelectSqlList(sql, SarsLevyReportBean.class);
	}
	
	/*
	 * Report: Summary of SARS Levy Detail By Arrival Dates
	 * Summarize all entries in SARS levy details where arrival date between dates passed. Groups by reference number / levy number.
	 * From date should be start of month and day. 
	 * To date expected to be end of day for date selected. 
	 * Confirmation required on where to get company entity name.
	 */
	public List<SarsLevyReportBean> summaryReportBetweenArrivalDates(Date formDate, Date toDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select " + 
				"	sld.ref_no as description " + 
				"	, ROUND(sum(sld.mandatory_levy),2) as mandatoryLevy " + 
				"	, ROUND(sum(sld.discretionary_levy),2) as discretionaryLevy " + 
				"	, ROUND(sum(sld.admin_levy),2) as adminLevy " + 
				"	, ROUND(sum(sld.interest),2) as interest " + 
				"	, ROUND(sum(sld.penalty),2) as penalty " + 
				"	, ROUND(sum(sld.total),2) as total " + 
				"from sars_levy_detail sld " + 
				"where sld.arrival_date_1 between '"+sdf.format(formDate)+"' and '"+sdf.format(toDate)+"' " + 
				"group by ref_no";
		return (List<SarsLevyReportBean>)super.nativeSelectSqlList(sql, SarsLevyReportBean.class);
	}
	
	/*
	 * Report: Summary of SARS Levy Detail By Arrival Dates Version Two
	 * Summarize all entries in SARS levy details where arrival date between dates passed. Groups by reference number / levy number.
	 * From date should be start of month and day. 
	 * To date expected to be end of day for date selected. 
	 * Confirmation required on where to get company entity name.
	 * Version Two contains the company name on sars employer details 
	 */
	public List<SarsLevyReportBean> summaryReportBetweenArrivalDatesVersionTwo(Date formDate, Date toDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT " + 
				"	a.description 	" + 
				"   , b.registered_name_of_entity AS additionalInformation " + 
				"	, a.mandatoryLevy 	" + 
				"	, a.discretionaryLevy 	" + 
				"	, a.adminLevy 	" + 
				"	, a.interest 	" + 
				"	, a.penalty 	" + 
				"	, a.total " + 
				"FROM ( " + 
				"    SELECT 	" + 
				"        sld.ref_no AS description " + 
				"        , ROUND(sum(sld.mandatory_levy),2) AS mandatoryLevy 	" + 
				"        , ROUND(sum(sld.discretionary_levy),2) AS discretionaryLevy 	" + 
				"        , ROUND(sum(sld.admin_levy),2) AS adminLevy 	" + 
				"        , ROUND(sum(sld.interest),2) AS interest 	" + 
				"        , ROUND(sum(sld.penalty),2) AS penalty 	" + 
				"        , ROUND(sum(sld.total),2) AS total " + 
				"        , MAX(sars_filel_id) AS sars_filel_id " + 
				"    FROM sars_levy_detail sld  " + 
				"    WHERE sld.arrival_date_1 BETWEEN '"+sdf.format(formDate)+"' AND '"+sdf.format(toDate)+"' " + 
				"    GROUP BY ref_no " + 
				") AS a " + 
				"INNER JOIN sars_employer_detail b ON b.sars_filel_id = a.sars_filel_id AND b.ref_no = a.description";
		return (List<SarsLevyReportBean>)super.nativeSelectSqlList(sql, SarsLevyReportBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> locateDistinctSchemeYears() throws Exception {
		String hql = "select distinct(o.schemeYear) from SarsLevyDetails o where o.schemeYear is not null order by o.schemeYear desc ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<Integer>)super.getList(hql, parameters);
	}
	
	/*
	 * Report: SARS Summary Over Multiple Scheme Year Report
	 * Summarize all entries in SARS levy details where scheme year is currently in list. Summarize by one or multiple Scheme years in list passed. Groups by reference number / levy number.
	 * If list is empty or null will summarize by all scheme years.
	 * Will ignore if no scheme year on line entry.s
	 */
	public List<SarsLevyReportBean> multipleSchemeYearSummaryReport(List<Integer> schemeYearsList) throws Exception {
		String sql = "select " + 
				"	sld.ref_no as description " + 
				"	, ROUND(sum(sld.mandatory_levy),2) as mandatoryLevy " + 
				"	, ROUND(sum(sld.discretionary_levy),2) as discretionaryLevy " + 
				"	, ROUND(sum(sld.admin_levy),2) as adminLevy " + 
				"	, ROUND(sum(sld.interest),2) as interest " + 
				"	, ROUND(sum(sld.penalty),2) as penalty " + 
				"	, ROUND(sum(sld.total),2) as total " + 
				"from sars_levy_detail sld ";
		if (schemeYearsList != null && schemeYearsList.size() != 0) {
			
			// if list has data, loop through and add years to entry
			sql += " where sld.scheme_year in ( ";
			Integer counter = 1;
			for (Integer year : schemeYearsList) {
				if (counter == schemeYearsList.size()) {
					sql += " " + year + " ";
				} else {
					sql += " " + year + " , ";
				}
				counter++;
			}
			counter = null;
			sql += " ) and ";
			
		} else {
			
			// if no list passed or null, get all years data
			sql += " where ";
			
		}
		
		sql +=  " sld.scheme_year is not null " + 
				" group by ref_no ";
		return (List<SarsLevyReportBean>)super.nativeSelectSqlList(sql, SarsLevyReportBean.class);
	}
	
	/*
	 * Report: SARS Summary Over Multiple Scheme Year Report Version Two
	 * Summarize all entries in SARS levy details where scheme year is currently in list. Summarize by one or multiple Scheme years in list passed. Groups by reference number / levy number.
	 * If list is empty or null will summarize by all scheme years.
	 * Will ignore if no scheme year on line entry.s
	 * Version Two: populates the company name
	 */
	public List<SarsLevyReportBean> multipleSchemeYearSummaryReportVersionTwo(List<Integer> schemeYearsList) throws Exception {
		String sql = "SELECT " + 
				"		a.description 	" + 
				"    	, b.registered_name_of_entity AS additionalInformation " + 
				"		, a.mandatoryLevy 	" + 
				"		, a.discretionaryLevy 	" + 
				"		, a.adminLevy 	" + 
				"		, a.interest 	" + 
				"		, a.penalty 	" + 
				"		, a.total " + 
				"	FROM (" + 
				"  select " + 
				"	sld.ref_no as description " + 
				"	, ROUND(sum(sld.mandatory_levy),2) as mandatoryLevy " + 
				"	, ROUND(sum(sld.discretionary_levy),2) as discretionaryLevy " + 
				"	, ROUND(sum(sld.admin_levy),2) as adminLevy " + 
				"	, ROUND(sum(sld.interest),2) as interest " + 
				"	, ROUND(sum(sld.penalty),2) as penalty " + 
				"	, ROUND(sum(sld.total),2) as total " + 
				"   , MAX(sars_filel_id) AS sars_filel_id " + 
				"from sars_levy_detail sld ";
		if (schemeYearsList != null && schemeYearsList.size() != 0) {
			// if list has data, loop through and add years to entry
			sql += " where sld.scheme_year in ( ";
			Integer counter = 1;
			for (Integer year : schemeYearsList) {
				if (counter == schemeYearsList.size()) {
					sql += " " + year + " ";
				} else {
					sql += " " + year + " , ";
				}
				counter++;
			}
			counter = null;
			sql += " ) and ";
		} else {
			
			// if no list passed or null, get all years data
			sql += " where ";
			
		}
		sql +=  " sld.scheme_year is not null " + 
				" group by ref_no " + 
				") AS a " + 
				" INNER JOIN sars_employer_detail b ON b.sars_filel_id = a.sars_filel_id AND b.ref_no = a.description ";
		return (List<SarsLevyReportBean>)super.nativeSelectSqlList(sql, SarsLevyReportBean.class);
	}

	public List<SarsLevyReportBean> multipleSchemeYearSummaryReportVersionTwo(List<Integer> schemeYearsList, Date formDate, Date toDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT " +
				"		a.description 	" +
				"    	, b.registered_name_of_entity AS additionalInformation " +
				"		, a.mandatoryLevy 	" +
				"		, a.discretionaryLevy 	" +
				"		, a.adminLevy 	" +
				"		, a.interest 	" +
				"		, a.penalty 	" +
				"		, a.total " +
				"	FROM (" +
				"  select " +
				"	sld.ref_no as description " +
				"	, ROUND(sum(sld.mandatory_levy),2) as mandatoryLevy " +
				"	, ROUND(sum(sld.discretionary_levy),2) as discretionaryLevy " +
				"	, ROUND(sum(sld.admin_levy),2) as adminLevy " +
				"	, ROUND(sum(sld.interest),2) as interest " +
				"	, ROUND(sum(sld.penalty),2) as penalty " +
				"	, ROUND(sum(sld.total),2) as total " +
				"   , MAX(sars_filel_id) AS sars_filel_id " +
				"from sars_levy_detail sld ";
		if (schemeYearsList != null && schemeYearsList.size() != 0) {
			// if list has data, loop through and add years to entry
			sql += " where sld.scheme_year in ( ";
			Integer counter = 1;
			for (Integer year : schemeYearsList) {
				if (counter == schemeYearsList.size()) {
					sql += " " + year + " ";
				} else {
					sql += " " + year + " , ";
				}
				counter++;
			}
			counter = null;
			sql += " ) and ";
		} else {

			// if no list passed or null, get all years data
			sql += " where ";

		}
		sql +=  " sld.scheme_year is not null and sld.arrival_date_1 BETWEEN '"+sdf.format(formDate)+"' AND '"+sdf.format(toDate)+"' " +
				" group by ref_no " +
				") AS a " +
				" INNER JOIN sars_employer_detail b ON b.sars_filel_id = a.sars_filel_id AND b.ref_no = a.description ";
		return (List<SarsLevyReportBean>)super.nativeSelectSqlList(sql, SarsLevyReportBean.class);
	}
	
	/**
	 * Standard Deviation Report
	 * @param entityType
	 * @param startingAt
	 * @param pageSize
	 * @param financialyear
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyStandardDeviationDataBean> findAllCompanyStatements(Class<?> entityType, int startingAt, int pageSize, List<SarsLevyStandardDeviationMonthBean> monthsList) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT ref_no as description, " + 
				"SUM(if(numEmp = 'monthOne', num, 0)) as monthOne, " + 
				"SUM(if(numEmp = 'monthTwo', num, 0)) as monthTwo, " + 
				"SUM(if(numEmp = 'monthThree', num, 0)) as monthThree, " + 
				"SUM(if(numEmp = 'monthFour', num, 0)) as monthFour, " + 
				"SUM(if(numEmp = 'monthFive', num, 0)) as monthFive, " + 
				"SUM(if(numEmp = 'monthSix', num, 0)) as monthSix, " + 
				"SUM(if(numEmp = 'monthSeven', num, 0)) as monthSeven, " + 
				"SUM(if(numEmp = 'monthEight', num, 0)) as monthEight, " + 
				"SUM(if(numEmp = 'monthNine', num, 0)) as monthNine, " + 
				"SUM(if(numEmp = 'monthTen', num, 0)) as monthTen, " + 
				"SUM(if(numEmp = 'monthEleven', num, 0)) as monthEleven, " + 
				"SUM(if(numEmp = 'monthTweleve', num, 0)) as monthTweleve " + 
				"FROM ( " + 
				"	select ref_no , ROUND(sum(mandatory_levy),2) as num, numEmp from ( " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthOne' as numEmp  " + 
				"				, sld.ref_no as ref_no  " + 
				"			from sars_levy_detail sld " + 
				"			where sld.arrival_date_1 between ' :F1D ' and ' :T1D ' " + 
				"		union " + 
				"			select  " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthTwo' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F2D ' and ' :T2D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthThree' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F3D ' and ':T3D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthFour' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F4D ' and ':T4D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthFive' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F5D ' and ':T5D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthSix' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F6D ' and ':T6D ' " +  
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthSeven' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F7D ' and ':T7D ' " +   
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthEight' as numEmp  " + 
				"				, sld.ref_no as ref_no  " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F8D ' and ':T8D ' " +   
				"		union " + 
				"			select  " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthNine' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F9D ' and ' :T9D ' " +    
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthTen' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F10D ' and ' :T10D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthEleven' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F11D ' and ' :T11D ' " + 
				"		union " + 
				"			select  " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthTweleve' as numEmp  " + 
				"				, sld.ref_no as ref_no  " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F12D ' and ' :T12D ' " + 
				"	) nums group by ref_no, numEmp " + 
				") counts " + 
				"group by ref_no";
		 Map<String, Object> parameters = new Hashtable<String, Object>();
		 // must have 12 entries in the list
		 int count = 1;
		 for (SarsLevyStandardDeviationMonthBean month : monthsList) {
//			 System.out.println(":FD" + count + " ------- " + sdf.format(month.getFromDate()));
//			 System.out.println(":TD" + count + " ------- " + sdf.format(month.getToDate()));
			 sql = sql.replace(":F" + count + "D", sdf.format(month.getFromDate()));
			 sql = sql.replace(":T" + count + "D", sdf.format(month.getToDate()));
//			 parameters.put("FD" + count, sdf.format(month.getFromDate()));
//			 parameters.put("TD" + count, sdf.format(month.getToDate()));
			 count++;
		 }
		 return (List<SarsLevyStandardDeviationDataBean>) super.nativeSelectSqlList(sql, entityType, parameters,  startingAt,  pageSize);
	}
	
	public List<SarsLevyStandardDeviationDataBean> countAllCompanyStatements(List<SarsLevyStandardDeviationMonthBean> monthsList) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT count(ref_no) as counter FROM ( " + 
				"SELECT ref_no as ref_no " +  
				"FROM ( " + 
				"	select ref_no , ROUND(sum(mandatory_levy),2) as num, numEmp from ( " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthOne' as numEmp  " + 
				"				, sld.ref_no as ref_no  " + 
				"			from sars_levy_detail sld " + 
				"			where sld.arrival_date_1 between ' :F1D ' and ' :T1D ' " + 
				"		union " + 
				"			select  " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthTwo' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F2D ' and ' :T2D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthThree' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F3D ' and ':T3D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthFour' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F4D ' and ':T4D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthFive' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F5D ' and ':T5D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthSix' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F6D ' and ':T6D ' " +  
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthSeven' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F7D ' and ':T7D ' " +   
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthEight' as numEmp  " + 
				"				, sld.ref_no as ref_no  " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F8D ' and ':T8D ' " +   
				"		union " + 
				"			select  " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthNine' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F9D ' and ' :T9D ' " +    
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthTen' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F10D ' and ' :T10D ' " + 
				"		union " + 
				"			select " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthEleven' as numEmp  " + 
				"				, sld.ref_no as ref_no " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F11D ' and ' :T11D ' " + 
				"		union " + 
				"			select  " + 
				"				sld.mandatory_levy as mandatory_levy " + 
				"				, 'monthTweleve' as numEmp  " + 
				"				, sld.ref_no as ref_no  " + 
				"			from sars_levy_detail sld " + 
				"           where sld.arrival_date_1 between ' :F12D ' and ' :T12D ' " + 
				"	) nums group by ref_no, numEmp " + 
				") counts " + 
				"group by ref_no  " + 
				") counter ";
		 // must have 12 entries in the list
		 int count = 1;
		 for (SarsLevyStandardDeviationMonthBean month : monthsList) {
			 sql = sql.replace(":F" + count + "D", sdf.format(month.getFromDate()));
			 sql = sql.replace(":T" + count + "D", sdf.format(month.getToDate()));
			 count++;
		 }
		 return (List<SarsLevyStandardDeviationDataBean>) super.nativeSelectSqlList(sql, SarsLevyStandardDeviationDataBean.class);
	}
	
	
	
	
	/*
select count(distinct ref_no)  
from sars_levy_detail     
where arrival_date_1 between '2000-04-01' and '2017-02-28'
 and ref_no in (select b.vendor_id from t_s2 b where b.posted_date_d between '2000-04-01' and '2017-03-31'  and b.scheme_year = 2017	 )
 and scheme_year = 2016	



select a
from SarsLevyDetails a
where a.arrivalDate1 >= (select max(b.arrivalDate1) from SarsLevyDetails b)
and a.refNo like 'L700755210%'

select count(o) from haj.com.sars.SarsLevyDetails o
where o.arrivalDate1 between '2016-10-01' and '2017-09-01'
and o.refNo = 'L010770404'
and o.mandatoryLevy < 0
and o.schemeYear in (2017,2016)
	 */
}

