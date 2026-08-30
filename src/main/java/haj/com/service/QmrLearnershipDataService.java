package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.CounterBean;
import haj.com.bean.QmrQuarterCountBean;
import haj.com.bean.QmrScriptOneBean;
import haj.com.bean.QmrSummaryBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.QmrLearnershipDataDAO;
import haj.com.entity.QmrFinYears;
import haj.com.entity.QmrLearnershipData;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.QmrEnteredCompletedEnum;
import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrGenderEnum;
import haj.com.entity.enums.QmrTypeSelectionEnum;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.EquityService;

public class QmrLearnershipDataService extends AbstractService {
	
	/** The dao. */
	private QmrLearnershipDataDAO dao = new QmrLearnershipDataDAO();
	
	/** The Service Levels */
	private GenderService genderService = null;
	private EquityService equityService = null;
	private QmrFinYearsService qmrFinYearsService = null;
	
	/**
	 * Get all QmrLearnershipData
 	 * @author TechFinium 
 	 * @see   QmrLearnershipData
 	 * @return a list of {@link haj.com.entity.QmrLearnershipData}
	 * @throws Exception the exception
 	 */
	public List<QmrLearnershipData> allQmrLearnershipData() throws Exception {
	  	return dao.allQmrLearnershipData();
	}


	/**
	 * Create or update QmrLearnershipData.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QmrLearnershipData
	 */
	public void create(QmrLearnershipData entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QmrLearnershipData.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QmrLearnershipData
	 */
	public void update(QmrLearnershipData entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QmrLearnershipData.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QmrLearnershipData
	 */
	public void delete(QmrLearnershipData entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QmrLearnershipData}
	 * @throws Exception the exception
	 * @see    QmrLearnershipData
	 */
	public QmrLearnershipData findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QmrLearnershipData by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QmrLearnershipData}
	 * @throws Exception the exception
	 * @see    QmrLearnershipData
	 */
	public List<QmrLearnershipData> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QmrLearnershipData
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QmrLearnershipData}
	 * @throws Exception the exception
	 */
	public List<QmrLearnershipData> allQmrLearnershipData(int first, int pageSize) throws Exception {
		return dao.allQmrLearnershipData(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QmrLearnershipData for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QmrLearnershipData
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QmrLearnershipData.class);
	}
	
    /**
     * Lazy load QmrLearnershipData with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QmrLearnershipData class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QmrLearnershipData} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QmrLearnershipData> allQmrLearnershipData(Class<QmrLearnershipData> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<QmrLearnershipData>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of QmrLearnershipData for lazy load with filters
     * @author TechFinium 
     * @param entity QmrLearnershipData class
     * @param filters the filters
     * @return Number of rows in the QmrLearnershipData entity
     * @throws Exception the exception     
     */	
	public int count(Class<QmrLearnershipData> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/*
	 * Generate Summary Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrSummaryBean> generateSummaryReportLearnershipUnemployedEntered(Integer finYearPassed) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return generateSummaryReport(finYearPassed, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/*
	 * Generate Summary Report:
	 * Learner ship Unemployed Completed
	 */
	public List<QmrSummaryBean> generateSummaryReportLearnershipUnemployedCompleted(Integer finYearPassed) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return generateSummaryReport(finYearPassed, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/*
	 * Generate Summary Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrSummaryBean> generateSummaryReportLearnershipEmployedEntered(Integer finYearPassed) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);	
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return generateSummaryReport(finYearPassed, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/*
	 * Generate Summary Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrSummaryBean> generateSummaryReportLearnershipEmployedCompleted(Integer finYearPassed) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return generateSummaryReport(finYearPassed, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/**
	 * Main generation for Summary report for haj.com.entity.QmrLearnershipData
	 * 
	 * @param finYearPassed
	 * @param qmrTypeSelectionEnumList
	 * @param employmentStatusEnum
	 * @param learnerStatusEnumList
	 * @param qmrEnteredCompletedEnum
	 * @return List<QmrSummaryBean>
	 * @throws Exception
	 */
	private List<QmrSummaryBean> generateSummaryReport(Integer finYearPassed, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum,  
			List<LearnerStatusEnum> learnerStatusEnumList, QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception {
		List<QmrEquityEnum> qmrEquityEnumList;  
		List<QmrGenderEnum> qmrGenderEnumList;
		
		List<QmrSummaryBean> reportList = QmrGenericService.instance().getQmrSummaryReportTemplate();
		if (qmrFinYearsService == null) {
			qmrFinYearsService = new QmrFinYearsService();
		}
		List<QmrFinYears> allQuarters = qmrFinYearsService.findByFinYearsStart(finYearPassed);
		for (QmrSummaryBean report : reportList) {
			// loop through quarters to find results
			for (QmrFinYears qmrFinYears : allQuarters) {
				Integer finalResult = 0;
				switch (report.getQmrReportType()) {
				case DisabilityCount:
					//check if data generated
					if (qmrFinYears.getDataGenerated() == null || !qmrFinYears.getDataGenerated()) {
						// use generic scripts
						List<CounterBean> cb = QmrGenericService.instance().qmrScriptCountDisability(qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, 
								employmentStatusEnum, learnerStatusEnumList);
						if (!cb.isEmpty() && cb.get(0) != null && cb.get(0).getCounter() != null) {
							finalResult = cb.get(0).getCounter().intValue();
						}
					} else {
						// data generated use main entity
						finalResult = dao.countByDisabilityIdentified(qmrFinYears.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, true);
					}
					break;
				case EquityGender:
					//check if data generated
					if (qmrFinYears.getDataGenerated() == null || !qmrFinYears.getDataGenerated()) {
						// use generic scripts
						qmrEquityEnumList = new ArrayList<>();  
						qmrEquityEnumList.add(report.getQmrEquityEnum());
						
						qmrGenderEnumList = new ArrayList<>();
						qmrGenderEnumList.add(report.getQmrGenderEnum());
						List<CounterBean> cb = QmrGenericService.instance().qmrScriptGenerEquityCount(qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, 
								employmentStatusEnum, learnerStatusEnumList, qmrEquityEnumList, qmrGenderEnumList);
						if (!cb.isEmpty() && cb.get(0) != null && cb.get(0).getCounter() != null) {
							finalResult = cb.get(0).getCounter().intValue();
						}
					} else {
						// data generated use main entity
						finalResult = dao.countByEquityGender(qmrFinYears.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, report.getQmrGenderEnum(), report.getQmrEquityEnum());
					}
					break;
				case NonRSACitizenCount:
					//check if data generated
					if (qmrFinYears.getDataGenerated() == null || !qmrFinYears.getDataGenerated()) {
						// use generic scripts
						List<CounterBean> cb = QmrGenericService.instance().qmrScriptNonRsaCitizenCount(qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, 
								employmentStatusEnum, learnerStatusEnumList);
						if (!cb.isEmpty() && cb.get(0) != null && cb.get(0).getCounter() != null) {
							finalResult = cb.get(0).getCounter().intValue();
						}
					} else {
						// data generated use main entity
						finalResult = dao.countByRsaCitizen(qmrFinYears.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, false);
					}
					break;
				case YouthCount:
					//check if data generated
					if (qmrFinYears.getDataGenerated() == null || !qmrFinYears.getDataGenerated()) {
						// use generic scripts
						List<CounterBean> cb = QmrGenericService.instance().qmrScriptYouthCount(qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, 
								employmentStatusEnum, learnerStatusEnumList, HAJConstants.QMR_AGE_YOUTH);
						if (!cb.isEmpty() && cb.get(0) != null && cb.get(0).getCounter() != null) {
							finalResult = cb.get(0).getCounter().intValue();
						}
					} else {
						// data generated use main entity
						finalResult = dao.countByYouthIdentfied(qmrFinYears.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, HAJConstants.QMR_AGE_YOUTH);
					}
					break;
				default:
					break;
				}
				// save result
				switch (qmrFinYears.getFinYearQuarters()) {
				case QuarterOne:
					report.setQuarterOneCount(finalResult);
					break;
				case QuarterTwo:
					report.setQuarterTwoCount(finalResult);
					break;
				case QuarterThree:
					report.setQuarterThreeCount(finalResult);
					break;
				case QuarterFour:
					report.setQuarterFourCount(finalResult);
					break;
				default:
					break;
				}
			}
			// sum up total 
			Integer total = 0;
			if (report.getQuarterOneCount() != null && report.getQuarterOneCount() != 0)
				total+=report.getQuarterOneCount();
			if (report.getQuarterTwoCount() != null && report.getQuarterTwoCount() != 0)
				total+=report.getQuarterTwoCount();
			if (report.getQuarterThreeCount() != null && report.getQuarterThreeCount() != 0)
				total+=report.getQuarterThreeCount();
			if (report.getQuarterFourCount() != null && report.getQuarterFourCount() != 0)
				total+=report.getQuarterFourCount();
			
			report.setTotal(total);			
		}
		return reportList;
	}
	
	/*
	 * Generate Count Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrQuarterCountBean> generateCountReportLearnershipUnemployedEntered(QmrFinYears selectedQuarter) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return generateCountReport(selectedQuarter, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/*
	 * Generate Count Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrQuarterCountBean> generateCountReportLearnershipUnemployedCompleted(QmrFinYears selectedQuarter) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return generateCountReport(selectedQuarter, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/*
	 * Generate Count Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrQuarterCountBean> generateCountReportLearnershipEmployedEntered(QmrFinYears selectedQuarter) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);	
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return generateCountReport(selectedQuarter, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/*
	 * Generate Count Report:
	 * Learner ship Unemployed Entered
	 */
	public List<QmrQuarterCountBean> generateCountReportLearnershipEmployedCompleted(QmrFinYears selectedQuarter) throws Exception {
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return generateCountReport(selectedQuarter, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	
	/**
	 * Main generation for Summary report for haj.com.entity.QmrLearnershipData
	 * 
	 * @param finYearPassed
	 * @param qmrTypeSelectionEnumList
	 * @param employmentStatusEnum
	 * @param learnerStatusEnumList
	 * @param qmrEnteredCompletedEnum
	 * @return List<QmrSummaryBean>
	 * @throws Exception
	 */
	private List<QmrQuarterCountBean> generateCountReport(QmrFinYears selectedQuarter, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, 
				List<LearnerStatusEnum> learnerStatusEnumList, QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception {
		List<QmrGenderEnum> qmrGenderEnumList;
		List<QmrEquityEnum> qmrEquityEnumList;  
		
		List<QmrQuarterCountBean> reportList = QmrGenericService.instance().getQmrSummaryReportTemplateForQuarter();

		for (QmrQuarterCountBean report : reportList) {
			System.out.println("Inside For Loop::"+report.getQmrReportType());
			Integer maleCount = 0;
			Integer femaleCount = 0;
			switch (report.getQmrReportType()) {
			case DisabilityCount:
				//check if data generated
				if (selectedQuarter.getDataGenerated() == null || !selectedQuarter.getDataGenerated()) {
					// use generic scripts
					
					// male count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.MALE);
					List<CounterBean> cbMale = QmrGenericService.instance().qmrScriptCountDisabilityWithGender(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, qmrGenderEnumList);
					if (!cbMale.isEmpty() && cbMale.get(0) != null && cbMale.get(0).getCounter() != null) {
						maleCount = cbMale.get(0).getCounter().intValue();
					}
					
					// female count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.FEMALE);
					List<CounterBean> cbFemale = QmrGenericService.instance().qmrScriptCountDisabilityWithGender(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, qmrGenderEnumList);
					if (!cbFemale.isEmpty() && cbFemale.get(0) != null && cbFemale.get(0).getCounter() != null) {
						femaleCount = cbFemale.get(0).getCounter().intValue();
					}
					
				} else {
					// data generated use main entity
					// male
//					maleCount = dao.countByDisabilityIdentifiedWithGenderAndEquity(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, true, QmrGenderEnum.MALE, report.getQmrEquityEnum());
					maleCount = dao.countByDisabilityIdentifiedWithGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, true, QmrGenderEnum.MALE);
					// female
//					femaleCount = dao.countByDisabilityIdentifiedWithGenderAndEquity(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, true, QmrGenderEnum.FEMALE, report.getQmrEquityEnum());
					femaleCount = dao.countByDisabilityIdentifiedWithGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, true, QmrGenderEnum.FEMALE);
				}
				break;
			case EquityGender:
				//check if data generated
				if (selectedQuarter.getDataGenerated() == null || !selectedQuarter.getDataGenerated()) {
					// use generic scripts
					
					// male count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.MALE);
					qmrEquityEnumList = new ArrayList<>();  
					qmrEquityEnumList.add(report.getQmrEquityEnum());
					List<CounterBean> cbMale = QmrGenericService.instance().qmrScriptGenerEquityCount(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, qmrEquityEnumList, qmrGenderEnumList);
					if (!cbMale.isEmpty() && cbMale.get(0) != null && cbMale.get(0).getCounter() != null) {
						maleCount = cbMale.get(0).getCounter().intValue();
					}
					
					// female count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.FEMALE);
					qmrEquityEnumList = new ArrayList<>();  
					qmrEquityEnumList.add(report.getQmrEquityEnum());
					List<CounterBean> cbFemale = QmrGenericService.instance().qmrScriptGenerEquityCount(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, qmrEquityEnumList, qmrGenderEnumList);
					if (!cbFemale.isEmpty() && cbFemale.get(0) != null && cbFemale.get(0).getCounter() != null) {
						femaleCount = cbFemale.get(0).getCounter().intValue();
					}
					
				} else {
					// data generated use main entity
					// male count
					maleCount = dao.countByEquityGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, QmrGenderEnum.MALE, report.getQmrEquityEnum());
					// female count
					femaleCount = dao.countByEquityGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, QmrGenderEnum.FEMALE, report.getQmrEquityEnum());
				}
				break;
			case NonRSACitizenCount:
				//check if data generated
				if (selectedQuarter.getDataGenerated() == null || !selectedQuarter.getDataGenerated()) {
					// use generic scripts
					
					// male count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.MALE);
					List<CounterBean> cbMale = QmrGenericService.instance().qmrScriptNonRsaCitizenCountWithGender(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, qmrGenderEnumList);
					if (!cbMale.isEmpty() && cbMale.get(0) != null && cbMale.get(0).getCounter() != null) {
						maleCount = cbMale.get(0).getCounter().intValue();
					}
					
					// female count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.FEMALE);
					List<CounterBean> cbFemale = QmrGenericService.instance().qmrScriptNonRsaCitizenCountWithGender(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, qmrGenderEnumList);
					if (!cbFemale.isEmpty() && cbFemale.get(0) != null && cbFemale.get(0).getCounter() != null) {
						femaleCount = cbFemale.get(0).getCounter().intValue();
					}
					
				} else {
					// data generated use main entity
					// male count
//					maleCount = dao.countByRsaCitizenWithGenderAndEquity(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, false, QmrGenderEnum.MALE, report.getQmrEquityEnum());
					maleCount = dao.countByRsaCitizenWithGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, false, QmrGenderEnum.MALE);
					// female count
//					femaleCount = dao.countByRsaCitizenWithGenderAndEquity(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, false, QmrGenderEnum.FEMALE, report.getQmrEquityEnum());
					femaleCount = dao.countByRsaCitizenWithGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, false, QmrGenderEnum.FEMALE);
				}
				break;
			case YouthCount:
				//check if data generated
				if (selectedQuarter.getDataGenerated() == null || !selectedQuarter.getDataGenerated()) {
					// use generic scripts
					
					// male count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.MALE);
					List<CounterBean> cbMale = QmrGenericService.instance().qmrScriptYouthCountWithGender(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, HAJConstants.QMR_AGE_YOUTH, qmrGenderEnumList);
					if (!cbMale.isEmpty() && cbMale.get(0) != null && cbMale.get(0).getCounter() != null) {
						maleCount = cbMale.get(0).getCounter().intValue();
					}
					
					// female count
					qmrGenderEnumList = new ArrayList<>();
					qmrGenderEnumList.add(QmrGenderEnum.FEMALE);
					qmrEquityEnumList = new ArrayList<>();  
					qmrEquityEnumList.add(report.getQmrEquityEnum());
					List<CounterBean> cbFemale = QmrGenericService.instance().qmrScriptYouthCountWithGender(qmrEnteredCompletedEnum, selectedQuarter.getFromDate(), selectedQuarter.getToDate(), qmrTypeSelectionEnumList, 
							employmentStatusEnum, learnerStatusEnumList, HAJConstants.QMR_AGE_YOUTH, qmrGenderEnumList);
					if (!cbFemale.isEmpty() && cbFemale.get(0) != null && cbFemale.get(0).getCounter() != null) {
						femaleCount = cbFemale.get(0).getCounter().intValue();
					}
					
				} else {
					// data generated use main entity
					// male count
//					maleCount = dao.countByYouthIdentfiedWithGenderAndEquity(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, HAJConstants.QMR_AGE_YOUTH, QmrGenderEnum.MALE, report.getQmrEquityEnum());
					maleCount = dao.countByYouthIdentfiedWithGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, HAJConstants.QMR_AGE_YOUTH, QmrGenderEnum.MALE);
					// female count
//					femaleCount = dao.countByYouthIdentfiedWithGenderAndEquity(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, HAJConstants.QMR_AGE_YOUTH, QmrGenderEnum.FEMALE, report.getQmrEquityEnum());
					femaleCount = dao.countByYouthIdentfiedWithGender(selectedQuarter.getId(), employmentStatusEnum.get(0), qmrEnteredCompletedEnum, HAJConstants.QMR_AGE_YOUTH, QmrGenderEnum.FEMALE);
				}
				break;
			default:
				break;
			}
			
			if (maleCount != null) {
				report.setMaleCount(maleCount);
			}else {
				report.setMaleCount(0);
			}
			if (femaleCount != null) {
				report.setFemaleCount(femaleCount);
			} else {
				report.setFemaleCount(0);
			}
			
			// sum up total 
			Integer total = 0;
			if (report.getMaleCount() != null && report.getMaleCount() != 0)
				total+=report.getMaleCount();
			if (report.getFemaleCount() != null && report.getFemaleCount() != 0)
				total+=report.getFemaleCount();
			report.setTotal(total);			
		}
		
		return reportList;
	}
	
	/* 
	 * Pull Learner ship Unemployed Entered: Data Report  
	 * Not Saved Data
	 */
	public List<QmrScriptOneBean> runLearnershipUnemployedEnteredForQuarterNotSaved(QmrFinYears qmrFinYears) throws Exception{
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	public List<CounterBean> runLearnershipUnemployedEnteredForQuarterNotSavedCount(QmrFinYears qmrFinYears) throws Exception{
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return QmrGenericService.instance().qmrScriptCountTotalLearners(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Unemployed Entered: Data Report  
	 * Saved Data
	 */
	public List<QmrLearnershipData> fetchSavedDataLearnershipUnemployedEnteredForQuarter(QmrFinYears qmrFinYears) throws Exception{
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return dao.findDataForReport(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	public Integer fetchSavedDataLearnershipUnemployedEnteredForQuarterCount(QmrFinYears qmrFinYears) throws Exception{
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return dao.findDataForReportCount(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Unemployed Completed: Data Report
	 * Not Saved Data
	 */
	public List<QmrScriptOneBean> runLearnershipUnemployedCompletedForQuarterNotSaved(QmrFinYears qmrFinYears) throws Exception {
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	public List<CounterBean> runLearnershipUnemployedCompletedForQuarterNotSavedCount(QmrFinYears qmrFinYears) throws Exception {
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return QmrGenericService.instance().qmrScriptCountTotalLearners(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Unemployed Completed: Data Report
	 * Saved Data
	 */
	public List<QmrLearnershipData> fetchSavedDataLearnershipUnemployedCompletedForQuarter(QmrFinYears qmrFinYears) throws Exception{
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return dao.findDataForReport(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	public Integer fetchSavedDataLearnershipUnemployedCompletedForQuarterCount(QmrFinYears qmrFinYears) throws Exception{
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return dao.findDataForReportCount(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Employed Entered: Data Report
	 * Not Saved Data
	 */
	public List<QmrScriptOneBean> runLearnershipEmployedEnteredForQuarterNotSaved(QmrFinYears qmrFinYears) throws Exception {
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);	
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	public List<CounterBean> runLearnershipEmployedEnteredForQuarterNotSavedCount(QmrFinYears qmrFinYears) throws Exception {
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1);	
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return QmrGenericService.instance().qmrScriptCountTotalLearners(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Employed Entered: Data Report
	 * Saved Data
	 */
	public List<QmrLearnershipData> fetchSavedDataLearnershipEmployedEnteredForQuarter(QmrFinYears qmrFinYears) throws Exception {
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);	
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return dao.findDataForReport(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	public Integer fetchSavedDataLearnershipEmployedEnteredForQuarterCount(QmrFinYears qmrFinYears) throws Exception {
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);	
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		return dao.findDataForReportCount(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Employed Completed: Data Report
	 * Not Saved Data
	 */
	public List<QmrScriptOneBean> runLearnershipEmployedCompletedForQuarterNotSaved(QmrFinYears qmrFinYears) throws Exception {
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	public List<CounterBean> runLearnershipEmployedCompletedForQuarterNotSavedCount(QmrFinYears qmrFinYears) throws Exception {
		/* Prep data for report */
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP);
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		List<LearnerStatusEnum> learnerStatusEnumList = QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return QmrGenericService.instance().qmrScriptCountTotalLearners(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
	}
	
	/* 
	 * Pull Learner ship Employed Completed: Data Report
	 * Saved Data
	 */
	public List<QmrLearnershipData> fetchSavedDataLearnershipEmployedCompletedForQuarter(QmrFinYears qmrFinYears) throws Exception {
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return dao.findDataForReport(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	public Integer fetchSavedDataLearnershipEmployedCompletedForQuarterCount(QmrFinYears qmrFinYears) throws Exception {
		List<EmploymentStatusEnum> employmentStatusEnum = QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed);
		QmrEnteredCompletedEnum qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		return dao.findDataForReportCount(qmrFinYears.getId(), employmentStatusEnum, qmrEnteredCompletedEnum);
	}
	
	/**
	 * Generates List<haj.com.entity.QmrLearnershipData> for a quarter provided. 
	 * List to be created at the end of the quarter generation and not in method haj.com.service.QmrLearnershipDataService.generateQmrLearnershipDataForQuater(QmrFinYears)
	 * 
	 * @param qmrFinYears
	 * @return List<QmrLearnershipData>
	 * @throws Exception
	 */
	public List<QmrLearnershipData> generateQmrLearnershipDataForQuater(QmrFinYears qmrFinYears) throws Exception {
		// preps for learner ship quarter data
		List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = new ArrayList<>();
		qmrTypeSelectionEnumList.addAll(QmrGenericService.instance().addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum.LEARNERSHIP));
		
		List<EmploymentStatusEnum> employmentStatusEnum;
		List<LearnerStatusEnum> learnerStatusEnumList;
		QmrEnteredCompletedEnum  qmrEnteredCompletedEnum = null;
		List<QmrScriptOneBean> results = null;
		List<QmrLearnershipData> transformedResults = null;
		List<QmrLearnershipData> returnResults = new ArrayList<>();
		
		/* Learner ship Unemployed Entered */
		employmentStatusEnum = new ArrayList<>();
		employmentStatusEnum.addAll(QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed));
		learnerStatusEnumList = new ArrayList<>();
		learnerStatusEnumList.addAll(QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1));
		qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		results = QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
		if (!results.isEmpty()) {
			transformedResults = populateDataIntoReportingForm(results, qmrFinYears, qmrEnteredCompletedEnum, EmploymentStatusEnum.UnEmployed);
			if (!transformedResults.isEmpty()) {
				returnResults.addAll(transformedResults);
			}
		}
		
		/* Learner ship Unemployed Completed */
		employmentStatusEnum = new ArrayList<>();
		employmentStatusEnum.addAll(QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed));
		learnerStatusEnumList = new ArrayList<>();
		learnerStatusEnumList.addAll(QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2));
		qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		results = QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
		if (!results.isEmpty()) {
			transformedResults = populateDataIntoReportingForm(results, qmrFinYears, qmrEnteredCompletedEnum, EmploymentStatusEnum.UnEmployed);
			if (!transformedResults.isEmpty()) {
				returnResults.addAll(transformedResults);
			}
		}
		
		/* Learner ship Employed Entered */
		employmentStatusEnum = new ArrayList<>();
		employmentStatusEnum.addAll(QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed));
		learnerStatusEnumList = new ArrayList<>();
		learnerStatusEnumList.addAll(QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(1));
		qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Entered;
		results = QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
		if (!results.isEmpty()) {
			transformedResults = populateDataIntoReportingForm(results, qmrFinYears, qmrEnteredCompletedEnum, EmploymentStatusEnum.Employed);
			if (!transformedResults.isEmpty()) {
				returnResults.addAll(transformedResults);
			}
		}
		
		/* Learner ship Employed Completed */
		employmentStatusEnum = new ArrayList<>();
		employmentStatusEnum.addAll(QmrGenericService.instance().addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.Employed));
		learnerStatusEnumList = new ArrayList<>();
		learnerStatusEnumList.addAll(QmrGenericService.instance().prepLearnerStatusEnumBasedOnIndicator(2));
		qmrEnteredCompletedEnum = QmrEnteredCompletedEnum.Completed;
		results = QmrGenericService.instance().populateLearnershipUnemployedResultsByQuarterNotSaved(qmrFinYears, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEnteredCompletedEnum);
		if (!results.isEmpty()) {
			transformedResults = populateDataIntoReportingForm(results, qmrFinYears, qmrEnteredCompletedEnum, EmploymentStatusEnum.Employed);
			if (!transformedResults.isEmpty()) {
				returnResults.addAll(transformedResults);
			}
		}
		return returnResults;
	}
	
	/**
	 * Populates additional information against the entries for correct formatting to be saved and used for reporting
	 * @param extractedResults
	 * @param qmrFinYears
	 * @param qmrEnteredCompletedEnum
	 * @param employmentStatusEnum
	 * @return List<QmrLearnershipData>
	 * @throws Exception
	 */
	private List<QmrLearnershipData> populateDataIntoReportingForm(List<QmrScriptOneBean> extractedResults, QmrFinYears qmrFinYears, QmrEnteredCompletedEnum qmrEnteredCompletedEnum, EmploymentStatusEnum employmentStatusEnum) throws Exception {
		List<QmrLearnershipData> results = new ArrayList<>();
		for (QmrScriptOneBean bean : extractedResults) {
			QmrLearnershipData newEntry = new QmrLearnershipData();
			// copy over the property information
			org.apache.commons.beanutils.BeanUtils.copyProperties(newEntry, bean);
			
			// manually alter the follow data
			if (bean.getCompanyLearnerFlatID() != null) {
				newEntry.setCompanyLearnerFlatLink(bean.getCompanyLearnerFlatID().longValue());
			}
			if (bean.getAge() != null) {
				newEntry.setAgeOfLearner(bean.getAge().intValue());
				newEntry.setAgeIndicator(bean.getAge().intValue());
			}
			// set standard data and process
			newEntry.setQmrFinYears(qmrFinYears);
			newEntry.setQmrEnteredCompleted(qmrEnteredCompletedEnum);
			
			// populate gender
			if (genderService == null) {
				genderService = new GenderService();
			}
			if (newEntry.getGender() != null && newEntry.getGender().trim() != "") {
				Gender gender = genderService.findByGenderNameOneResult(newEntry.getGender());
				if (gender != null && gender.getQmrGender() != null) {
					newEntry.setQmrGender(gender.getQmrGender());
				}
			}			
			
			// populate equity
			if (equityService == null) {
				equityService = new EquityService();
			}
			if (newEntry.getRace() != null && newEntry.getGender().trim() != "") {
				Equity equity = equityService.findByNameReturnOneResult(newEntry.getRace());
				if (equity != null && equity.getQmrEquity() != null) {
					newEntry.setQmrEquity(equity.getQmrEquity());
				}
			}
			
			if (employmentStatusEnum != null) {
				newEntry.setEmployedUnEmployed(employmentStatusEnum);
			}
			
			if (bean.getDisability() != null && !bean.getDisability().trim().isEmpty()) {
				if (bean.getDisability().trim().equalsIgnoreCase("Yes")) {
					newEntry.setDisabilityIdentified(true);
				} else {
					newEntry.setDisabilityIdentified(false);
				}
			} else {
				newEntry.setDisabilityIdentified(false);
			}
			
			if (bean.getNonRsaCitizen() != null && !bean.getNonRsaCitizen().trim().isEmpty()) {
				if (bean.getNonRsaCitizen().equalsIgnoreCase("Citizen")) {
					newEntry.setRsaCitizen(true);
				}else {
					newEntry.setRsaCitizen(false);
				}
			} else {
				newEntry.setRsaCitizen(false);
			}
			results.add(newEntry);
		}
		return results;
	}
	
	public Integer countAllEntries(Long qmrFinYearId, EmploymentStatusEnum employedUnEmployed, QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception {
		return dao.countAllEntries(qmrFinYearId, employedUnEmployed, qmrEnteredCompletedEnum);
	}
}
