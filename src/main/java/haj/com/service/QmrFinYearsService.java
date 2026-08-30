package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.QmrFinYearsDAO;
import haj.com.entity.QmrAETProgrammeData;
import haj.com.entity.QmrArtisanData;
import haj.com.entity.QmrBursaryData;
import haj.com.entity.QmrCandidacyProgrammeData;
import haj.com.entity.QmrFinYears;
import haj.com.entity.QmrIndividualUnitStandardData;
import haj.com.entity.QmrInternshipData;
import haj.com.entity.QmrLearnershipData;
import haj.com.entity.QmrLecturerDevelopmentData;
import haj.com.entity.QmrRPLData;
import haj.com.entity.QmrSkillsProgrammeData;
import haj.com.entity.QmrTVETData;
import haj.com.entity.QmrUniversityStudentData;
import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.entity.lookup.FinancialYears;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.FinYearQuartersLookUpService;
import haj.com.service.lookup.FinancialYearsService;
import haj.com.utils.GenericUtility;

public class QmrFinYearsService extends AbstractService {
	
	/** The dao. */
	private QmrFinYearsDAO dao = new QmrFinYearsDAO();
	
	/** Additional Service Levels */
	private FinancialYearsService financialYearsService = null;
	private FinYearQuartersLookUpService finYearQuartersLookUpService = new FinYearQuartersLookUpService(); 
	
	/** Data Generation Service Levels */
	private QmrLearnershipDataService qmrLearnershipDataService = new QmrLearnershipDataService();
	private QmrBursaryDataService qmrBursaryDataService = new QmrBursaryDataService();
	private QmrSkillsProgrammeDataService qmrSkillsProgrammeDataService = new QmrSkillsProgrammeDataService();
	private QmrInternshipDataService qmrInternshipDataService = new QmrInternshipDataService();
	private QmrArtisanDataService qmrArtisanDataService = new QmrArtisanDataService();
	private QmrCandidacyProgrammeDataService qmrCandidacyProgrammeDataService = new QmrCandidacyProgrammeDataService();
	private QmrRPLDataService qmRplDataService = new QmrRPLDataService();
	private QmrTVETDataService qmrTVETDataService = new QmrTVETDataService();
	private QmrUniversityStudentDataService qmrUniversityStudentDataService = new QmrUniversityStudentDataService();
	private QmrLecturerDevelopmentDataService qmrLecturerDevelopmentDataService = new QmrLecturerDevelopmentDataService();
	private QmrAETProgrammeDataService qmrAETProgrammeDataService = new QmrAETProgrammeDataService();
	private QmrIndividualUnitStandardDataService qmrIndividualUnitStandardDataService = new QmrIndividualUnitStandardDataService();
	

	/**
	 * Get all QmrFinYears
 	 * @author TechFinium 
 	 * @see   QmrFinYears
 	 * @return a list of {@link haj.com.entity.QmrFinYears}
	 * @throws Exception the exception
 	 */
	public List<QmrFinYears> allQmrFinYears() throws Exception {
	  	return dao.allQmrFinYears();
	}


	/**
	 * Create or update QmrFinYears.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QmrFinYears
	 */
	public void create(QmrFinYears entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QmrFinYears.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QmrFinYears
	 */
	public void update(QmrFinYears entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QmrFinYears.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QmrFinYears
	 */
	public void delete(QmrFinYears entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QmrFinYears}
	 * @throws Exception the exception
	 * @see    QmrFinYears
	 */
	public QmrFinYears findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QmrFinYears by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QmrFinYears}
	 * @throws Exception the exception
	 * @see    QmrFinYears
	 */
	public List<QmrFinYears> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QmrFinYears
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QmrFinYears}
	 * @throws Exception the exception
	 */
	public List<QmrFinYears> allQmrFinYears(int first, int pageSize) throws Exception {
		return dao.allQmrFinYears(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QmrFinYears for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QmrFinYears
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QmrFinYears.class);
	}
	
    /**
     * Lazy load QmrFinYears with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QmrFinYears class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QmrFinYears} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> allQmrFinYears(Class<QmrFinYears> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QmrFinYears>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of QmrFinYears for lazy load with filters
     * @author TechFinium 
     * @param entity QmrFinYears class
     * @param filters the filters
     * @return Number of rows in the QmrFinYears entity
     * @throws Exception the exception     
     */	
	public int count(Class<QmrFinYears> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QmrFinYears> allQmrFinYearsByFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYearSelected) throws Exception {
		String hql = "select o from QmrFinYears o where o.finYearStart = :finYearSelected ";
		filters.put("finYearSelected", finYearSelected);
		return (List<QmrFinYears>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllQmrFinYearsByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from QmrFinYears o where o.finYearStart = :finYearSelected ";
		return dao.countWhere(filters, hql);
	}
	
	public List<Integer> findDistinctFinYearsStart() throws Exception {
		return dao.findDistinctFinYearsStart();
	}
	
	public List<QmrFinYears> findByFinYearsStart(Integer finYearStart) throws Exception {
		return dao.findByFinYearsStart(finYearStart);
	}
	
	public List<QmrFinYears> findByDateForGeneratione(Date toDate) throws Exception {
		return dao.findByDateForGeneratione(toDate);
	}
	
	public List<QmrFinYears> findListByFinYearAndQuarter(Long finYearId, FinYearQuartersEnum finYearQuartersEnum) throws Exception {
		return dao.findListByFinYearAndQuarter(finYearId, finYearQuartersEnum);
	}
	
	public QmrFinYears findListByFinYearAndQuarterReturnOne(Long finYearId, FinYearQuartersEnum finYearQuartersEnum) throws Exception {
		List<QmrFinYears> list = findListByFinYearAndQuarter(finYearId, finYearQuartersEnum);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/** Yearly Generation  */
	public void generateNewFinYear(Date today) throws Exception {
		today = GenericUtility.getStartOfDay(today);
		// get quarter one generic information
		FinYearQuartersLookUp quarterOneLookUp = finYearQuartersLookUpService.findByQuarterAssigned(FinYearQuartersEnum.QuarterOne);
		if (quarterOneLookUp == null || quarterOneLookUp.getFromDate() == null) {
			throw new Exception("Unable to locate look up information for: Quarter One. Please review the configuration.");
		}
		Date quarterOneStartDate = GenericUtility.getStartOfDay(quarterOneLookUp.getFromDate());
		if (HAJConstants.sdfDDMMMM.format(quarterOneStartDate).equals(HAJConstants.sdfDDMMMM.format(today))) {
			// generate new fin years	
			@SuppressWarnings("deprecation")
			Integer currentYear = today.getYear() + 1900;
			Integer endOfFinYear = currentYear +1;
			
			List<IDataEntity> createList = new ArrayList<>();
			
			List<FinYearQuartersLookUp> allQuestersLookUp = finYearQuartersLookUpService.allFinYearQuartersLookUpOrderedByQuarters();
			if (financialYearsService == null) {
				financialYearsService = new FinancialYearsService();
			}
			FinancialYears finYearLookup = financialYearsService.findByStartYearReturnFirst(currentYear);
			for (FinYearQuartersLookUp finYearQuartersLookUp : allQuestersLookUp) {
				
				QmrFinYears newEntry = new QmrFinYears();
				
				newEntry.setFinYearStart(currentYear);
				newEntry.setFinYearEnd(endOfFinYear);
				if (finYearLookup != null) {
					newEntry.setFinancialYears(finYearLookup);
				}
				newEntry.setDataGenerated(false);
				newEntry.setFinYearQuartersLookUpFlatKey(finYearQuartersLookUp.getId());
				newEntry.setFinYearQuarters(finYearQuartersLookUp.getFinYearQuarters());
				newEntry.setRefNo("Y"+String.valueOf(currentYear - 2000) + "Q" + finYearQuartersLookUp.getOrderNumber().toString());
				
				Date fromDate = finYearQuartersLookUp.getFromDate();
				Date toDate = finYearQuartersLookUp.getToDate();
				
				if (finYearQuartersLookUp.getUseNextYear() == null || !finYearQuartersLookUp.getUseNextYear()) {
					// use currentYear Year
					newEntry.setFromDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(fromDate) + " " + currentYear.toString()));
					newEntry.setToDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(toDate) + " " + currentYear.toString()));
					newEntry.setDateForGeneration(GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(newEntry.getToDate(), 1)));
				} else {
					// use endOfFinYear Year
					newEntry.setFromDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(fromDate) + " " + endOfFinYear.toString()));
					newEntry.setToDate(HAJConstants.sdfDDMMMMYYYY.parse(HAJConstants.sdfDDMMMM.format(toDate) + " " + endOfFinYear.toString()));
					newEntry.setDateForGeneration(GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(newEntry.getToDate(), 1)));
				}
				createList.add(newEntry);
			}
			
			if (!createList.isEmpty()) {
				dao.createBatch(createList);
			}
		}
	}
	
	/*
	 * Schedule service for generating data for the QMR fin Years
	 */
	public void generateDataForQuarter(Date today) throws Exception {
		Date generationDate = GenericUtility.getStartOfDay(today);
		List<QmrFinYears> generationList = findByDateForGeneratione(generationDate);
		for (QmrFinYears qmrFinYear : generationList) {
			generateDataForQmrFinYear(qmrFinYear);
		}
	}
	
	/**
	 * Generates the data for a quarter
	 * @throws Exception
	 */
	public void generateDataForQmrFinYear(QmrFinYears entry) throws Exception {
		if (entry.getDataGenerated() == null || !entry.getDataGenerated()) {
			// set create list
			List<IDataEntity> creatList = new ArrayList<>(); 
			
			// set update list
			List<IDataEntity> updateList = new ArrayList<>();
			
			// generate learner ship data: completed
			List<QmrLearnershipData> learnershipData = qmrLearnershipDataService.generateQmrLearnershipDataForQuater(entry);
			if (!learnershipData.isEmpty()) {
				creatList.addAll(learnershipData);
			}
			learnershipData.clear();
			
			// generate Bursary data: completed needs to be tested
			List<QmrBursaryData> qmrBursaryData = qmrBursaryDataService.generateQmrBursaryDataForQuater(entry);
			if (!qmrBursaryData.isEmpty()) {
				creatList.addAll(qmrBursaryData);
			}
			qmrBursaryData.clear();
			
			// generate haj.com.entity.QmrSkillsProgrammeData : completed needs to be tested
			List<QmrSkillsProgrammeData> qmrSkillsProgrammeData = qmrSkillsProgrammeDataService.generateQmrSkillsProgrammeDataForQuater(entry);
			if (!qmrSkillsProgrammeData.isEmpty()) {
				creatList.addAll(qmrSkillsProgrammeData);
			}
			qmrSkillsProgrammeData.clear();
			
			// generate haj.com.entity.QmrInternshipData : completed needs to be tested
			List<QmrInternshipData> qmrInternshipData = qmrInternshipDataService.generateQmrInternshipDataForQuater(entry);
			if (!qmrInternshipData.isEmpty()) {
				creatList.addAll(qmrInternshipData);
			}
			qmrInternshipData.clear();
			
			// generate haj.com.entity.QmrArtisanData : completed needs to be tested
			List<QmrArtisanData> qmrArtisanData = qmrArtisanDataService.generateQuaterData(entry);
			if (!qmrArtisanData.isEmpty()) {
				creatList.addAll(qmrArtisanData);
			}
			qmrArtisanData.clear();
			
			// generate haj.com.entity.QmrCandidacyProgrammeData : completed needs to be tested
			List<QmrCandidacyProgrammeData> qmrCandidacyProgrammeData = qmrCandidacyProgrammeDataService.generateQuaterData(entry);
			if (!qmrCandidacyProgrammeData.isEmpty()) {
				creatList.addAll(qmrCandidacyProgrammeData);
			}
			qmrCandidacyProgrammeData.clear();
			
			// generate haj.com.entity.QmrRPLData : completed needs to be tested
			List<QmrRPLData> qmrRPLData = qmRplDataService.generateQuaterData(entry);
			if (!qmrRPLData.isEmpty()) {
				creatList.addAll(qmrRPLData);
			}
			qmrRPLData.clear();
			
			// generate haj.com.entity.QmrTVETData : completed needs to be tested
			List<QmrTVETData> qmrTVETData = qmrTVETDataService.generateQuaterData(entry);
			if (!qmrTVETData.isEmpty()) {
				creatList.addAll(qmrTVETData);
			}
			qmrTVETData.clear();
			
			// generate haj.com.entity.QmrUniversityStudentData : completed needs to be tested
			List<QmrUniversityStudentData> qmrUniversityStudentData = qmrUniversityStudentDataService.generateQuaterData(entry);
			if (!qmrUniversityStudentData.isEmpty()) {
				creatList.addAll(qmrUniversityStudentData);
			}
			qmrUniversityStudentData.clear();
			
			// generate haj.com.entity.QmrLecturerDevelopmentData : completed needs to be tested
			List<QmrLecturerDevelopmentData> qmrLecturerDevelopmentData = qmrLecturerDevelopmentDataService.generateQuaterData(entry);
			if (!qmrLecturerDevelopmentData.isEmpty()) {
				creatList.addAll(qmrLecturerDevelopmentData);
			}
			qmrLecturerDevelopmentData.clear();
			
			// generate haj.com.entity.QmrAETProgrammeData : completed needs to be tested
			List<QmrAETProgrammeData> qmrAETProgrammeData = qmrAETProgrammeDataService.generateQuaterData(entry);
			if (!qmrAETProgrammeData.isEmpty()) {
				creatList.addAll(qmrAETProgrammeData);
			}
			qmrAETProgrammeData.clear();
			
			// generate haj.com.entity.QmrIndividualUnitStandardData : completed needs to be tested
			List<QmrIndividualUnitStandardData> qmrIndividualUnitStandardData = qmrIndividualUnitStandardDataService.generateQuaterData(entry);
			if (!qmrIndividualUnitStandardData.isEmpty()) {
				creatList.addAll(qmrIndividualUnitStandardData);
			}
			qmrIndividualUnitStandardData.clear();

			// update to data generated
			entry.setDataGenerated(true);
			entry.setDateDataGenerated(getSynchronizedDate());
			updateList.add(entry);
			if (!creatList.isEmpty()) {
				dao.createBatch(creatList, 500);
			}
			if (!updateList.isEmpty()) {
				dao.updateBatch(updateList, 500);
			}
		} else {
			throw new Exception("Data Already Generated for Quarter Selected");
		}
	}
	
	
	
}
