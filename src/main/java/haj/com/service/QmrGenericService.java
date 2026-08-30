package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.bean.CounterBean;
import haj.com.bean.QmrQuarterCountBean;
import haj.com.bean.QmrScriptFiveBean;
import haj.com.bean.QmrScriptFourBean;
import haj.com.bean.QmrScriptOneBean;
import haj.com.bean.QmrScriptThreeBean;
import haj.com.bean.QmrScriptTwoBean;
import haj.com.bean.QmrSummaryBean;
import haj.com.dao.QmrReportingDAO;
import haj.com.entity.QmrFinYears;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.QmrEnteredCompletedEnum;
import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrGenderEnum;
import haj.com.entity.enums.QmrReportType;
import haj.com.entity.enums.QmrTypeSelectionEnum;
import haj.com.framework.AbstractService;

/**
 * The Class QmrGenericService.
 * Actions used across all QMR related Services
 */
public class QmrGenericService extends AbstractService {
	
	/** DAO */
	private QmrReportingDAO qmrReportingDAO = new QmrReportingDAO();

	private static QmrGenericService qmrGenericService;
	public static synchronized QmrGenericService instance() {
		if (qmrGenericService == null) {
			qmrGenericService = new QmrGenericService();
		}
		return qmrGenericService;
	}
	
	/**
	 * Preps LearnerStatusEnum based on indicator provided
	 * 1 - Entered Status
	 * 2 - Completed Status
	 * @param indicator
	 * @return List<LearnerStatusEnum>
	 * @throws Exception
	 */
	public List<LearnerStatusEnum> prepLearnerStatusEnumBasedOnIndicator(int indicator) throws Exception {
		List<LearnerStatusEnum> resultsList = new ArrayList<>();
		switch (indicator) {
		case 1:
			resultsList.add(LearnerStatusEnum.Registered);
			break;
		case 2:
			resultsList.add(LearnerStatusEnum.Completed);
			resultsList.add(LearnerStatusEnum.QualificationObtained);
			break;
		default:
			break;
		}
		return resultsList;
	}
	
	public List<EmploymentStatusEnum> addEmploymentStatusEnumToArrayList(EmploymentStatusEnum entry) throws Exception{
		List<EmploymentStatusEnum> results = new ArrayList<>();
		if (entry != null) {
			results.add(entry);
		}
		return results;
	}
	
	public List<QmrTypeSelectionEnum> addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum entry) throws Exception{
		List<QmrTypeSelectionEnum> results = new ArrayList<>();
		if (entry != null) {
			results.add(entry);
		}
		return results;
	}
	
	/* 
	 * Generate the list for the report
	 * Date: 21 April 2020 
	 */
	public List<QmrSummaryBean> getQmrSummaryReportTemplate() {
		List<QmrSummaryBean> reportList = new ArrayList<>();
		
		/* gender & equity reporting section */
		QmrSummaryBean aficanMale = new QmrSummaryBean("AFRICAN MALE", QmrReportType.EquityGender, QmrGenderEnum.MALE, QmrEquityEnum.AFRICAN);
		reportList.add(aficanMale);
		aficanMale = null;
		
		QmrSummaryBean aficanFemale = new QmrSummaryBean("AFRICAN FEMALE", QmrReportType.EquityGender, QmrGenderEnum.FEMALE, QmrEquityEnum.AFRICAN);
		reportList.add(aficanFemale);
		aficanFemale = null;
		
		QmrSummaryBean whiteMale = new QmrSummaryBean("WHITE MALE", QmrReportType.EquityGender, QmrGenderEnum.MALE, QmrEquityEnum.WHITE);
		reportList.add(whiteMale);
		whiteMale = null;
		
		QmrSummaryBean whiteFemale = new QmrSummaryBean("WHITE FEMALE", QmrReportType.EquityGender, QmrGenderEnum.FEMALE, QmrEquityEnum.WHITE);
		reportList.add(whiteFemale);
		whiteFemale = null;
		
		QmrSummaryBean colouredMale = new QmrSummaryBean("COLOURED MALE", QmrReportType.EquityGender, QmrGenderEnum.MALE, QmrEquityEnum.COLOURED);
		reportList.add(colouredMale);
		colouredMale = null;
		
		QmrSummaryBean colouredFemale = new QmrSummaryBean("COLOURED FEMALE", QmrReportType.EquityGender, QmrGenderEnum.FEMALE, QmrEquityEnum.COLOURED);
		reportList.add(colouredFemale);
		colouredFemale = null;
		
		QmrSummaryBean indianMale = new QmrSummaryBean("INDIAN WHITE", QmrReportType.EquityGender, QmrGenderEnum.MALE, QmrEquityEnum.INDIAN);
		reportList.add(indianMale);
		indianMale = null;
		
		QmrSummaryBean indianFemale = new QmrSummaryBean("INDIAN FEMALE", QmrReportType.EquityGender, QmrGenderEnum.FEMALE, QmrEquityEnum.INDIAN);
		reportList.add(indianFemale);
		indianFemale = null;
	
		/* DisabilityCount reporting section */
		QmrSummaryBean disabilities = new QmrSummaryBean("PEOPLE WITH DISABILITIES", QmrReportType.DisabilityCount);
		reportList.add(disabilities);
		disabilities = null;
		
		/* YouthCount reporting section */
		QmrSummaryBean youth = new QmrSummaryBean("YOUTH", QmrReportType.YouthCount);
		reportList.add(youth);
		youth = null;
		
		/* NonRSACitizenCount reporting section */
		QmrSummaryBean nonRsaCitizen = new QmrSummaryBean("NON SA-CITIZENS", QmrReportType.NonRSACitizenCount);
		reportList.add(nonRsaCitizen);
		nonRsaCitizen = null;
		
		return reportList;
	}
	
	public List<QmrQuarterCountBean> getQmrSummaryReportTemplateForQuarter() {
		List<QmrQuarterCountBean> reportList = new ArrayList<>();
		/* gender & equity reporting section */
		QmrQuarterCountBean african = new QmrQuarterCountBean("AFRICAN", QmrReportType.EquityGender, QmrEquityEnum.AFRICAN);
		reportList.add(african);
		african = null;
		
		QmrQuarterCountBean coloured = new QmrQuarterCountBean("COLOURED", QmrReportType.EquityGender, QmrEquityEnum.COLOURED);
		reportList.add(coloured);
		coloured = null;
		
		QmrQuarterCountBean white = new QmrQuarterCountBean("WHITE", QmrReportType.EquityGender, QmrEquityEnum.WHITE);
		reportList.add(white);
		white = null;
		
		QmrQuarterCountBean indian = new QmrQuarterCountBean("INDIAN", QmrReportType.EquityGender, QmrEquityEnum.INDIAN);
		reportList.add(indian);
		indian = null;
		
		/* YouthCount reporting section */
		QmrQuarterCountBean youth = new QmrQuarterCountBean("YOUTH", QmrReportType.YouthCount);
		reportList.add(youth);
		youth = null;
		
		/* DisabilityCount reporting section */
		QmrQuarterCountBean disabilities = new QmrQuarterCountBean("DISABILITY", QmrReportType.DisabilityCount);
		reportList.add(disabilities);
		disabilities = null;
	
		/* NonRSACitizenCount reporting section */
		QmrQuarterCountBean nonRsaCitizen = new QmrQuarterCountBean("NON-SOUTH AFRICAN CITIZEN", QmrReportType.NonRSACitizenCount);
		reportList.add(nonRsaCitizen);
		nonRsaCitizen = null;
		
		return reportList;
	}
	
	/*
	 * Not saved data: Data report extract for script one
	 */
	public List<QmrScriptOneBean> populateLearnershipUnemployedResultsByQuarterNotSaved(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception{
		return qmrReportingDAO.qmrScriptOneGeneration(qmrFinYears.getRefNo(), qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Not saved data: Data report extract for script two
	 */
	public List<QmrScriptTwoBean> populateScriptTwoBeanByQuarterNotSaved(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception{
		return qmrReportingDAO.qmrScriptTwoGeneration(qmrFinYears.getRefNo(), qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Not saved data: Data report extract for script Three
	 */
	public List<QmrScriptThreeBean> populateScriptThreeBeanByQuarterNotSaved(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception{
		return qmrReportingDAO.qmrScriptThreeGeneration(qmrFinYears.getRefNo(), qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Not saved data: Data report extract for script Four
	 */
	public List<QmrScriptFourBean> populateScriptFourBeanByQuarterNotSaved(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception{
		return qmrReportingDAO.qmrScriptFourGeneration(qmrFinYears.getRefNo(), qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Not saved data: Data report extract for script Five
	 */
	public List<QmrScriptFiveBean> populateScriptFiveBeanByQuarterNotSaved(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception{
		return qmrReportingDAO.qmrScriptFiveGeneration(qmrFinYears.getRefNo(), qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Counter bean: By Equity and Gender count not saved data
	 */
	public List<CounterBean> qmrScriptGenerEquityCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList,  List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptGenerEquityCount(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEquityEnum, qmrGenderEnum);
	}
	
	/*
	 * Counter bean: Where age below or equal to age indicator not saved data
	 */
	public List<CounterBean> qmrScriptYouthCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Integer ageCount) throws Exception {
		return qmrReportingDAO.qmrScriptYouthCount(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, ageCount);
	}
	
	/*
	 * Counter bean: Where age below or equal to age indicator with gender and equity not saved data
	 */
	public List<CounterBean> qmrScriptYouthCountWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Integer ageCount, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptYouthCountWithGenderAndEquity(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, ageCount, qmrEquityEnum, qmrGenderEnum);
	}
	
	/*
	 * Count of users who age is equal or below Youth indicator for QMR Reporting (With Gender)
	 */
	public List<CounterBean> qmrScriptYouthCountWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Integer ageCount, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptYouthCountWithGender(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, ageCount, qmrGenderEnum);
	}
	
	/*
	 * Counter bean: Count of RSA Citizens
	 */
	public List<CounterBean> qmrScriptRsaCitizenCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		return qmrReportingDAO.qmrScriptRsaCitizenCount(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Counter bean: Count of Non-RSA Citizens
	 */
	public List<CounterBean> qmrScriptNonRsaCitizenCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		return qmrReportingDAO.qmrScriptNonRsaCitizenCount(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Counter bean: Count of Non-RSA Citizens with gender and equity
	 */
	public List<CounterBean> qmrScriptRsaCitizenCountWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptRsaCitizenCountWithGenderAndEquity(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEquityEnum, qmrGenderEnum);
	}
	
	/*
	 * Count of Non RSA Citizens with Gender And Equity
	 */
	public List<CounterBean> qmrScriptNonRsaCitizenCountWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptNonRsaCitizenCountWithGenderAndEquity(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEquityEnum, qmrGenderEnum);
	}
	
	/*
	 * Count of RSA Citizens with Gender
	 */
	public List<CounterBean> qmrScriptRsaCitizenCountWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptRsaCitizenCountWithGender(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrGenderEnum);
	}
	
	/*
	 * Count of Non RSA Citizens with Gender
	 */
	public List<CounterBean> qmrScriptNonRsaCitizenCountWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptNonRsaCitizenCountWithGender(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrGenderEnum);
	}
	
	/*
	 * Counter bean: Count number of Disability
	 */
	public List<CounterBean> qmrScriptCountDisability(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		return qmrReportingDAO.qmrScriptCountDisability(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
	}
	
	/*
	 * Counter bean: Count number of Disability with gender and equity
	 */
	public List<CounterBean> qmrScriptCountDisabilityWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptCountDisabilityWithGenderAndEquity(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrEquityEnum, qmrGenderEnum);
	}
	
	/*
	 * Counter bean: Count number of Disability with gender
	 */
	public List<CounterBean> qmrScriptCountDisabilityWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		return qmrReportingDAO.qmrScriptCountDisabilityWithGender(qmrEnteredCompletedEnum, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, qmrGenderEnum);
	}
	
	/*
	 * Counter bean: All Learners linked to raw data script 
	 */
	public List<CounterBean> qmrScriptCountTotalLearners(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum) throws Exception{
		return qmrReportingDAO.qmrScriptCountTotalLearners(qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, null);
	}
	
	/*
	 * Counter bean: All Bursary Learners linked to raw data script 
	 */
	public List<CounterBean> qmrScriptCountTotalLearnersBursery(QmrFinYears qmrFinYears, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList , QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Boolean newBursaryUser) throws Exception{
		return qmrReportingDAO.qmrScriptCountTotalLearners(qmrEnteredCompletedEnum, qmrFinYears.getFromDate(), qmrFinYears.getToDate(), qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList, newBursaryUser);
	}
	
}