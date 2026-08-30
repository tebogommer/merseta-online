package haj.com.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.SarsLevyReportBean;
import haj.com.bean.SarsLevyStandardDeviationDataBean;
import haj.com.bean.SarsLevyStandardDeviationMonthBean;
import haj.com.dao.SarsLevyDetailsDAO;
import haj.com.framework.AbstractService;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLevyDetailsService.
 */
public class SarsLevyDetailsService extends AbstractService {
	
	/** The dao. */
	private SarsLevyDetailsDAO dao = new SarsLevyDetailsDAO();
	private SarsEmployerDetailService sarsEmployerDetailService;

	/**
	 * Get all SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception the exception
	 * @see   SarsLevyDetails
	 */
	public List<SarsLevyDetails> allSarsLevyDetails() throws Exception {
	  	return dao.allSarsLevyDetails();
	}


	/**
	 * Create or update SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SarsLevyDetails
	 */
	public void create(SarsLevyDetails entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLevyDetails
	 */
	public void update(SarsLevyDetails entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLevyDetails
	 */
	public void delete(SarsLevyDetails entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception the exception
	 * @see    SarsLevyDetails
	 */
	public SarsLevyDetails findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsLevyDetails by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception the exception
	 * @see    SarsLevyDetails
	 */
	public List<SarsLevyDetails> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SarsLevyDetails.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.sars.SarsLevyDetails}
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> allSarsLevyDetails(int first, int pageSize) throws Exception {
		return dao.allSarsLevyDetails(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SarsLevyDetails for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SarsLevyDetails
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsLevyDetails.class);
	}
	
    /**
     * Lazy load SarsLevyDetails with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SarsLevyDetails class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.sars.SarsLevyDetails} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> allSarsLevyDetails(Class<SarsLevyDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SarsLevyDetails>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SarsLevyDetails for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SarsLevyDetails class
     * @param filters the filters
     * @return Number of rows in the SarsLevyDetails entity
     * @throws Exception the exception
     */	
	public int count(Class<SarsLevyDetails> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> allSarsLevyDetailsBySarsFileId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long sarsFileId) throws Exception {
		String hql = "select o from SarsLevyDetails o left join o.sarsFiles where o.sarsFiles.id = :sarsFileId ";
		filters.put("sarsFileId", sarsFileId);
		return populateAdditionalInformationList((List<SarsLevyDetails>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	
	}

	public int countAllSarsLevyDetailsBySarsFileId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SarsLevyDetails o where o.sarsFiles.id = :sarsFileId ";
		return  dao.countWhere(filters, hql);
	}
	
	public List<SarsLevyDetails> populateAdditionalInformationList(List<SarsLevyDetails> entityList) throws Exception {
		for (SarsLevyDetails entity : entityList) {
			populateAdditionalInformation(entity);
		}
		return entityList;
	}
	
	public SarsLevyDetails populateAdditionalInformation(SarsLevyDetails entity) throws Exception {
		if (entity.getMandatoryLevy() != null) {
			entity.setMandatoryLevyD(GenericUtility.roundToPrecision(entity.getMandatoryLevy().doubleValue(), 2));
		}
		if (entity.getDiscretionaryLevy() != null) {
			entity.setDiscretionaryLevyD(GenericUtility.roundToPrecision(entity.getDiscretionaryLevy().doubleValue(), 2));
		}
		if (entity.getAdminLevy() != null) {
			entity.setAdminLevyD(GenericUtility.roundToPrecision(entity.getAdminLevy().doubleValue(), 2));
		}
		if (entity.getInterest() != null) {
			entity.setInterestD(GenericUtility.roundToPrecision(entity.getInterest().doubleValue(), 2));
		}
		if (entity.getPenalty() != null) {
			entity.setPenaltyD(GenericUtility.roundToPrecision(entity.getPenalty().doubleValue(), 2));
		}
		if (entity.getTotal() != null) {
			entity.setTotalD(GenericUtility.roundToPrecision(entity.getTotal().doubleValue(), 2));
		}
		return entity;
	}
	
	/**
	 * All sars levy details no FK.
	 *
	 * @param forDate the for date
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> allSarsLevyDetailsNoFK(Date forDate) throws Exception { 
		return dao.allSarsLevyDetailsNoFK(forDate);
	}
	
	/**
	 * All sars levy details no FK.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> allSarsLevyDetailsNoFK() throws Exception { 
	 return dao.allSarsLevyDetailsNoFK();	
	}
	
	/**
	 * Find by employer.
	 *
	 * @param emp the emp
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> findByEmployer(SarsEmployerDetail emp) throws Exception { 
		return dao.findByEmployer(emp.getId());
	}
	
	/**
	 * Find by month and ref.
	 *
	 * @param sarsFiles the sars files
	 * @param refNo the ref no
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> findByMonthAndRef(SarsFiles sarsFiles, String refNo) throws Exception { 
		return dao.findByMonthAndRef(sarsFiles.getId(),refNo);
	}
	
	public Long numberOfLevies(String refNo, Date fromDate, Date toDate,String inClause) throws Exception { 
		return dao.numberOfLevies(refNo, fromDate, toDate, inClause);
	}
	
	public List<SarsLevyDetails> latestLevy(String refNo)  throws Exception { 
		return dao.latestLevy(refNo);
	}
	
	public List<SarsLevyDetails> leviesBySchemeYearByChamber(Integer schemeYear)  throws Exception { 
		return fixSign(dao.leviesBySchemeYearByChamber(schemeYear));
	}


	public List<SarsLevyDetails> fixSign(List<SarsLevyDetails> leviesBySchemeYearByChamber) {
/*		leviesBySchemeYearByChamber.forEach(a-> {
			fixSign(a);
		});
*/
	return leviesBySchemeYearByChamber;
	}


	public SarsLevyDetails fixSign(SarsLevyDetails a) {
		if (a !=null && a.getMandatoryLevy()!=null) {
		 a.setMandatoryLevy(a.getMandatoryLevy().doubleValue()<0?BigDecimal.valueOf((a.getMandatoryLevy().doubleValue()*-1.0)):a.getMandatoryLevy());
		 a.setDiscretionaryLevy(a.getDiscretionaryLevy().doubleValue()<0?BigDecimal.valueOf((a.getDiscretionaryLevy().doubleValue()*-1.0)):a.getDiscretionaryLevy());
		 a.setAdminLevy(a.getAdminLevy().doubleValue()<0?BigDecimal.valueOf((a.getAdminLevy().doubleValue()*-1.0)):a.getAdminLevy());
		 a.setInterest(a.getInterest().doubleValue()<0?BigDecimal.valueOf((a.getInterest().doubleValue()*-1.0)):a.getInterest());
		 a.setPenalty(a.getPenalty().doubleValue()<0?BigDecimal.valueOf((a.getPenalty().doubleValue()*-1.0)):a.getPenalty());
		 a.setTotal(a.getTotal().doubleValue()<0?BigDecimal.valueOf((a.getTotal().doubleValue()*-1.0)):a.getTotal());
		}
		return a;
	}
	
	public SarsLevyDetails find1stByArrivalDate(Date arrivalDate) throws Exception { 
		return dao.find1stByArrivalDate(arrivalDate);
	}
	
	public List<SarsLevyReportBean> schemeYearSummaryReport() throws Exception {
		return dao.schemeYearSummaryReport();
	}
	
	public List<SarsLevyReportBean> summaryReportBetweenArrivalDates(Date formDate, Date toDate) throws Exception {
		return dao.summaryReportBetweenArrivalDates(GenericUtility.getStartOfDay(formDate), GenericUtility.getEndOfDay(toDate));
	}
	
	public List<SarsLevyReportBean> summaryReportBetweenArrivalDatesVersionTwo(Date formDate, Date toDate) throws Exception {
		return dao.summaryReportBetweenArrivalDatesVersionTwo(GenericUtility.getStartOfDay(formDate), GenericUtility.getEndOfDay(toDate));
	}
	
	@SuppressWarnings("unused")
	private List<SarsLevyReportBean> populateReportInformationSarsLevyReportBean(List<SarsLevyReportBean> summaryReportBetweenArrivalDates) throws Exception{
		if (sarsEmployerDetailService == null) {
			sarsEmployerDetailService = new SarsEmployerDetailService();
		}
		for (SarsLevyReportBean sarsLevyReportBean : summaryReportBetweenArrivalDates) {
			if (sarsLevyReportBean.getDescription() != null && !sarsLevyReportBean.getDescription().isEmpty()) {
				// locate company name by REF number
				List<SarsEmployerDetail> list = sarsEmployerDetailService.findByRefNumberReturnOneResult(sarsLevyReportBean.getDescription().trim());
				
				if (list != null && list.size() != 0) {
					sarsLevyReportBean.setAdditionalInformation(list.get(0).getRegisteredNameOfEntity());
				}else {
					sarsLevyReportBean.setAdditionalInformation("Unable to locate Entity Name");
				}
				
				list = null;
			}else {
				sarsLevyReportBean.setAdditionalInformation("Unable to locate Entity Name");
			}
		}
		sarsEmployerDetailService = null;
		return summaryReportBetweenArrivalDates;
	}


	public List<Integer> locateDistinctSchemeYears() throws Exception {
		return dao.locateDistinctSchemeYears();
	}
	
	public List<SarsLevyReportBean> multipleSchemeYearSummaryReport(Integer fromSchemeYear, Integer toSchemeYear) throws Exception {
		List<Integer> schemeYears = new ArrayList<>();
		if (fromSchemeYear == toSchemeYear) {
			schemeYears.add(fromSchemeYear);
		} else {
			schemeYears.add(fromSchemeYear);
			Integer checkSchemeYear = fromSchemeYear;
			while (!checkSchemeYear.equals(toSchemeYear)) {
				checkSchemeYear++;
				schemeYears.add(checkSchemeYear);
			}
//			schemeYears.add(toSchemeYear);
		}
		return multipleSchemeYearSummaryReport(schemeYears);
	}
	
	public List<SarsLevyReportBean> multipleSchemeYearSummaryReport(List<Integer> schemeYearsList) throws Exception {
		return dao.multipleSchemeYearSummaryReport(schemeYearsList);
	}
	
	public List<SarsLevyReportBean> multipleSchemeYearSummaryReportVersionTwo(Integer fromSchemeYear, Integer toSchemeYear) throws Exception {
		List<Integer> schemeYears = new ArrayList<>();
		if (fromSchemeYear == toSchemeYear) {
			schemeYears.add(fromSchemeYear);
		} else {
			schemeYears.add(fromSchemeYear);
			Integer checkSchemeYear = fromSchemeYear;
			while (!checkSchemeYear.equals(toSchemeYear)) {
				checkSchemeYear++;
				schemeYears.add(checkSchemeYear);
			}
//			schemeYears.add(toSchemeYear);
		}
		return multipleSchemeYearSummaryReportVersionTwo(schemeYears);
	}

	public List<SarsLevyReportBean> multipleSchemeYearSummaryReportVersionTwo(Integer fromSchemeYear, Integer toSchemeYear, Date formDate, Date toDate) throws Exception {
		List<Integer> schemeYears = new ArrayList<>();
		if (fromSchemeYear == toSchemeYear) {
			schemeYears.add(fromSchemeYear);
		} else {
			schemeYears.add(fromSchemeYear);
			Integer checkSchemeYear = fromSchemeYear;
			while (!checkSchemeYear.equals(toSchemeYear)) {
				checkSchemeYear++;
				schemeYears.add(checkSchemeYear);
			}
//			schemeYears.add(toSchemeYear);
		}
		return dao.multipleSchemeYearSummaryReportVersionTwo(schemeYears, GenericUtility.getStartOfDay(formDate), GenericUtility.getEndOfDay(toDate));
	}

	public List<SarsLevyReportBean> multipleSchemeYearSummaryReportVersionTwo(List<Integer> schemeYearsList) throws Exception {
		return dao.multipleSchemeYearSummaryReportVersionTwo(schemeYearsList);
	}
	
	public List<SarsLevyStandardDeviationMonthBean> populateMonthsListBySarsFileId(SarsFiles sarsFile) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<SarsLevyStandardDeviationMonthBean> monthList = new ArrayList<>();
		Date tweleveMonthsBefore = GenericUtility.deductMonthsFromDate(sarsFile.getForMonth(), 11);
		monthList.add(new SarsLevyStandardDeviationMonthBean(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(tweleveMonthsBefore)), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(tweleveMonthsBefore)), sdf.format(tweleveMonthsBefore)));
		for (int i = 1; i <= 10; i++) {
			Date nextMonth = GenericUtility.addMonthsToDate(tweleveMonthsBefore, i);
			monthList.add(new SarsLevyStandardDeviationMonthBean(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(nextMonth)), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(nextMonth)), sdf.format(nextMonth)));
			nextMonth = null;
		}
		monthList.add(new SarsLevyStandardDeviationMonthBean(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(sarsFile.getForMonth())), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(sarsFile.getForMonth())), sdf.format(sarsFile.getForMonth())));
		// list must be 12 entries or report will fail
		return monthList;
	}
	
	public List<SarsLevyStandardDeviationDataBean> findStandardDeviationReportByMonthList(Class<?> entityType, int startingAt, int pageSize, List<SarsLevyStandardDeviationMonthBean> monthsList) throws Exception {
		return populateAdditionalInformationForDeviationReportList(dao.findAllCompanyStatements(entityType, startingAt, pageSize, monthsList));
	}
	
	public Integer countAllCompanyStatements(List<SarsLevyStandardDeviationMonthBean> monthsList) throws Exception {
		List<SarsLevyStandardDeviationDataBean> entries = dao.countAllCompanyStatements(monthsList);
		return entries.get(0).getCounter().intValue();
	}
	
	public List<SarsLevyStandardDeviationDataBean> populateAdditionalInformationForDeviationReportList(List<SarsLevyStandardDeviationDataBean> resultList) throws Exception{
		for (SarsLevyStandardDeviationDataBean result : resultList) {
			populateAdditionalInformationForDeviationReport(result);
		}
		return resultList;
	}
	
	/*
	 * Business Rules for calculations:
	 * A. The data to be used should look at the last 12 DHET files, up to the most current
	 *Â B. The value to be used is mandatory levy income
	 * C. Calculate average levy income based on months actually contributed
	 * D. Calculate the standard deviation over the 12 month period
	 * E. Calculate the standard deviation percentage (D/C)
	 * 
	 * Inconsistent levy income companies will be those where both rules below apply:
	 * The standard deviation amount is > than 40 000 (use amount calculated in â€œCâ€� above for this)
	 * The % standard deviation (D above) is greater than 20%
	 * 
	 * Where there is only one monthâ€™s levies contributed, the formula will return a â€œ#DIV/0!â€�. In this instance, the company will be inconsistent if the levy amount is greater than R40 000.
	 */
	public SarsLevyStandardDeviationDataBean populateAdditionalInformationForDeviationReport(SarsLevyStandardDeviationDataBean result) throws Exception{
		// processes entries that are not 0
		List<Double> entriesValid = buildValidEntries(result);
		
		// calculates grant total
		result.setTotal(calculateTotal(entriesValid));
		
		// calculates average
		double average = 0.0;
		if (entriesValid != null) {
			for (Double entry : entriesValid) {
				average += entry;
			}
			average = average / entriesValid.size();
		} else {
			average = average / 0;
		}
		result.setAverage(average);
		// calculates Standard Deviation
		result.setStandardDeviation(GenericUtility.calculateStandardDeviation(entriesValid));
		
		// calculates Standard Deviation percentage 
		Double sdp = GenericUtility.calculateStandardDeviationPercentage(entriesValid);
		sdp = sdp * 100;
		result.setStandardPercentage(Math.round(sdp));
		
		/*
		 *  Inconsistent levy income Business Rules 
		 *  Information above
		 */
		Double valueCheck = 40000.00;
		Long percentageCheck = 20l;
		
		// if formula returns null or 0 -- referred to error excel error â€œ#DIV/0!â€� Version 2
		if (result.getStandardPercentage() == null || result.getStandardPercentage().equals(0l)) {
			if (result.getAverage() >= valueCheck) {
				result.setStatus("Inconsistent");
			} else {
				result.setStatus("Normal");
			}
		} else {
			if (result.getAverage() >= valueCheck && result.getStandardPercentage() >= percentageCheck) {
				result.setStatus("Inconsistent");
			} else {
				result.setStatus("Normal");
			}
		}
		
		// if formula returns null or 0 -- referred to error excel error â€œ#DIV/0!â€� Version 1
//		if (result.getStandardPercentage() == null || result.getStandardPercentage().equals(0l)) {
//			if (result.getStandardDeviation() > valueCheck) {
//				result.setStatus("Inconsistent");
//			} else {
//				result.setStatus("Normal");
//			}
//		} else {
//			if (result.getStandardDeviation() >= valueCheck && result.getStandardPercentage() > percentageCheck) {
//				result.setStatus("Inconsistent");
//			} else {
//				result.setStatus("Normal");
//			}
//		}
		
		entriesValid = null;
		return result;
	}
	
	public Double calculateTotal(List<Double> entriesValid) throws Exception{
		Double total = 0.0;
		for (Double amount : entriesValid) {
			total = total + amount;
		}
		return total;
	}
	
	public List<Double> buildValidEntries(SarsLevyStandardDeviationDataBean result){
		List<Double> numberOfEntriesNotZero = new ArrayList<>();
		if (!result.getMonthOne().equals(0.0)) {
			result.setMonthOne(result.getMonthOne()*-1);
			numberOfEntriesNotZero.add(result.getMonthOne());
		}
		if (!result.getMonthTwo().equals(0.0)) {
			result.setMonthTwo(result.getMonthTwo()*-1);
			numberOfEntriesNotZero.add(result.getMonthTwo());
		}
		if (!result.getMonthThree().equals(0.0)) {
			result.setMonthThree(result.getMonthThree()*-1);
			numberOfEntriesNotZero.add(result.getMonthThree());
		}
		if (!result.getMonthFour().equals(0.0)) {
			result.setMonthFour(result.getMonthFour()*-1);
			numberOfEntriesNotZero.add(result.getMonthFour());
		}
		if (!result.getMonthFive().equals(0.0)) {
			result.setMonthFive(result.getMonthFive()*-1);
			numberOfEntriesNotZero.add(result.getMonthFive());
		}
		if (!result.getMonthSix().equals(0.0)) {
			result.setMonthSix(result.getMonthSix()*-1);
			numberOfEntriesNotZero.add(result.getMonthSix());
		}
		if (!result.getMonthSeven().equals(0.0)) {
			result.setMonthSeven(result.getMonthSeven()*-1);
			numberOfEntriesNotZero.add(result.getMonthSeven());
		}
		if (!result.getMonthEight().equals(0.0)) {
			result.setMonthEight(result.getMonthEight()*-1);
			numberOfEntriesNotZero.add(result.getMonthEight());
		}
		if (!result.getMonthNine().equals(0.0)) {
			result.setMonthNine(result.getMonthNine()*-1);
			numberOfEntriesNotZero.add(result.getMonthNine());
		}
		if (!result.getMonthTen().equals(0.0)) {
			result.setMonthTen(result.getMonthTen()*-1);
			numberOfEntriesNotZero.add(result.getMonthTen());
		}
		if (!result.getMonthEleven().equals(0.0)) {
			result.setMonthEleven(result.getMonthEleven()*-1);
			numberOfEntriesNotZero.add(result.getMonthEleven());
		}
		if (!result.getMonthTweleve().equals(0.0)) {
			result.setMonthTweleve(result.getMonthTweleve()*-1);
			numberOfEntriesNotZero.add(result.getMonthTweleve());
		}
		return numberOfEntriesNotZero;
	}
	
	public void updateMgProcess(SarsLevyDetails entity) throws Exception{
		if (entity.getCanAppearOnMgPayments()) {
			entity.setCanAppearOnMgPayments(false);
		}else {
			entity.setCanAppearOnMgPayments(true);
		}
		update(entity);
	}

}