package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.MgTransactionsBean;
import haj.com.entity.datatakeon.TS2;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsFilesDAO.
 */
public class SarsFilesDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsFiles.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SarsFiles}
	 * @throws Exception global exception
	 * @see    SarsFiles
	 */
	@SuppressWarnings("unchecked")
	public List<SarsFiles> allSarsFiles() throws Exception {
		return (List<SarsFiles>)super.getList("select o from SarsFiles o");
	}

	/**
	 * Get all SarsFiles between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SarsFiles}
	 * @throws Exception global exception
	 * @see    SarsFiles
	 */
	@SuppressWarnings("unchecked")
	public List<SarsFiles> allSarsFiles(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsFiles o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsFiles>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SarsFiles> last12MonthsSarsFiles() throws Exception {
	 	String hql = "select o from SarsFiles o order by o.forMonth desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<SarsFiles>)super.getList(hql, parameters,12);
	}
	
	
	@SuppressWarnings("unchecked")
	public SarsFiles latestSarsLevyFile() throws Exception {
		SarsFiles sf = null;
	 	String hql = "select o from SarsFiles o order by o.forMonth desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    List<SarsFiles> l = (List<SarsFiles>)super.getList(hql, parameters,1);
		for (SarsFiles sarsFiles : l) {
			sf = sarsFiles;
		}
	    return sf;
	}
	
	public Integer countSarsFilesWhereForMonthBetweenDates(Date fromDate, Date toDate) throws Exception {
	 	String hql = "select count(o) from SarsFiles o where DATE(o.forMonth) between DATE(:fromDate) and DATE(:toDate)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SarsFiles}
	 * @throws Exception global exception
	 * @see    SarsFiles
	 */
	public SarsFiles findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsFiles o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsFiles)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by date.
	 *
	 * @param forMonth the for month
	 * @return the sars files
	 * @throws Exception the exception
	 */
	public SarsFiles findByDate(Date forMonth) throws Exception {
	 	String hql = "select o from SarsFiles o where o.forMonth = :forMonth " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("forMonth", forMonth);
		return (SarsFiles)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find SarsFiles by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SarsFiles}
	 * @throws Exception global exception
	 * @see    SarsFiles
	 */
	@SuppressWarnings("unchecked")
	public List<SarsFiles> findByName(String description) throws Exception {
	 	String hql = "select o from SarsFiles o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsFiles>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsFiles> findBetweemDates(Date fromDate , Date toDate) throws Exception {
	 	String hql = "select o from SarsFiles o "
	 			+ "where date(o.forMonth) between :fromDate and :toDate order by o.forMonth " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
		return (List<SarsFiles>)super.getList(hql, parameters);
	}

	/**
	 * Monthy summary.
	 *
	 * @param date the date
	 * @return the sars levy details
	 */
	public SarsLevyDetails monthySummary(Date date) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(o.arrivalDate1, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.penalty), sum(o.total), sum(o.interest)) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.arrivalDate1 = :date "
	 			+ " group by o.arrivalDate1 " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("date", date);
		return (SarsLevyDetails)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Monthy summary.
	 *
	 * @param id the id
	 * @return the sars levy details
	 */
	public SarsLevyDetails monthySummary(Long id) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(o.sarsFiles.id, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.penalty), sum(o.total), sum(o.interest)) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.sarsFiles.id = :id "
	 			+ " group by  o.sarsFiles.id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsLevyDetails)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Sdl summary.
	 *
	 * @param refNo the ref no
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sdlSummary(String refNo) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(o.arrivalDate1, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.penalty), sum(o.total), sum(o.interest)) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.refNo = :refNo "
	 			+ " group by o.arrivalDate1 "
	 			+ " order by  o.arrivalDate1 desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Sdl summary by ref and date.
	 *
	 * @param refNo the ref no
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sdlSummaryByRefAndDate(String refNo, Date fromDate, Date toDate) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(o.arrivalDate1, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.penalty), sum(o.interest), sum(o.total)) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.refNo = :refNo "
	 			+ " and o.arrivalDate1 between :fromDate and :toDate "
	 			+ " group by o.arrivalDate1 "
	 			+ " order by  o.arrivalDate1 desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	

	@SuppressWarnings("unchecked")
	public SarsLevyDetails sdlSummaryByRefAndSchemeYear(String refNo, Integer schemeYear) {	
	    String hql = "select  new  haj.com.sars.SarsLevyDetails(o.schemeYear,sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.penalty), sum(o.interest), sum(o.total)) " + 
	    		"	 			 from SarsLevyDetails o  " + 
	    		"	 			 where o.refNo = :refNo " + 
	    		"	 			 and o.schemeYear = :schemeYear " ;	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    parameters.put("schemeYear", schemeYear);
	
		return (SarsLevyDetails)super.getUniqueResult(hql, parameters);
	}	
	
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sdlSummaryByRefAndSchemeYearGroupByArrivalDate(String refNo, Integer schemeYear) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails( o.arrivalDate1,sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.penalty), sum(o.interest), sum(o.total)) "
	//	String hql = "select new  haj.com.sars.SarsLevyDetails( o.arrivalDate1,o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.penalty, o.interest, o.total) "
	 			+ " from SarsLevyDetails o "
	 			+	"	 			 where o.refNo = :refNo " + 
	    		"	 			 and o.schemeYear = :schemeYear " 	
	 			+ " group by o.arrivalDate1 "
	 			+ " order by  o.arrivalDate1 asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    parameters.put("schemeYear", schemeYear);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Recon levy summary.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	//public SarsLevyDetails(Integer schemeYear,BigDecimal mandatoryLevy, BigDecimal discretionaryLevy , BigDecimal adminLevy, BigDecimal interest ,BigDecimal penalty, BigDecimal total) {
//	@SuppressWarnings("unchecked")
//	public List<SarsLevyDetails> reconLevySummary() throws Exception {
//	 	String hql = "select new  haj.com.sars.SarsLevyDetails( o.schemeYear, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total) ) "
//	 			+ " from SarsLevyDetails o "
//	 			+ " where o.mandatoryLevy is not null"
//	 			+ " group by o.schemeYear "
//	 			+ " order by  o.schemeYear desc" ;
//		return (List<SarsLevyDetails>)super.getList(hql);
//	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> reconLevySummary() throws Exception {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails( o.arrivalDate1, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total) ) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.mandatoryLevy is not null"
	 			+ " group by o.arrivalDate1 ";
	 		//	+ " order by  o.arrivalDate1 desc" ;
		return (List<SarsLevyDetails>)super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> reconLevySummary(String refNo) throws Exception {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(  o.arrivalDate1, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total) ) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.mandatoryLevy is not null"
	 			+"  and o.refNo = :refNo "
	 			+ " group by o.arrivalDate1 ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}


	
	@SuppressWarnings("unchecked")
	public List<TS2> reconInvoiceSummary() throws Exception{
	 	String hql = "select new haj.com.entity.datatakeon.TS2(o.schemeYear, sum(o.documentAmountD)) "
	 			+ " from TS2 o "
	 			+ " group by o.schemeYear " ;
		return (List<TS2>)super.getList(hql);
	}
	
	public TS2 reconInvoiceSummary(Integer year,Integer month) throws Exception{
		
	 	String hql = "select new haj.com.entity.datatakeon.TS2(year(o.documentDateD), month(o.documentDateD), sum(o.currentTrxAmountD)) "
	 			+ "from TS2 o " + 
	 			"where year(o.documentDateD) = :year " + 
	 			"and month(o.documentDateD) = :month " + 
	 			"group by year(o.documentDateD), month(o.documentDateD)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	    parameters.put("month", month);
		return (TS2)super.getUniqueResult(hql, parameters);
	}	
	/*
	select year(o.documentDateD), month(o.documentDateD), sum(o.currentTrxAmountD) from TS2 o
where year(o.documentDateD) = 2017
and month(o.documentDateD) = 3
group by year(o.documentDateD), month(o.documentDateD)
	 */
	
	
	public TS2 reconInvoiceSummary(Integer year,Integer month,String vendorId) throws Exception{
	 	String hql = "select new haj.com.entity.datatakeon.TS2(year(o.documentDateD), month(o.documentDateD), sum(o.documentAmountD)) "
	 			+ " from TS2 o "
	 			+ " where year(o.documentDateD) = :year " + 
	 			"	and month(o.documentDateD) = :month " + 
		 		 " and o.vendorId = :vendorId "
	 			+ "group by year(o.documentDateD), month(o.documentDateD) " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	    parameters.put("month", month);
	    parameters.put("vendorId", vendorId);
		return (TS2)super.getUniqueResult(hql, parameters);
	}
	
	public Long numberEmployersInSARSLevyFiles(Integer schemeYear) throws Exception{
	 	String hql = "select  count(distinct o.refNo)  from SarsLevyDetails o " + 
	 				 " where o.schemeYear = :schemeYear and o.mandatoryLevy <> 0 ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
	public Long numberEmployersInSARSEmployerFiles(Integer schemeYear) throws Exception{
	 	String hql = "select count(distinct o.refNo) from SarsEmployerDetail o " + 
	 			"where o.sarsFiles.id in (select  distinct l.sarsFiles.id  from SarsLevyDetails l " + 
	 			"				where l.schemeYear = :schemeYear and l.mandatoryLevy <> 0) " + 
	 			"and o.tradingStatus = 'A' " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
		return (Long)super.getUniqueResult(hql, parameters);
	}	
	
	

	public SarsEmployerDetail numberEmployersInSARSEmployerFilesByStatus(Integer schemeYear, String status) throws Exception{

	
		String hql = "select new haj.com.sars.SarsEmployerDetail(count(distinct o.refNo)) "
	 			+ " from SarsEmployerDetail o " + 
	 			" where o.sarsFiles.id in " 
	 			+ " (select  distinct l.sarsFiles.id  from SarsLevyDetails l " + 
	 			"				where l.schemeYear = :schemeYear and l.mandatoryLevy <> 0 ) " + 
	 			" and o.tradingStatus = :status " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
	    parameters.put("status", status);
		return (SarsEmployerDetail)super.getUniqueResult(hql, parameters);
	}	
	
	

	
	
	public Long numberEmployersInSETAInvoiceFile(Integer schemeYear) throws Exception{
	 	String hql = "select  count(distinct o.vendorId ) from TS2 o " + 
	 			"where o.schemeYear = :schemeYear";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Long> fileIds(Integer schemeYear) throws Exception{
	 	String hql = "select  distinct l.sarsFiles.id  from SarsLevyDetails l " + 
	 			"				where l.schemeYear = :schemeYear " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("schemeYear", schemeYear);
		return (List<Long>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> schemeYears() throws Exception{
	 	String hql = "select distinct o.schemeYear from SarsLevyDetails o " + 
	 			"where o.schemeYear is not null " + 
	 			"order by o.schemeYear" ;
		return (List<Integer>)super.getList(hql);
	}
	
	public SarsLevyDetails monthSummary(Integer year, Date beforeDate, String refNo) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(sum(o.mandatoryLevy)) "
	 			+ " from SarsLevyDetails o "
	 			//+ " where date(o.arrivalDate1) <= date(:beforeDate) " + 
	 			+"where o.refNo = :refNo " + 
	 			"and o.schemeYear = :year " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	  //  parameters.put("beforeDate", beforeDate);
	    parameters.put("refNo", refNo);
		return (SarsLevyDetails)super.getUniqueResult(hql, parameters);
	}
	
	
	public SarsLevyDetails monthSummary(Integer year) {
	 	String hql = "select new  haj.com.sars.SarsLevyDetails(sum(o.mandatoryLevy)) "
	 			+ " from SarsLevyDetails o "
	 			+"where o.schemeYear = :year " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
		return (SarsLevyDetails)super.getUniqueResult(hql, parameters);
	}
	
	
	public TS2 gpMonthSummary(Integer year,  String refNo) throws Exception{
	 	String hql = "select new  haj.com.entity.datatakeon.TS2(sum(o.documentAmountD)) from TS2 o " + 
	 			"where o.vendorId = :refNo " + 
	 			"and o.schemeYear = :year  "
	 		+ "order by o.documentDateD" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	    parameters.put("refNo", refNo);
		return (TS2)super.getUniqueResult(hql, parameters);
	}
	
	
	public TS2 gpMonthSummary(Integer year) throws Exception{
	 	String hql = "select new  haj.com.entity.datatakeon.TS2(sum(o.documentAmountD)) from TS2 o " + 
	 			"where o.schemeYear = :year  "
	 		+ "order by o.documentDateD" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
		return (TS2)super.getUniqueResult(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public SarsFiles findByYearMonth(Integer year, Integer month) {
		SarsFiles s = null;
		String hql = " select o from SarsFiles o " + 
	 			"where year(o.forMonth) = :year " + 
	 			"and month(o.forMonth) = :month " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	    parameters.put("month", month);
	    List<SarsFiles> l =  (List<SarsFiles>)super.getList(hql, parameters,1);
		for (SarsFiles sarsFiles : l) {
			s = sarsFiles;
		}
		return  s;
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(Date fromDate, Date toDate) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFile(Date fromDate, Date toDate) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.sarsFiles.canProcessMgPayments = :canProcessMgPayments "
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck(Date fromDate, Date toDate) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.sarsFiles.canProcessMgPayments = :canProcessMgPayments "
	 			+ " and ( "
	 			+ " (o.mandatoryLevy > 0 and o.schemeYear in ( select y.forSchemeYear from SarsLevySchemeYearReturns y where y.allowReturnsMandatory = true ) )"
	 			+ " or "
	 			+ " (o.mandatoryLevy < 0 and o.schemeYear in ( select z.forSchemeYear from SarsLevySchemeYearReturns z where z.allowInvoicesMandatory = true ) ) "
	 			+ " ) "
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck() throws Exception {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	inner join SarsFiles sf on sf.id = o.sarsFiles.id and sf.canProcessMgPayments = :canProcessMgPayments"
	 			+ " where EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.canAppearOnMgPayments = :canProcessMgPaymentsLevyLevel "
	 			+ " and ( "
	 			+ " (o.mandatoryLevy > 0 and o.schemeYear in ( select y.forSchemeYear from SarsLevySchemeYearReturns y where y.allowReturnsMandatory = true ) )"
	 			+ " or "
	 			+ " (o.mandatoryLevy < 0 and o.schemeYear in ( select z.forSchemeYear from SarsLevySchemeYearReturns z where z.allowInvoicesMandatory = true ) ) "
	 			+ " ) "
	 			+ " order by o.refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
	    parameters.put("canProcessMgPaymentsLevyLevel", Boolean.TRUE);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck() throws Exception {
	 	String hql = "select count(o.id) from SarsLevyDetails o "
	 			+ "	inner join SarsFiles sf on sf.id = o.sarsFiles.id and sf.canProcessMgPayments = :canProcessMgPayments"
	 			+ " where EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.canAppearOnMgPayments = :canProcessMgPaymentsLevyLevel "
	 			+ " and ( "
	 			+ " (o.mandatoryLevy > 0 and o.schemeYear in ( select y.forSchemeYear from SarsLevySchemeYearReturns y where y.allowReturnsMandatory = true ) )"
	 			+ " or "
	 			+ " (o.mandatoryLevy < 0 and o.schemeYear in ( select z.forSchemeYear from SarsLevySchemeYearReturns z where z.allowInvoicesMandatory = true ) ) "
	 			+ " ) "
	 			+ " order by o.refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
	    parameters.put("canProcessMgPaymentsLevyLevel", Boolean.TRUE);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFile(Date fromDate, Date toDate, Integer schemeYear) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.sarsFiles.canProcessMgPayments = :canProcessMgPayments"
	 			+ " and o.schemeYear = :schemeYear"
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
	    parameters.put("schemeYear", schemeYear);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(Date fromDate, Date toDate, Integer schemeYear) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.sarsFiles.canProcessMgPayments = :canProcessMgPayments"
	 			+ " and o.schemeYear = :schemeYear"
	 			+ " and ( "
	 			+ " (o.mandatoryLevy > 0 and o.schemeYear in ( select y.forSchemeYear from SarsLevySchemeYearReturns y where y.allowReturnsMandatory = true ) )"
	 			+ " or "
	 			+ " (o.mandatoryLevy < 0 and o.schemeYear in ( select z.forSchemeYear from SarsLevySchemeYearReturns z where z.allowInvoicesMandatory = true ) ) "
	 			+ " ) "
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
	    parameters.put("schemeYear", schemeYear);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(Integer schemeYear) throws Exception{
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.id,  o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total ) from SarsLevyDetails o "
	 			+ "	where EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.sarsFiles.canProcessMgPayments = :canProcessMgPayments"
	 			+ " and o.canAppearOnMgPayments = :canProcessMgPaymentsLevyLevel"
	 			+ " and o.schemeYear = :schemeYear"
	 			+ " and ( "
	 			+ " (o.mandatoryLevy > 0 and o.schemeYear in ( select y.forSchemeYear from SarsLevySchemeYearReturns y where y.allowReturnsMandatory = true ) )"
	 			+ " or "
	 			+ " (o.mandatoryLevy < 0 and o.schemeYear in ( select z.forSchemeYear from SarsLevySchemeYearReturns z where z.allowInvoicesMandatory = true ) ) "
	 			+ " ) "
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
	    parameters.put("canProcessMgPaymentsLevyLevel", Boolean.TRUE);
	    parameters.put("schemeYear", schemeYear);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	public Integer countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(Integer schemeYear) throws Exception{
	 	String hql = "select count(o) from SarsLevyDetails o "
	 			+ "	where EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " and o.sarsFiles.canProcessMgPayments = :canProcessMgPayments"
	 			+ " and o.canAppearOnMgPayments = :canProcessMgPaymentsLevyLevel"
	 			+ " and o.schemeYear = :schemeYear"
	 			+ " and ( "
	 			+ " (o.mandatoryLevy > 0 and o.schemeYear in ( select y.forSchemeYear from SarsLevySchemeYearReturns y where y.allowReturnsMandatory = true ) )"
	 			+ " or "
	 			+ " (o.mandatoryLevy < 0 and o.schemeYear in ( select z.forSchemeYear from SarsLevySchemeYearReturns z where z.allowInvoicesMandatory = true ) ) "
	 			+ " ) "
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
	    parameters.put("canProcessMgPayments", Boolean.TRUE);
	    parameters.put("canProcessMgPaymentsLevyLevel", Boolean.TRUE);
	    parameters.put("schemeYear", schemeYear);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransferGroupByRef(Date fromDate, Date toDate) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum)"
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy)"
	 			+ " order by refNo asc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(Date fromDate, Date toDate, List<Integer> schemeYearList) {
	 	String hql = "select new haj.com.sars.SarsLevyDetails( o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total) from SarsLevyDetails o "
	 			+ "	where date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum) "
	 			+ " and NOT EXISTS(select y from GpGrantBatchEntry y where y.levyNumber = o.refNo and y.schemeYear = o.schemeYear and date(y.arrivalDateFromSars) = date(o.arrivalDate1) and y.mandatoryLevyFromSars = o.mandatoryLevy) ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    if (schemeYearList.size() != 0) {
			hql += " and (";
			int count = 1;
			for (Integer year : schemeYearList) {
				if (count == schemeYearList.size()) {
					hql += " o.schemeYear = :schemeYear" + count;
				} else {
					hql += " o.schemeYear = :schemeYear" + count + " or ";
				}
				parameters.put("schemeYear" + count, year);
				count++;
			}
			hql += " ) ";
		}
	    hql += " order by refNo asc" ;
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetails> grantListGeneration(Date fromDate, Date toDate, List<Integer> schemeYearList) throws Exception {
	 	String hql = "select new haj.com.sars.SarsLevyDetails(o.refNo, schemeYear, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total)) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.mandatoryLevy is not null "
	 			+ "	and date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum) ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    if (schemeYearList.size() != 0) {
			hql += " and (";
			int count = 1;
			for (Integer year : schemeYearList) {
				if (count == schemeYearList.size()) {
					hql += " o.schemeYear = :schemeYear" + count;
				} else {
					hql += " o.schemeYear = :schemeYear" + count + " or ";
				}
				parameters.put("schemeYear" + count, year);
				count++;
			}
			hql += " ) ";
		}
	    hql += " group by o.refNo , o.schemeYear ";
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("approvalEnum", WspStatusEnum.Approved);
		return (List<SarsLevyDetails>)super.getList(hql, parameters);
	}
	
	// counter for available MG entries
	public List<MgTransactionsBean> countAvalaibleEntriesForProcessing() throws Exception {
		String hql = "select  " + 
				"	count(o.id) as count  " + 
				"from (  " + 
				"	select   " + 
				"		id   " + 
				"	from sars_files  " + 
				"	where   " + 
				"		can_process_mg_payments = true  " + 
				") sf  " + 
				"inner join   " + 
				"	sars_levy_detail o on sf.id = o.sars_filel_id and o.can_appear_on_mg_payments = true   " + 
				"inner join   " + 
				"	sars_levy_scheme_year_returns yr on yr.for_scheme_year = o.scheme_year  " + 
				"inner join   " + 
				"	wsp_approved_check wsp on wsp.levy_number = o.ref_no and wsp.dhet_fin_year = o.scheme_year and wsp.wsp_status_enum = :wspStatusEnum  " + 
				"left join   " + 
				"	gp_grant_batch_entry gb on gb.sars_levy_details_id = o.id  " + 
				"where   " + 
				"	gb.sars_levy_details_id is null   " + 
				"	and (  " + 
				"		(o.mandatory_levy > 0 and yr.allow_returns_mandatory = true)  " + 
				"		or (o.mandatory_levy < 0 and yr.allow_invoices_mandatory = true)  " + 
				"	)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspStatusEnum", 4);
		return (List<MgTransactionsBean>) super.nativeSelectSqlList(hql, MgTransactionsBean.class, parameters);
	}
	
	// counter for available MG entries
	public List<MgTransactionsBean> countAvalaibleEntriesForProcessingBySchemeYear(Integer schemeYear) throws Exception {
		String hql = "select  " + 
				"	count(o.id) as count  " + 
				"from (  " + 
				"	select   " + 
				"		id   " + 
				"	from sars_files  " + 
				"	where   " + 
				"		can_process_mg_payments = true  " + 
				") sf  " + 
				"inner join   " + 
				"	sars_levy_detail o on sf.id = o.sars_filel_id and o.can_appear_on_mg_payments = true   " + 
				"inner join   " + 
				"	sars_levy_scheme_year_returns yr on yr.for_scheme_year = o.scheme_year  " + 
				"inner join   " + 
				"	wsp_approved_check wsp on wsp.levy_number = o.ref_no and wsp.dhet_fin_year = o.scheme_year and wsp.wsp_status_enum = :wspStatusEnum  " + 
				"left join   " + 
				"	gp_grant_batch_entry gb on gb.sars_levy_details_id = o.id  " + 
				"where   " + 
				"	gb.sars_levy_details_id is null   " + 
				"	and (  " + 
				"		(o.mandatory_levy > 0 and yr.allow_returns_mandatory = true)  " + 
				"		or (o.mandatory_levy < 0 and yr.allow_invoices_mandatory = true)  " + 
				"	)" + 
				"   and o.scheme_year = :schemeYear " ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspStatusEnum", 4);
		parameters.put("schemeYear", schemeYear);
		return (List<MgTransactionsBean>) super.nativeSelectSqlList(hql, MgTransactionsBean.class, parameters);
	}
	
	// all ID for SARS Levy Detail ID for processing with limit on 2000 entries
	public List<MgTransactionsBean> avalaibleEntriesForMgProcessing() throws Exception {
		String hql = "select  " + 
				"	o.id as sarsLevyFileId  " + 
				"from (  " + 
				"	select   " + 
				"		id   " + 
				"	from sars_files  " + 
				"	where   " + 
				"		can_process_mg_payments = true  " + 
				") sf  " + 
				"inner join   " + 
				"	sars_levy_detail o on sf.id = o.sars_filel_id and o.can_appear_on_mg_payments = true   " + 
				"inner join   " + 
				"	sars_levy_scheme_year_returns yr on yr.for_scheme_year = o.scheme_year  " + 
				"inner join   " + 
				"	wsp_approved_check wsp on wsp.levy_number = o.ref_no and wsp.dhet_fin_year = o.scheme_year and wsp.wsp_status_enum = :wspStatusEnum  " + 
				"left join   " + 
				"	gp_grant_batch_entry gb on gb.sars_levy_details_id = o.id  " + 
				"where   " + 
				"	gb.sars_levy_details_id is null   " + 
				"	and (  " + 
				"		(o.mandatory_levy > 0 and yr.allow_returns_mandatory = true)  " + 
				"		or (o.mandatory_levy < 0 and yr.allow_invoices_mandatory = true)  " + 
				"	)" + 
				"Limit 2000 ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspStatusEnum", 4);
		return (List<MgTransactionsBean>) super.nativeSelectSqlList(hql, MgTransactionsBean.class, parameters);
	}
	
	
	// all ID for SARS Levy Detail ID for processing with scheme year with limit on 2000 entries
	public List<MgTransactionsBean> avalaibleEntriesForMgProcessingBySchemeYear(Integer schemeYear) throws Exception {
		String hql = "select  " + 
				"	o.id as sarsLevyFileId  " + 
				"from (  " + 
				"	select   " + 
				"		id   " + 
				"	from sars_files  " + 
				"	where   " + 
				"		can_process_mg_payments = true  " + 
				") sf  " + 
				"inner join   " + 
				"	sars_levy_detail o on sf.id = o.sars_filel_id and o.can_appear_on_mg_payments = true   " + 
				"inner join   " + 
				"	sars_levy_scheme_year_returns yr on yr.for_scheme_year = o.scheme_year  " + 
				"inner join   " + 
				"	wsp_approved_check wsp on wsp.levy_number = o.ref_no and wsp.dhet_fin_year = o.scheme_year and wsp.wsp_status_enum = :wspStatusEnum  " + 
				"left join   " + 
				"	gp_grant_batch_entry gb on gb.sars_levy_details_id = o.id  " + 
				"where   " + 
				"	gb.sars_levy_details_id is null   " + 
				"	and (  " + 
				"		(o.mandatory_levy > 0 and yr.allow_returns_mandatory = true)  " + 
				"		or (o.mandatory_levy < 0 and yr.allow_invoices_mandatory = true)  " + 
				"	)" + 
				"  and o.scheme_year = :schemeYear " + 
				"Limit 1000 ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspStatusEnum", 4);
		parameters.put("schemeYear", schemeYear);
		return (List<MgTransactionsBean>) super.nativeSelectSqlList(hql, MgTransactionsBean.class, parameters);
	}
	
/*
select o.forMonth from haj.com.sars.SarsFiles o
order by o.forMonth desc
	 
select  count(distinct o.refNo)  from SarsLevyDetails o 
where o.schemeYear = 2016

select count(distinct o.refNo) from SarsEmployerDetail o
where o.sarsFiles.id in (select  distinct l.sarsFiles.id  from SarsLevyDetails l  where l.schemeYear = 2016)
and o.tradingStatus = 'A'

select o.tradingStatus , count(distinct o.refNo) from SarsEmployerDetail o
where o.sarsFiles.id in (select  distinct l.sarsFiles.id  from SarsLevyDetails l where l.schemeYear = 2016)
group by o.tradingStatus

select  count(distinct o.vendorId ) from TS2 o
where o.schemeYear = 2016

select  distinct l.sarsFiles.id  from SarsLevyDetails l 
where l.schemeYear = 2016				
*/

/*
select sum(o.mandatoryLevy) from SarsLevyDetails o
where o.arrivalDate1 <= '2017-02-01'
and o.refNo = 'L910704628'
and o.schemeYear = 2016

select sum(o.documentAmountD) from TS2 o
where o.vendorId = 'L910704628'
and o.schemeYear = 2017

select distinct o.schemeYear from SarsLevyDetails o
where o.schemeYear is not null
and  (o.schemeYear + 1) <= year(current_date) 
order by o.schemeYear
 */

}

