package haj.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.SortOrder;

import haj.com.bean.CounterBean;
import haj.com.bean.NsdpExtractReportBean;
import haj.com.bean.NsdpSummaryReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.NsdpQuarterReportingDAO;
import haj.com.entity.NsdpQuarterReporting;
import haj.com.entity.QmrFinYears;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanySizeEnum;
import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.entity.lookup.FinancialYears;
import haj.com.entity.lookup.NsdpReportConfig;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.FinYearQuartersLookUpService;
import haj.com.service.lookup.NsdpReportConfigService;
import haj.com.utils.GenericUtility;

public class NsdpQuarterReportingService extends AbstractService {
	
	/** The dao. */
	private NsdpQuarterReportingDAO dao = new NsdpQuarterReportingDAO();
	
	/* Additional Reporting */
	private FinYearQuartersLookUpService finYearQuartersLookUpService = new FinYearQuartersLookUpService();
	private QmrFinYearsService qmrFinYearsService = new QmrFinYearsService();
	private NsdpReportConfigService nsdpReportConfigService;
	
	/* Services used for populating repotrs / counts */
	private WspService wspService = null;
	private QmrTVETDataService qmrTVETDataService = null;
	private QmrRPLDataService qmrRPLDataService = null;
	private QmrLearnershipDataService qmrLearnershipDataService = null;
	private QmrUniversityStudentDataService qmrUniversityStudentDataService = null;
	private QmrArtisanDataService qmrArtisanDataService = null;
	private QmrInternshipDataService qmrInternshipDataService = null;
	private QmrAETProgrammeDataService qmrAETProgrammeDataService = null;
	private QmrSkillsProgrammeDataService qmrSkillsProgrammeDataService = null;
	private QmrCandidacyProgrammeDataService qmrCandidacyProgrammeDataService = null;
	private QmrBursaryDataService qmrBursaryDataService = null;
	
	
	/**
	 * Get all NsdpQuarterReporting
 	 * @author TechFinium 
 	 * @see   NsdpQuarterReporting
 	 * @return a list of {@link haj.com.entity.NsdpQuarterReporting}
	 * @throws Exception the exception
 	 */
	public List<NsdpQuarterReporting> allNsdpQuarterReporting() throws Exception {
	  	return dao.allNsdpQuarterReporting();
	}


	/**
	 * Create or update NsdpQuarterReporting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NsdpQuarterReporting
	 */
	public void create(NsdpQuarterReporting entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	
	public void createUpdateWithUserInfo(NsdpQuarterReporting entity, Users sessionUser) throws Exception{
		if (sessionUser != null) {
			entity.setLastActionUser(sessionUser);
		}
		entity.setLastActionDate(getSynchronizedDate());
		create(entity);
	}


	/**
	 * Update  NsdpQuarterReporting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NsdpQuarterReporting
	 */
	public void update(NsdpQuarterReporting entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NsdpQuarterReporting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NsdpQuarterReporting
	 */
	public void delete(NsdpQuarterReporting entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NsdpQuarterReporting}
	 * @throws Exception the exception
	 * @see    NsdpQuarterReporting
	 */
	public NsdpQuarterReporting findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NsdpQuarterReporting by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NsdpQuarterReporting}
	 * @throws Exception the exception
	 * @see    NsdpQuarterReporting
	 */
	public List<NsdpQuarterReporting> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NsdpQuarterReporting
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NsdpQuarterReporting}
	 * @throws Exception the exception
	 */
	public List<NsdpQuarterReporting> allNsdpQuarterReporting(int first, int pageSize) throws Exception {
		return dao.allNsdpQuarterReporting(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NsdpQuarterReporting for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NsdpQuarterReporting
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NsdpQuarterReporting.class);
	}
	
    /**
     * Lazy load NsdpQuarterReporting with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NsdpQuarterReporting class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NsdpQuarterReporting} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> allNsdpQuarterReporting(Class<NsdpQuarterReporting> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NsdpQuarterReporting>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of NsdpQuarterReporting for lazy load with filters
     * @author TechFinium 
     * @param entity NsdpQuarterReporting class
     * @param filters the filters
     * @return Number of rows in the NsdpQuarterReporting entity
     * @throws Exception the exception     
     */	
	public int count(Class<NsdpQuarterReporting> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void updateCountsForReportDisplay(Long nsdpReportConfigId) throws Exception {
		List<NsdpQuarterReporting> list = findByNsdpReportConfigId(nsdpReportConfigId);
		populateDataForAutoGenReportsList(list);
	}
	
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> allNsdpQuarterReportingByNsdpReportConfigId( int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long nsdpReportConfigId) throws Exception {
		String hql = "select o from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId";
		filters.put("nsdpReportConfigId", nsdpReportConfigId);
		return populateDataForAutoGenReportsList((List<NsdpQuarterReporting>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllNsdpQuarterReportingByNsdpReportConfigId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId";
		return  dao.countWhere(filters, hql);
	}
	
	public Integer countByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
		return dao.countByNsdpReportConfigId(nsdpReportConfigId);
	}
	
	public Integer sumTotalAchivedByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
		return dao.sumTotalAchivedByNsdpReportConfigId(nsdpReportConfigId);
	}
	
	public Integer sumTargetsByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
		return dao.sumTargetsByNsdpReportConfigId(nsdpReportConfigId);
	}
	
	public List<NsdpQuarterReporting> findByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
		return dao.findByNsdpReportConfigId(nsdpReportConfigId);
	}
	
	public List<NsdpQuarterReporting> findByNsdpReportConfigIdOrderedByQuarter(Long nsdpReportConfigId) throws Exception {
		return dao.findByNsdpReportConfigIdOrderedByQuarter(nsdpReportConfigId);
	}
	
	public NsdpSummaryReportBean generateNsdpSummaryReportBean(NsdpReportConfig nsdpReportConfig) throws Exception{
		NsdpSummaryReportBean bean = new NsdpSummaryReportBean();
		bean.setOverallAchived(sumTotalAchivedByNsdpReportConfigId(nsdpReportConfig.getId()));
		if (bean.getOverallAchived() != null && bean.getOverallAchived() != 0 && nsdpReportConfig.getTargetAmount() != null && nsdpReportConfig.getTargetAmount() != 0) {
			double percentage = Double.valueOf(bean.getOverallAchived()) / Double.valueOf(nsdpReportConfig.getTargetAmount());
			bean.setOverallPercentage(percentage * 100);
			bean.setOverallVariance(nsdpReportConfig.getTargetAmount() - bean.getOverallAchived());
		} else {
			bean.setOverallPercentage(0d);
			if (nsdpReportConfig.getTargetAmount() != null) {
				bean.setOverallVariance(nsdpReportConfig.getTargetAmount());
			} else {
				bean.setOverallVariance(0);
			}
		}
		if (nsdpReportConfig.getTargetAmount() != null && sumTargetsByNsdpReportConfigId(nsdpReportConfig.getId()).equals(nsdpReportConfig.getTargetAmount())) {
			bean.setTargetsMatch(true);
		} else {
			bean.setTargetsMatch(false);
		}
		return bean;
	}
	
	public void generateForNsdpReportingConfig(NsdpReportConfig nsdpReportConfig, Users sessionUser) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		List<FinYearQuartersLookUp> allQuestersLookUp = finYearQuartersLookUpService.allFinYearQuartersLookUpOrderedByQuarters();
		for (FinYearQuartersLookUp finYearQuartersLookUp : allQuestersLookUp) {
			NsdpQuarterReporting newEntry = new NsdpQuarterReporting();
			newEntry.setFinancialYears(nsdpReportConfig.getFinancialYears());
			newEntry.setNsdpReportConfig(nsdpReportConfig);
			newEntry.setDataGenerated(false);
			newEntry.setFinYearQuarters(finYearQuartersLookUp.getFinYearQuarters());
			newEntry.setFinYearQuartersLookUpFlatKey(finYearQuartersLookUp.getId());
			newEntry.setQuarterTargetAmount(0);
			newEntry.setQuarterAchivedAmount(0);
			if (sessionUser != null) {
				newEntry.setLastActionUser(sessionUser);
				newEntry.setSystemUpdate(false);
			} else {
				newEntry.setSystemUpdate(true);
			}
			newEntry.setLastActionDate(getSynchronizedDate());
			Date fromDate = finYearQuartersLookUp.getFromDate();
			Date toDate = finYearQuartersLookUp.getToDate();
			if (finYearQuartersLookUp.getUseNextYear() == null || !finYearQuartersLookUp.getUseNextYear()) {
				// use currentYear Year
				newEntry.setFromDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(fromDate) + " " + nsdpReportConfig.getFinancialYears().getStartYear().toString()));
				newEntry.setToDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(toDate) + " " + nsdpReportConfig.getFinancialYears().getStartYear().toString()));
			} else {
				// use endOfFinYear Year
				newEntry.setFromDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(fromDate) + " " + nsdpReportConfig.getFinancialYears().getEndYear().toString()));
				newEntry.setToDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(toDate) + " " + nsdpReportConfig.getFinancialYears().getEndYear().toString()));
			}
			createList.add(newEntry);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}
	
	public List<NsdpQuarterReporting> populateDataForAutoGenReportsList(List<NsdpQuarterReporting> nsdpQuarterReportingList) throws Exception{
		for (NsdpQuarterReporting nsdpQuarterReporting : nsdpQuarterReportingList) {
			populateDataForAutoGenReports(nsdpQuarterReporting);
		}
		return nsdpQuarterReportingList;
	}
	
	public NsdpQuarterReporting populateDataForAutoGenReports(NsdpQuarterReporting quarterReport) throws Exception {
		boolean updateEntry = false;
		if (quarterReport.getNsdpReportConfig() != null && quarterReport.getFinancialYears() != null) {
			if (quarterReport.getNsdpReportConfig().getManualCapture() != null && !quarterReport.getNsdpReportConfig().getManualCapture() && quarterReport.getNsdpReportConfig().getNsdpReportRunType() != null) {
				if (quarterReport.getDataGenerated() == null || !quarterReport.getDataGenerated()) {
					
					if (quarterReport.getFromDate().after(getSynchronizedDate())) {
						updateEntry = true;
						quarterReport.setQuarterAchivedAmount(0);
					} else {
						// fetch QMR Fin Year to compare if data generated
						if (qmrFinYearsService == null) {
							qmrFinYearsService = new QmrFinYearsService();
						}
						QmrFinYears qmrFinYear = qmrFinYearsService.findListByFinYearAndQuarterReturnOne(quarterReport.getFinancialYears().getId(), quarterReport.getFinYearQuarters());
						switch (quarterReport.getNsdpReportConfig().getNsdpReportRunType()) {
						case GrantsApprovedSmallCompany:
							if (wspService == null) {
								wspService = new WspService();
							}
							List<CounterBean> resultsListGrantsApprovedSmallCompany = new ArrayList<>();
							if (quarterReport.getFinancialYears().getGrantFocusYear() != null) {
								Date formDate = new Date();
								Date toDate = new Date();
								
								if (quarterReport.getFinYearQuarters() != null && quarterReport.getFinYearQuarters() == FinYearQuartersEnum.QuarterOne) {
									// instead of 01 April must start be 01 Feb for Quarter 1 
									formDate = GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(quarterReport.getFromDate(), 2)));
								} else {
									formDate = GenericUtility.getStartOfDay(quarterReport.getFromDate());
								}
								toDate = GenericUtility.getEndOfDay(quarterReport.getToDate());
								
								resultsListGrantsApprovedSmallCompany = wspService.countGrantApprovalsByGrantYearAndCompanySize(quarterReport.getFinancialYears().getGrantFocusYear(), CompanySizeEnum.Small, 
										formDate, toDate, WspStatusEnum.getWspStatusEnumApprovedList(), 
										WspTypeEnum.getNsdpReportWspTypeList());
							}
							
							assignQuarterValue(quarterReport, resultsListGrantsApprovedSmallCompany);
							resultsListGrantsApprovedSmallCompany.clear();
							if (getSynchronizedDate().after(quarterReport.getToDate())) {
								quarterReport.setDataGenerated(true);
							}
							updateEntry = true;
							break;
						case GrantsApprovedMediumCompany:
							if (wspService == null) {
								wspService = new WspService();
							}
							List<CounterBean> resultsListGrantsApprovedMediumCompany = new ArrayList<>();
							if (quarterReport.getFinancialYears().getGrantFocusYear() != null) {
								Date formDate = new Date();
								Date toDate = new Date();
								
								if (quarterReport.getFinYearQuarters() != null && quarterReport.getFinYearQuarters() == FinYearQuartersEnum.QuarterOne) {
									// instead of 01 April must start be 01 Feb for Quarter 1 
									formDate = GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(quarterReport.getFromDate(), 2)));
								} else {
									formDate = GenericUtility.getStartOfDay(quarterReport.getFromDate());
								}
								toDate = GenericUtility.getEndOfDay(quarterReport.getToDate());
								
								
								resultsListGrantsApprovedMediumCompany = wspService.countGrantApprovalsByGrantYearAndCompanySize(quarterReport.getFinancialYears().getGrantFocusYear(), CompanySizeEnum.Medium, 
										formDate, toDate, WspStatusEnum.getWspStatusEnumApprovedList(), 
										WspTypeEnum.getNsdpReportWspTypeList());
							}
							assignQuarterValue(quarterReport, resultsListGrantsApprovedMediumCompany);
							resultsListGrantsApprovedMediumCompany.clear();
							if (getSynchronizedDate().after(quarterReport.getToDate())) {
								quarterReport.setDataGenerated(true);
							}
							updateEntry = true;
							break;
						case GrantsApprovedLargeCompany:
							if (wspService == null) {
								wspService = new WspService();
							}
							List<CounterBean> resultsListGrantsApprovedLargeCompany = new ArrayList<>();
							if (quarterReport.getFinancialYears().getGrantFocusYear() != null) {
								Date formDate = new Date();
								Date toDate = new Date();
								
								if (quarterReport.getFinYearQuarters() != null && quarterReport.getFinYearQuarters() == FinYearQuartersEnum.QuarterOne) {
									// instead of 01 April must start be 01 Feb for Quarter 1 
									formDate = GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(quarterReport.getFromDate(), 2)));
								} else {
									formDate = GenericUtility.getStartOfDay(quarterReport.getFromDate());
								}
								toDate = GenericUtility.getEndOfDay(quarterReport.getToDate());
								
								resultsListGrantsApprovedLargeCompany = wspService.countGrantApprovalsByGrantYearAndCompanySize(quarterReport.getFinancialYears().getGrantFocusYear(), CompanySizeEnum.Large, 
										formDate, toDate, WspStatusEnum.getWspStatusEnumApprovedList(), 
										WspTypeEnum.getNsdpReportWspTypeList());
							}
							assignQuarterValue(quarterReport, resultsListGrantsApprovedLargeCompany);
							resultsListGrantsApprovedLargeCompany.clear();
							if (getSynchronizedDate().after(quarterReport.getToDate())) {
								quarterReport.setDataGenerated(true);
							}
							updateEntry = true;
							break;
						case QmrTvetEntered:
							if (qmrTVETDataService == null) {
								qmrTVETDataService = new QmrTVETDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrTVETDataService.fetchSavedDataTVETEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> qmrTvetCompletedList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								qmrTvetCompletedList = qmrTVETDataService.runTVETEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, qmrTvetCompletedList);
								qmrTvetCompletedList.clear();
							}
							updateEntry = true;
							break;
						case QmrTvetCompleted:
							if (qmrTVETDataService == null) {
								qmrTVETDataService = new QmrTVETDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrTVETDataService.fetchSavedDataTVETCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> qmrTvetCompletedList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								qmrTvetCompletedList = qmrTVETDataService.runTVETCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, qmrTvetCompletedList);
								qmrTvetCompletedList.clear();
							}
							updateEntry = true;
							break;
						case QmrArpl:
							if (qmrRPLDataService == null) {
								qmrRPLDataService = new QmrRPLDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrRPLDataService.fetchSavedDataRPLEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrRPLDataService.runRPLEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedLearnershipsEntered:
							if (qmrLearnershipDataService == null) {
								qmrLearnershipDataService = new QmrLearnershipDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrLearnershipDataService.fetchSavedDataLearnershipUnemployedEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrLearnershipDataService.runLearnershipUnemployedEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedLearnershipsCompleted:
							if (qmrLearnershipDataService == null) {
								qmrLearnershipDataService = new QmrLearnershipDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrLearnershipDataService.fetchSavedDataLearnershipUnemployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrLearnershipDataService.runLearnershipUnemployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingLearnershipsEntered:
							if (qmrLearnershipDataService == null) {
								qmrLearnershipDataService = new QmrLearnershipDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrLearnershipDataService.fetchSavedDataLearnershipEmployedEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrLearnershipDataService.runLearnershipEmployedEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingLearnershipsCompleted:
							if (qmrLearnershipDataService == null) {
								qmrLearnershipDataService = new QmrLearnershipDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrLearnershipDataService.fetchSavedDataLearnershipEmployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrLearnershipDataService.runLearnershipEmployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUniversityStudentPlacementEntered:
							if (qmrUniversityStudentDataService == null) {
								qmrUniversityStudentDataService = new QmrUniversityStudentDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrUniversityStudentDataService.fetchSavedDataUniversityStudentEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrUniversityStudentDataService.runUniversityStudentEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUniversityStudentPlacementCompleted:
							if (qmrUniversityStudentDataService == null) {
								qmrUniversityStudentDataService = new QmrUniversityStudentDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrUniversityStudentDataService.fetchSavedDataUniversityStudentCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrUniversityStudentDataService.runUniversityStudentCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrArtisanEntered:
							if (qmrArtisanDataService == null) {
								qmrArtisanDataService = new QmrArtisanDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrArtisanDataService.fetchSavedDataArtisanEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrArtisanDataService.runArtisanEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrArtisanCompleted:
							if (qmrArtisanDataService == null) {
								qmrArtisanDataService = new QmrArtisanDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrArtisanDataService.fetchSavedDataArtisanCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrArtisanDataService.runArtisanCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedInternshipEntered:
							if (qmrInternshipDataService == null) {
								qmrInternshipDataService = new QmrInternshipDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrInternshipDataService.fetchSavedDataInternshipUnemployedEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrInternshipDataService.runInternshipUnemployedEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedInternshipCompleted:
							if (qmrInternshipDataService == null) {
								qmrInternshipDataService = new QmrInternshipDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrInternshipDataService.fetchSavedDataInternshipUnemployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrInternshipDataService.runInternshipUnemployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrAetProgrammeEntered:
							if (qmrAETProgrammeDataService == null) {
								qmrAETProgrammeDataService = new QmrAETProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrAETProgrammeDataService.fetchSavedDataAETProgrammeEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrAETProgrammeDataService.runAETProgrammeEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrAetProgrammeCompleted:
							if (qmrAETProgrammeDataService == null) {
								qmrAETProgrammeDataService = new QmrAETProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrAETProgrammeDataService.fetchSavedDataAETProgrammeCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrAETProgrammeDataService.runAETProgrammeCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedSkillsProgrammeEntered:
							if (qmrSkillsProgrammeDataService == null) {
								qmrSkillsProgrammeDataService = new QmrSkillsProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrSkillsProgrammeDataService.fetchSavedDataSkillsProgrammeUnemployedEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrSkillsProgrammeDataService.runSkillsProgrammeUnemployedEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedSkillsProgrammeCompleted:
							if (qmrSkillsProgrammeDataService == null) {
								qmrSkillsProgrammeDataService = new QmrSkillsProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrSkillsProgrammeDataService.fetchSavedDataSkillsProgrammeUnemployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrSkillsProgrammeDataService.runSkillsProgrammeUnemployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingSkillsProgrammeEntered:
							if (qmrSkillsProgrammeDataService == null) {
								qmrSkillsProgrammeDataService = new QmrSkillsProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrSkillsProgrammeDataService.fetchSavedDataSkillsProgrammeEmployedEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrSkillsProgrammeDataService.runSkillsProgrammeEmployedEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingSkillsProgrammeCompleted:
							if (qmrSkillsProgrammeDataService == null) {
								qmrSkillsProgrammeDataService = new QmrSkillsProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrSkillsProgrammeDataService.fetchSavedDataSkillsProgrammeEmployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrSkillsProgrammeDataService.runSkillsProgrammeEmployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrCandidacyProgrammeEntered:
							if (qmrCandidacyProgrammeDataService == null) {
								qmrCandidacyProgrammeDataService = new QmrCandidacyProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrCandidacyProgrammeDataService.fetchSavedDataCandidacyProgrammeEnteredForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrCandidacyProgrammeDataService.runCandidacyProgrammeEnteredForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrCandidacyProgrammeCompleted:
							if (qmrCandidacyProgrammeDataService == null) {
								qmrCandidacyProgrammeDataService = new QmrCandidacyProgrammeDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrCandidacyProgrammeDataService.fetchSavedDataCandidacyProgrammeCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrCandidacyProgrammeDataService.runCandidacyProgrammeCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedBursaryEnteredNew:
							if (qmrBursaryDataService == null) {
								qmrBursaryDataService = new QmrBursaryDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrBursaryDataService.fetchSavedDataBursaryUnemployedEnteredForQuarterCountNew(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrBursaryDataService.runBursaryUnemployedEnteredForQuarterNotSavedCountNew(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedBursaryEnteredContinue:
							if (qmrBursaryDataService == null) {
								qmrBursaryDataService = new QmrBursaryDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrBursaryDataService.fetchSavedDataBursaryUnemployedEnteredForQuarterCountContinuing(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrBursaryDataService.runBursaryUnemployedEnteredForQuarterNotSavedCountContinuing(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrUnemployedBursaryCompleted:
							if (qmrBursaryDataService == null) {
								qmrBursaryDataService = new QmrBursaryDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrBursaryDataService.fetchSavedDataBursaryUnemployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrBursaryDataService.runBursaryUnemployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingBursaryEnteredNew:
							if (qmrBursaryDataService == null) {
								qmrBursaryDataService = new QmrBursaryDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrBursaryDataService.fetchSavedDataBursaryEmployedEnteredForQuarterCountNew(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrBursaryDataService.runBursaryEmployedEnteredForQuarterNotSavedCountNew(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingBursaryEnteredContinue:
							if (qmrBursaryDataService == null) {
								qmrBursaryDataService = new QmrBursaryDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrBursaryDataService.fetchSavedDataBursaryEmployedEnteredForQuarterCountContinuing(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrBursaryDataService.runBursaryEmployedEnteredForQuarterNotSavedCountContinuing(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						case QmrWorkingBursaryCompleted:
							if (qmrBursaryDataService == null) {
								qmrBursaryDataService = new QmrBursaryDataService();
							}
							if (qmrFinYear.getDataGenerated() != null && qmrFinYear.getDataGenerated()) {
								quarterReport.setDataGenerated(true);
								// look at saved data
								quarterReport.setQuarterAchivedAmount(qmrBursaryDataService.fetchSavedDataBursaryEmployedCompletedForQuarterCount(qmrFinYear));
							} else {
								List<CounterBean> resultsList = new ArrayList<>();
								quarterReport.setDataGenerated(false);
								// look at count script
								resultsList = qmrBursaryDataService.runBursaryEmployedCompletedForQuarterNotSavedCount(qmrFinYear);
								assignQuarterValue(quarterReport, resultsList);
								resultsList.clear();
							}
							updateEntry = true;
							break;
						default:
							break;
						}
					}
				}
			}
		}
		if (updateEntry) {
			quarterReport.setDataAutoPopulationRan(getSynchronizedDate());
			update(quarterReport);
		}
		return quarterReport;
	}


	/**
	 * @param quarterReport
	 * @param qmrTvetCompletedList
	 */
	public void assignQuarterValue(NsdpQuarterReporting quarterReport, List<CounterBean> qmrTvetCompletedList) {
		if (qmrTvetCompletedList.isEmpty()) {
			quarterReport.setQuarterAchivedAmount(0);
		} else {
			if (qmrTvetCompletedList.get(0).getCounter() != null) {
				quarterReport.setQuarterAchivedAmount(qmrTvetCompletedList.get(0).getCounter().intValue());
			} else {
				quarterReport.setQuarterAchivedAmount(0);
			}
		}
	}
	
	public void downloadNsdpReportIntoExcel(FinancialYears financialYears, NsdpReportConfig nsdpReportConfig, Users sessionUser) throws Exception {
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		// NsdpExtractReportBean
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "NUMBER", "FINANCIAL YEAR", "NSDP OUTCOMES", "NSDP SUB-OUTCOMES", "OUTPUT INDICATORS", "APP/SLA TARGETS", "TOTAL Q1 TARGET", "TOTAL ACHIEVED AT Q1", "TOTAL Q2 TARGET", "TOTAL ACHIEVED AT Q2", "TOTAL Q3 TARGET", "TOTAL ACHIEVED AT Q3", "TOTAL Q4 TARGET", "TOTAL ACHIEVED AT Q4", "OVERALL ACHIEVED AT Q4", "PERCENTAGES %", "VARIANCE"});
		counter++;
		// populate data
		populateDataForReport(data, financialYears, nsdpReportConfig, counter, sessionUser);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "NSDP Report Download.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void populateDataForReport(Map<String, Object[]> data, FinancialYears financialYears, NsdpReportConfig nsdpReportConfig, Integer counter, Users sessionUser) throws Exception{
		int counterForPopulation = counter;
		if (financialYears != null) {
			// generate report for all financial years data
			if (nsdpReportConfigService == null) {
				nsdpReportConfigService = new NsdpReportConfigService();
			}
			List<NsdpReportConfig> configForFinYear = nsdpReportConfigService.findByFinancialYearsIdWithOrderBy(financialYears.getId());
			for (NsdpReportConfig entry : configForFinYear) {
//				System.out.println("Order Number: "+entry.getOrderNumber() +". Description: " + entry.getDescription());
				populateData(data, entry, counterForPopulation, sessionUser);
//				System.out.println("Counter: " + counterForPopulation);
				counterForPopulation++;
			}
		} else if (nsdpReportConfig != null) {
			// generate report for one report
			populateData(data, nsdpReportConfig, counterForPopulation, sessionUser);
			counterForPopulation++;
		}
		counter = counterForPopulation;
	}
	
	private void populateData(Map<String, Object[]> data, NsdpReportConfig nsdpReportConfig, Integer counter, Users sessionUser) throws Exception{
		// map to object
		Integer position = (counter - 1);
		
		// populate default info for report
		NsdpExtractReportBean bean = new NsdpExtractReportBean(((nsdpReportConfig.getOrderNumber() != null) ? nsdpReportConfig.getOrderNumber() : position) ,nsdpReportConfig.getFinancialYears().getDescription(), nsdpReportConfig.getNsdpOutcomes(), nsdpReportConfig.getNsdpSubOutcomes(), nsdpReportConfig.getDescription(), nsdpReportConfig.getTargetAmount());
		
		int annualTarget = bean.getAnnualTraget();
		int totalAchived = 0;
		if (countByNsdpReportConfigId(nsdpReportConfig.getId()) == 0) {
			generateForNsdpReportingConfig(nsdpReportConfig, sessionUser);
		}
		List<NsdpQuarterReporting> results = findByNsdpReportConfigIdOrderedByQuarter(nsdpReportConfig.getId());
		for (NsdpQuarterReporting reportEntry : results) {
			// populate data
			populateDataForAutoGenReports(reportEntry);
			
			// add total achieved
			if (reportEntry.getQuarterAchivedAmount() != null) {
				totalAchived += reportEntry.getQuarterAchivedAmount();
			}
			
			// populate quarter information
			switch (reportEntry.getFinYearQuarters()) {
			case QuarterOne:
				if (reportEntry.getQuarterAchivedAmount() != null) {
					bean.setQuarterOneAchived(reportEntry.getQuarterAchivedAmount());
				}
				if (reportEntry.getQuarterTargetAmount() != null) {
					bean.setQuarterOneTarget(reportEntry.getQuarterTargetAmount());
				}
				break;
			case QuarterTwo:
				if (reportEntry.getQuarterAchivedAmount() != null) {
					bean.setQuarterTwoAchived(reportEntry.getQuarterAchivedAmount());
				}
				if (reportEntry.getQuarterTargetAmount() != null) {
					bean.setQuarterTwoTarget(reportEntry.getQuarterTargetAmount());
				}		
				break;
			case QuarterThree:
				if (reportEntry.getQuarterAchivedAmount() != null) {
					bean.setQuarterThreeAchived(reportEntry.getQuarterAchivedAmount());
				}
				if (reportEntry.getQuarterTargetAmount() != null) {
					bean.setQuarterThreeTarget(reportEntry.getQuarterTargetAmount());
				}
				break;
			case QuarterFour:
				if (reportEntry.getQuarterAchivedAmount() != null) {
					bean.setQuarterFourAchived(reportEntry.getQuarterAchivedAmount());
				}
				if (reportEntry.getQuarterTargetAmount() != null) {
					bean.setQuarterFourTarget(reportEntry.getQuarterTargetAmount());
				}
				break;
			default:
				break;
			}
		}
		bean.setOverallAchived(totalAchived);
		
		// calc percentage
		if (annualTarget != 0 && totalAchived != 0) {
			double percentage = Double.valueOf(totalAchived) / Double.valueOf(annualTarget);
			bean.setPercentageAchived(percentage * 100);
		} else {
			bean.setPercentageAchived(0.0d);
		}
		
		// calc variance
		if (totalAchived != 0) {
			bean.setVarinaceAchived(annualTarget - totalAchived);
		} else {
			bean.setVarinaceAchived(annualTarget);
		}
		
		// new Object[] { "FINANCIAL YEAR", "NSDP OUTCOMES", "NSDP SUB-OUTCOMES", "OUTPUT INDICATORS", "APP/SLA TARGETS", "TOTAL Q1 TARGET", "TOTAL ACHIEVED AT Q1", "TOTAL Q2 TARGET", "TOTAL ACHIEVED AT Q2", "TOTAL Q3 TARGETg", "TOTAL ACHIEVED AT Q3", "TOTAL Q4 TARGET", "TOTAL ACHIEVED AT Q4", "OVERALL ACHIEVED AT Q4", "PERCENTAGES %", "VARIANCE"})
		data.put(counter.toString(), new Object[] { bean.getOrderNumber(), bean.getFinancialYear(), bean.getNsdpOutcomes(), bean.getNsdpSubOutcomes(), bean.getOutputIndicators(), bean.getAnnualTraget(), bean.getQuarterOneTarget(), bean.getQuarterOneAchived(), bean.getQuarterTwoTarget(), bean.getQuarterTwoAchived(), bean.getQuarterThreeTarget(), bean.getQuarterThreeAchived(), bean.getQuarterFourTarget(), bean.getQuarterFourAchived(), bean.getOverallAchived(), bean.getPercentageAchived(), bean.getVarinaceAchived() });
		
	}
}
