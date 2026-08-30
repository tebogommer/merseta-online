package haj.com.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;


import haj.com.dao.MandatoryGrantDetailArchiveDAO;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.MandatoryGrantDetailArchive;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

public class MandatoryGrantDetailArchiveService extends AbstractService {
	/** The dao. */
	private MandatoryGrantDetailArchiveDAO dao = new MandatoryGrantDetailArchiveDAO();

	/** The Service */
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();

	/**
	 * Get all MandatoryGrantDetailArchive
	 * 
	 * @author TechFinium
	 * @see MandatoryGrantDetailArchive
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
	 * @throws Exception
	 *             the exception
	 */
	public List<MandatoryGrantDetailArchive> allMandatoryGrantDetailArchive() throws Exception {
		return dao.allMandatoryGrantDetailArchive();
	}

	/**
	 * Create or update MandatoryGrantDetailArchive.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetailArchive
	 */
	public void create(MandatoryGrantDetailArchive entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update MandatoryGrantDetailArchive.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetailArchive
	 */
	public void update(MandatoryGrantDetailArchive entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MandatoryGrantDetailArchive.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetailArchive
	 */
	public void delete(MandatoryGrantDetailArchive entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.MandatoryGrantDetailArchive}
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetailArchive
	 */
	public MandatoryGrantDetailArchive findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MandatoryGrantDetailArchive by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetailArchive
	 */
	public List<MandatoryGrantDetailArchive> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MandatoryGrantDetailArchive
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
	 * @throws Exception
	 *             the exception
	 */
	public List<MandatoryGrantDetailArchive> allMandatoryGrantDetailArchive(int first, int pageSize) throws Exception {
		return dao.allMandatoryGrantDetailArchive(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MandatoryGrantDetailArchive for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the MandatoryGrantDetailArchive
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(MandatoryGrantDetailArchive.class);
	}

	/**
	 * Lazy load MandatoryGrantDetailArchive with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MandatoryGrantDetailArchive class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetailArchive> allMandatoryGrantDetailArchive(Class<MandatoryGrantDetailArchive> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<MandatoryGrantDetailArchive>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,filters);
	}

	/**
	 * Get count of MandatoryGrantDetailArchive for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            MandatoryGrantDetailArchive class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the MandatoryGrantDetailArchive entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<MandatoryGrantDetailArchive> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public void archiveEntriesNotImportedByYear() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
		SimpleDateFormat timestamp = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
		Date fromDate = GenericUtility.getStartOfDay(sdf.parse("01 01 2018"));
		Date toDate = GenericUtility.getEndOfDay(sdf.parse("01 10 2018"));
		
		Integer counter = 1;
		System.out.println(timestamp.format(new Date()) + " ----------- START");
		while (counter != 0) {
			System.out.println(timestamp.format(new Date()) + " ----------- COUNTER VALUE AT START: " + counter);
			List<MandatoryGrantDetail> entriesToBeProcessed = mandatoryGrantDetailService.allMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(fromDate, toDate, WspStatusEnum.Approved, 10000);
			for (MandatoryGrantDetail mandatoryGrantDetail : entriesToBeProcessed) {
				boolean createError = false;
				MandatoryGrantDetailArchive archiveEntry = new MandatoryGrantDetailArchive();
				archiveEntry.setIdMandatoryGrantDetail(mandatoryGrantDetail.getId());
				if (mandatoryGrantDetail.getFirstName() != null && !mandatoryGrantDetail.getFirstName().isEmpty()) {
					archiveEntry.setFirstName(mandatoryGrantDetail.getFirstName());
				}
				if (mandatoryGrantDetail.getLastName() != null && !mandatoryGrantDetail.getLastName().isEmpty()) {
					archiveEntry.setLastName(mandatoryGrantDetail.getLastName());
				}
				if (mandatoryGrantDetail.getIdType() != null) {
					archiveEntry.setIdType(mandatoryGrantDetail.getIdType());
				}
				if (mandatoryGrantDetail.getIdTypeCode() != null) {
					archiveEntry.setIdTypeCode(mandatoryGrantDetail.getIdTypeCode());
				}
				if (mandatoryGrantDetail.getIdNumber() != null && !mandatoryGrantDetail.getIdNumber().isEmpty()) {
					archiveEntry.setIdNumber(mandatoryGrantDetail.getIdNumber());
				}
				if (mandatoryGrantDetail.getCreateDate() != null) {
					archiveEntry.setMandatoryGrantDetailCreateDate(mandatoryGrantDetail.getCreateDate());
				}
				if (mandatoryGrantDetail.getWsp() != null) {
					archiveEntry.setWsp(mandatoryGrantDetail.getWsp());
				}
				if (mandatoryGrantDetail.getOfoCodes() != null) {
					archiveEntry.setOfoCodes(mandatoryGrantDetail.getOfoCodes());
				}
				if (mandatoryGrantDetail.getMunicipality() != null) {
					archiveEntry.setMunicipality(mandatoryGrantDetail.getMunicipality());
				}
				if (mandatoryGrantDetail.getTraining() != null) {
					archiveEntry.setTraining(mandatoryGrantDetail.getTraining());
				}
				if (mandatoryGrantDetail.getFunding() != null) {
					archiveEntry.setFunding(mandatoryGrantDetail.getFunding());
				}
				if (mandatoryGrantDetail.getInterventionType() != null) {
					archiveEntry.setInterventionType(mandatoryGrantDetail.getInterventionType());
				}
				if (mandatoryGrantDetail.getNqfAligned() != null) {
					archiveEntry.setNqfAligned(mandatoryGrantDetail.getNqfAligned());
				}
				if (mandatoryGrantDetail.getQualification() != null) {
					archiveEntry.setQualification(mandatoryGrantDetail.getQualification());
				}
				if (mandatoryGrantDetail.getUnitStandard() != null) {
					archiveEntry.setUnitStandard(mandatoryGrantDetail.getUnitStandard());
				}
				if (mandatoryGrantDetail.getSkillsProgram() != null) {
					archiveEntry.setSkillsProgram(mandatoryGrantDetail.getSkillsProgram());
				}
				if (mandatoryGrantDetail.getSkillsSet() != null) {
					archiveEntry.setSkillsSet(mandatoryGrantDetail.getSkillsSet());
				}
				if (mandatoryGrantDetail.getNqfLevels() != null) {
					archiveEntry.setNqfLevels(mandatoryGrantDetail.getNqfLevels());
				}
				if (mandatoryGrantDetail.getInterventionLevel() != null) {
					archiveEntry.setInterventionLevel(mandatoryGrantDetail.getInterventionLevel());
				}
				if (mandatoryGrantDetail.getEtqa() != null) {
					archiveEntry.setEtqa(mandatoryGrantDetail.getEtqa());
				}
				if (mandatoryGrantDetail.getProviderType() != null) {
					archiveEntry.setProviderType(mandatoryGrantDetail.getProviderType());
				}
				if (mandatoryGrantDetail.getTrainingDeliveryMethod() != null) {
					archiveEntry.setTrainingDeliveryMethod(mandatoryGrantDetail.getTrainingDeliveryMethod());
				}
				if (mandatoryGrantDetail.getInterventionTitle() != null) {
					archiveEntry.setInterventionTitle(mandatoryGrantDetail.getInterventionTitle());
				}
				if (mandatoryGrantDetail.getEstimatedCost() != null) {
					archiveEntry.setEstimatedCost(mandatoryGrantDetail.getEstimatedCost());
				}
				if (mandatoryGrantDetail.getStartDate() != null) {
					archiveEntry.setStartDate(mandatoryGrantDetail.getStartDate());
				}
				if (mandatoryGrantDetail.getEndDate() != null) {
					archiveEntry.setEndDate(mandatoryGrantDetail.getEndDate());
				}
				if (mandatoryGrantDetail.getDateOfBirth() != null) {
					archiveEntry.setDateOfBirth(mandatoryGrantDetail.getDateOfBirth());
				}
				if (mandatoryGrantDetail.getWspReport() != null) {
					archiveEntry.setWspReport(mandatoryGrantDetail.getWspReport());
				}
				if (mandatoryGrantDetail.getPivotNonPivot() != null) {
					archiveEntry.setPivotNonPivot(mandatoryGrantDetail.getPivotNonPivot());
				}
				if (mandatoryGrantDetail.getOfoCode() != null && !mandatoryGrantDetail.getOfoCode().isEmpty()) {
					archiveEntry.setOfoCode(mandatoryGrantDetail.getOfoCode());
				}
				if (mandatoryGrantDetail.getSpecialisationCode() != null && !mandatoryGrantDetail.getSpecialisationCode().isEmpty()) {
					archiveEntry.setSpecialisationCode(mandatoryGrantDetail.getSpecialisationCode());
				}
				if (mandatoryGrantDetail.getGender() != null) {
					archiveEntry.setGender(mandatoryGrantDetail.getGender());
				}
				if (mandatoryGrantDetail.getGenderCode() != null && !mandatoryGrantDetail.getGenderCode().isEmpty()) {
					archiveEntry.setGenderCode(mandatoryGrantDetail.getGenderCode());
				}
				if (mandatoryGrantDetail.getEquity() != null) {
					archiveEntry.setEquity(mandatoryGrantDetail.getEquity());
				}
				if (mandatoryGrantDetail.getEquityCode() != null && !mandatoryGrantDetail.getEquityCode().isEmpty()) {
					archiveEntry.setEquityCode(mandatoryGrantDetail.getEquityCode());
				}
				if (mandatoryGrantDetail.getNationality() != null) {
					archiveEntry.setNationality(mandatoryGrantDetail.getNationality());
				}
				if (mandatoryGrantDetail.getNationalityCode() != null && !mandatoryGrantDetail.getNationalityCode().isEmpty()) {
					archiveEntry.setNationalityCode(mandatoryGrantDetail.getNationalityCode());
				}
				if (mandatoryGrantDetail.getEnrolmentStatusCode() != null && !mandatoryGrantDetail.getEnrolmentStatusCode().isEmpty()) {
					archiveEntry.setEnrolmentStatusCode(mandatoryGrantDetail.getEnrolmentStatusCode());
				}
				if (mandatoryGrantDetail.getEnrolmentStatus() != null) {
					archiveEntry.setEnrolmentStatus(mandatoryGrantDetail.getEnrolmentStatus());
				}
				if (mandatoryGrantDetail.getFundingCode() != null && !mandatoryGrantDetail.getFundingCode().isEmpty()) {
					archiveEntry.setFundingCode(mandatoryGrantDetail.getFundingCode());
				}
				if (mandatoryGrantDetail.getInterventionTypeCode() != null && !mandatoryGrantDetail.getInterventionTypeCode().isEmpty()) {
					archiveEntry.setInterventionTypeCode(mandatoryGrantDetail.getInterventionTypeCode());
				}
				if (mandatoryGrantDetail.getQualificationCode() != null) {
					archiveEntry.setQualificationCode(mandatoryGrantDetail.getQualificationCode());
				}
				if (mandatoryGrantDetail.getProviderTypeCode() != null && !mandatoryGrantDetail.getProviderTypeCode().isEmpty()) {
					archiveEntry.setProviderTypeCode(mandatoryGrantDetail.getProviderTypeCode());
				}
				if (mandatoryGrantDetail.getTrainingDeliveryMethodCode() != null && !mandatoryGrantDetail.getTrainingDeliveryMethodCode().isEmpty()) {
					archiveEntry.setTrainingDeliveryMethodCode(mandatoryGrantDetail.getTrainingDeliveryMethodCode());
				}
				if (mandatoryGrantDetail.getEmploymentType() != null) {
					archiveEntry.setEmploymentType(mandatoryGrantDetail.getEmploymentType());
				}
				if (mandatoryGrantDetail.getDisability() != null) {
					archiveEntry.setDisability(mandatoryGrantDetail.getDisability());
				}
				if (mandatoryGrantDetail.getDisabilityCode() != null && !mandatoryGrantDetail.getDisabilityCode().isEmpty()) {
					archiveEntry.setDisabilityCode(mandatoryGrantDetail.getDisabilityCode());
				}
				if (mandatoryGrantDetail.getEmploymentStatus() != null) {
					archiveEntry.setEmploymentStatus(mandatoryGrantDetail.getEmploymentStatus());
				}
				if (mandatoryGrantDetail.getEmploymentTypeCode() != null && !mandatoryGrantDetail.getEmploymentTypeCode().isEmpty()) {
					archiveEntry.setEmploymentTypeCode(mandatoryGrantDetail.getEmploymentTypeCode());
				}
				if (mandatoryGrantDetail.getSkillsProgramCode() != null && !mandatoryGrantDetail.getSkillsProgramCode().isEmpty()) {
					archiveEntry.setSkillsProgramCode(mandatoryGrantDetail.getSkillsProgramCode());
				}
				if (mandatoryGrantDetail.getSkillsSetCode() != null && !mandatoryGrantDetail.getSkillsSetCode().isEmpty()) {
					archiveEntry.setSkillsSetCode(mandatoryGrantDetail.getSkillsSetCode());
				}
				if (mandatoryGrantDetail.getImportErrors() != null && !mandatoryGrantDetail.getImportErrors().isEmpty()) {
					archiveEntry.setImportErrors(mandatoryGrantDetail.getImportErrors());
				}
				if (mandatoryGrantDetail.getNonCreditBearingIntervationTitle() != null) {
					archiveEntry.setNonCreditBearingIntervationTitle(mandatoryGrantDetail.getNonCreditBearingIntervationTitle());
				}
				archiveEntry.setImported(mandatoryGrantDetail.isImported());
				archiveEntry.setImportError(mandatoryGrantDetail.isImportError());
				
				// create of archive entry
				try {
					dao.create(archiveEntry);
					archiveEntry = null;
					createError = false;
				} catch (Exception e) {
					createError = true;
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					FailedArchiveEntriesService.instance().logFail(mandatoryGrantDetail.getId(), "Create", exceptionAsString);
				}

				// delete of original entry
				try {
					if (!createError) {
						mandatoryGrantDetailService.delete(mandatoryGrantDetail);
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					FailedArchiveEntriesService.instance().logFail(mandatoryGrantDetail.getId(), "Delete", exceptionAsString);
				}
				
			}
			entriesToBeProcessed = null;
			counter = mandatoryGrantDetailService.countMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(fromDate, toDate, WspStatusEnum.Approved);
			System.out.println(timestamp.format(new Date()) + " ----------- counter check before while loop end: " + counter);
		}
		System.out.println(timestamp.format(new Date()) + "----------- PROCESS END ");
		counter = null;
	}
	
	/**
	 * Scheduler Service to Archive 50 000 maximum every night in batches of 10 000
	 * 
	 * @throws Exception
	 */
	public void schedularArchiveEntriesNotImportedByYear() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
		Date fromDate = GenericUtility.getStartOfDay(sdf.parse("01 01 2019"));
		Date toDate = GenericUtility.getEndOfDay(sdf.parse("01 01 2020"));
		Integer counter = 0;
		while (counter < 5) {
			List<MandatoryGrantDetail> entriesToBeProcessed = mandatoryGrantDetailService.allMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(fromDate, toDate, WspStatusEnum.Approved, 10000);
			for (MandatoryGrantDetail mandatoryGrantDetail : entriesToBeProcessed) {
				boolean createError = false;
				MandatoryGrantDetailArchive archiveEntry = new MandatoryGrantDetailArchive();
				archiveEntry.setIdMandatoryGrantDetail(mandatoryGrantDetail.getId());
				if (mandatoryGrantDetail.getFirstName() != null && !mandatoryGrantDetail.getFirstName().isEmpty()) {
					archiveEntry.setFirstName(mandatoryGrantDetail.getFirstName());
				}
				if (mandatoryGrantDetail.getLastName() != null && !mandatoryGrantDetail.getLastName().isEmpty()) {
					archiveEntry.setLastName(mandatoryGrantDetail.getLastName());
				}
				if (mandatoryGrantDetail.getIdType() != null) {
					archiveEntry.setIdType(mandatoryGrantDetail.getIdType());
				}
				if (mandatoryGrantDetail.getIdTypeCode() != null) {
					archiveEntry.setIdTypeCode(mandatoryGrantDetail.getIdTypeCode());
				}
				if (mandatoryGrantDetail.getIdNumber() != null && !mandatoryGrantDetail.getIdNumber().isEmpty()) {
					archiveEntry.setIdNumber(mandatoryGrantDetail.getIdNumber());
				}
				if (mandatoryGrantDetail.getCreateDate() != null) {
					archiveEntry.setMandatoryGrantDetailCreateDate(mandatoryGrantDetail.getCreateDate());
				}
				if (mandatoryGrantDetail.getWsp() != null) {
					archiveEntry.setWsp(mandatoryGrantDetail.getWsp());
				}
				if (mandatoryGrantDetail.getOfoCodes() != null) {
					archiveEntry.setOfoCodes(mandatoryGrantDetail.getOfoCodes());
				}
				if (mandatoryGrantDetail.getMunicipality() != null) {
					archiveEntry.setMunicipality(mandatoryGrantDetail.getMunicipality());
				}
				if (mandatoryGrantDetail.getTraining() != null) {
					archiveEntry.setTraining(mandatoryGrantDetail.getTraining());
				}
				if (mandatoryGrantDetail.getFunding() != null) {
					archiveEntry.setFunding(mandatoryGrantDetail.getFunding());
				}
				if (mandatoryGrantDetail.getInterventionType() != null) {
					archiveEntry.setInterventionType(mandatoryGrantDetail.getInterventionType());
				}
				if (mandatoryGrantDetail.getNqfAligned() != null) {
					archiveEntry.setNqfAligned(mandatoryGrantDetail.getNqfAligned());
				}
				if (mandatoryGrantDetail.getQualification() != null) {
					archiveEntry.setQualification(mandatoryGrantDetail.getQualification());
				}
				if (mandatoryGrantDetail.getUnitStandard() != null) {
					archiveEntry.setUnitStandard(mandatoryGrantDetail.getUnitStandard());
				}
				if (mandatoryGrantDetail.getSkillsProgram() != null) {
					archiveEntry.setSkillsProgram(mandatoryGrantDetail.getSkillsProgram());
				}
				if (mandatoryGrantDetail.getSkillsSet() != null) {
					archiveEntry.setSkillsSet(mandatoryGrantDetail.getSkillsSet());
				}
				if (mandatoryGrantDetail.getNqfLevels() != null) {
					archiveEntry.setNqfLevels(mandatoryGrantDetail.getNqfLevels());
				}
				if (mandatoryGrantDetail.getInterventionLevel() != null) {
					archiveEntry.setInterventionLevel(mandatoryGrantDetail.getInterventionLevel());
				}
				if (mandatoryGrantDetail.getEtqa() != null) {
					archiveEntry.setEtqa(mandatoryGrantDetail.getEtqa());
				}
				if (mandatoryGrantDetail.getProviderType() != null) {
					archiveEntry.setProviderType(mandatoryGrantDetail.getProviderType());
				}
				if (mandatoryGrantDetail.getTrainingDeliveryMethod() != null) {
					archiveEntry.setTrainingDeliveryMethod(mandatoryGrantDetail.getTrainingDeliveryMethod());
				}
				if (mandatoryGrantDetail.getInterventionTitle() != null) {
					archiveEntry.setInterventionTitle(mandatoryGrantDetail.getInterventionTitle());
				}
				if (mandatoryGrantDetail.getEstimatedCost() != null) {
					archiveEntry.setEstimatedCost(mandatoryGrantDetail.getEstimatedCost());
				}
				if (mandatoryGrantDetail.getStartDate() != null) {
					archiveEntry.setStartDate(mandatoryGrantDetail.getStartDate());
				}
				if (mandatoryGrantDetail.getEndDate() != null) {
					archiveEntry.setEndDate(mandatoryGrantDetail.getEndDate());
				}
				if (mandatoryGrantDetail.getDateOfBirth() != null) {
					archiveEntry.setDateOfBirth(mandatoryGrantDetail.getDateOfBirth());
				}
				if (mandatoryGrantDetail.getWspReport() != null) {
					archiveEntry.setWspReport(mandatoryGrantDetail.getWspReport());
				}
				if (mandatoryGrantDetail.getPivotNonPivot() != null) {
					archiveEntry.setPivotNonPivot(mandatoryGrantDetail.getPivotNonPivot());
				}
				if (mandatoryGrantDetail.getOfoCode() != null && !mandatoryGrantDetail.getOfoCode().isEmpty()) {
					archiveEntry.setOfoCode(mandatoryGrantDetail.getOfoCode());
				}
				if (mandatoryGrantDetail.getSpecialisationCode() != null && !mandatoryGrantDetail.getSpecialisationCode().isEmpty()) {
					archiveEntry.setSpecialisationCode(mandatoryGrantDetail.getSpecialisationCode());
				}
				if (mandatoryGrantDetail.getGender() != null) {
					archiveEntry.setGender(mandatoryGrantDetail.getGender());
				}
				if (mandatoryGrantDetail.getGenderCode() != null && !mandatoryGrantDetail.getGenderCode().isEmpty()) {
					archiveEntry.setGenderCode(mandatoryGrantDetail.getGenderCode());
				}
				if (mandatoryGrantDetail.getEquity() != null) {
					archiveEntry.setEquity(mandatoryGrantDetail.getEquity());
				}
				if (mandatoryGrantDetail.getEquityCode() != null && !mandatoryGrantDetail.getEquityCode().isEmpty()) {
					archiveEntry.setEquityCode(mandatoryGrantDetail.getEquityCode());
				}
				if (mandatoryGrantDetail.getNationality() != null) {
					archiveEntry.setNationality(mandatoryGrantDetail.getNationality());
				}
				if (mandatoryGrantDetail.getNationalityCode() != null && !mandatoryGrantDetail.getNationalityCode().isEmpty()) {
					archiveEntry.setNationalityCode(mandatoryGrantDetail.getNationalityCode());
				}
				if (mandatoryGrantDetail.getEnrolmentStatusCode() != null && !mandatoryGrantDetail.getEnrolmentStatusCode().isEmpty()) {
					archiveEntry.setEnrolmentStatusCode(mandatoryGrantDetail.getEnrolmentStatusCode());
				}
				if (mandatoryGrantDetail.getEnrolmentStatus() != null) {
					archiveEntry.setEnrolmentStatus(mandatoryGrantDetail.getEnrolmentStatus());
				}
				if (mandatoryGrantDetail.getFundingCode() != null && !mandatoryGrantDetail.getFundingCode().isEmpty()) {
					archiveEntry.setFundingCode(mandatoryGrantDetail.getFundingCode());
				}
				if (mandatoryGrantDetail.getInterventionTypeCode() != null && !mandatoryGrantDetail.getInterventionTypeCode().isEmpty()) {
					archiveEntry.setInterventionTypeCode(mandatoryGrantDetail.getInterventionTypeCode());
				}
				if (mandatoryGrantDetail.getQualificationCode() != null) {
					archiveEntry.setQualificationCode(mandatoryGrantDetail.getQualificationCode());
				}
				if (mandatoryGrantDetail.getProviderTypeCode() != null && !mandatoryGrantDetail.getProviderTypeCode().isEmpty()) {
					archiveEntry.setProviderTypeCode(mandatoryGrantDetail.getProviderTypeCode());
				}
				if (mandatoryGrantDetail.getTrainingDeliveryMethodCode() != null && !mandatoryGrantDetail.getTrainingDeliveryMethodCode().isEmpty()) {
					archiveEntry.setTrainingDeliveryMethodCode(mandatoryGrantDetail.getTrainingDeliveryMethodCode());
				}
				if (mandatoryGrantDetail.getEmploymentType() != null) {
					archiveEntry.setEmploymentType(mandatoryGrantDetail.getEmploymentType());
				}
				if (mandatoryGrantDetail.getDisability() != null) {
					archiveEntry.setDisability(mandatoryGrantDetail.getDisability());
				}
				if (mandatoryGrantDetail.getDisabilityCode() != null && !mandatoryGrantDetail.getDisabilityCode().isEmpty()) {
					archiveEntry.setDisabilityCode(mandatoryGrantDetail.getDisabilityCode());
				}
				if (mandatoryGrantDetail.getEmploymentStatus() != null) {
					archiveEntry.setEmploymentStatus(mandatoryGrantDetail.getEmploymentStatus());
				}
				if (mandatoryGrantDetail.getEmploymentTypeCode() != null && !mandatoryGrantDetail.getEmploymentTypeCode().isEmpty()) {
					archiveEntry.setEmploymentTypeCode(mandatoryGrantDetail.getEmploymentTypeCode());
				}
				if (mandatoryGrantDetail.getSkillsProgramCode() != null && !mandatoryGrantDetail.getSkillsProgramCode().isEmpty()) {
					archiveEntry.setSkillsProgramCode(mandatoryGrantDetail.getSkillsProgramCode());
				}
				if (mandatoryGrantDetail.getSkillsSetCode() != null && !mandatoryGrantDetail.getSkillsSetCode().isEmpty()) {
					archiveEntry.setSkillsSetCode(mandatoryGrantDetail.getSkillsSetCode());
				}
				if (mandatoryGrantDetail.getImportErrors() != null && !mandatoryGrantDetail.getImportErrors().isEmpty()) {
					archiveEntry.setImportErrors(mandatoryGrantDetail.getImportErrors());
				}
				if (mandatoryGrantDetail.getNonCreditBearingIntervationTitle() != null) {
					archiveEntry.setNonCreditBearingIntervationTitle(mandatoryGrantDetail.getNonCreditBearingIntervationTitle());
				}
				if (mandatoryGrantDetail.getRsaCitizenTypeEnum() != null) {
					archiveEntry.setRsaCitizenTypeEnum(mandatoryGrantDetail.getRsaCitizenTypeEnum());
				}
				archiveEntry.setImported(mandatoryGrantDetail.isImported());
				archiveEntry.setImportError(mandatoryGrantDetail.isImportError());
				
				// create of archive entry
				try {
					dao.create(archiveEntry);
					archiveEntry = null;
					createError = false;
				} catch (Exception e) {
					createError = true;
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					FailedArchiveEntriesService.instance().logFail(mandatoryGrantDetail.getId(), "Create", exceptionAsString);
				}
	
				// delete of original entry
				try {
					if (!createError) {
						mandatoryGrantDetailService.delete(mandatoryGrantDetail);
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					FailedArchiveEntriesService.instance().logFail(mandatoryGrantDetail.getId(), "Delete", exceptionAsString);
				}
			}
			entriesToBeProcessed = null;
			counter++;
		}
		counter = null;
	}

	/** Test Methods */
	public void test(long id) throws Exception {
		testArchiveOneEntry(mandatoryGrantDetailService.findByKey(id));
	}
	public void testArchiveOneEntry(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		boolean createError = false;
		boolean deleteError = false;
		MandatoryGrantDetailArchive archiveEntry = new MandatoryGrantDetailArchive();
		archiveEntry.setIdMandatoryGrantDetail(mandatoryGrantDetail.getId());
		if (mandatoryGrantDetail.getFirstName() != null && !mandatoryGrantDetail.getFirstName().isEmpty()) {
			archiveEntry.setFirstName(mandatoryGrantDetail.getFirstName());
		}
		if (mandatoryGrantDetail.getLastName() != null && !mandatoryGrantDetail.getLastName().isEmpty()) {
			archiveEntry.setLastName(mandatoryGrantDetail.getLastName());
		}
		if (mandatoryGrantDetail.getIdType() != null) {
			archiveEntry.setIdType(mandatoryGrantDetail.getIdType());
		}
		if (mandatoryGrantDetail.getIdTypeCode() != null) {
			archiveEntry.setIdTypeCode(mandatoryGrantDetail.getIdTypeCode());
		}
		if (mandatoryGrantDetail.getIdNumber() != null && !mandatoryGrantDetail.getIdNumber().isEmpty()) {
			archiveEntry.setIdNumber(mandatoryGrantDetail.getIdNumber());
		}
		if (mandatoryGrantDetail.getCreateDate() != null) {
			archiveEntry.setMandatoryGrantDetailCreateDate(mandatoryGrantDetail.getCreateDate());
		}
		if (mandatoryGrantDetail.getWsp() != null) {
			archiveEntry.setWsp(mandatoryGrantDetail.getWsp());
		}
		if (mandatoryGrantDetail.getOfoCodes() != null) {
			archiveEntry.setOfoCodes(mandatoryGrantDetail.getOfoCodes());
		}
		if (mandatoryGrantDetail.getMunicipality() != null) {
			archiveEntry.setMunicipality(mandatoryGrantDetail.getMunicipality());
		}
		if (mandatoryGrantDetail.getTraining() != null) {
			archiveEntry.setTraining(mandatoryGrantDetail.getTraining());
		}
		if (mandatoryGrantDetail.getFunding() != null) {
			archiveEntry.setFunding(mandatoryGrantDetail.getFunding());
		}
		if (mandatoryGrantDetail.getInterventionType() != null) {
			archiveEntry.setInterventionType(mandatoryGrantDetail.getInterventionType());
		}
		if (mandatoryGrantDetail.getNqfAligned() != null) {
			archiveEntry.setNqfAligned(mandatoryGrantDetail.getNqfAligned());
		}
		if (mandatoryGrantDetail.getQualification() != null) {
			archiveEntry.setQualification(mandatoryGrantDetail.getQualification());
		}
		if (mandatoryGrantDetail.getUnitStandard() != null) {
			archiveEntry.setUnitStandard(mandatoryGrantDetail.getUnitStandard());
		}
		if (mandatoryGrantDetail.getSkillsProgram() != null) {
			archiveEntry.setSkillsProgram(mandatoryGrantDetail.getSkillsProgram());
		}
		if (mandatoryGrantDetail.getSkillsSet() != null) {
			archiveEntry.setSkillsSet(mandatoryGrantDetail.getSkillsSet());
		}
		if (mandatoryGrantDetail.getNqfLevels() != null) {
			archiveEntry.setNqfLevels(mandatoryGrantDetail.getNqfLevels());
		}
		if (mandatoryGrantDetail.getInterventionLevel() != null) {
			archiveEntry.setInterventionLevel(mandatoryGrantDetail.getInterventionLevel());
		}
		if (mandatoryGrantDetail.getEtqa() != null) {
			archiveEntry.setEtqa(mandatoryGrantDetail.getEtqa());
		}
		if (mandatoryGrantDetail.getProviderType() != null) {
			archiveEntry.setProviderType(mandatoryGrantDetail.getProviderType());
		}
		if (mandatoryGrantDetail.getTrainingDeliveryMethod() != null) {
			archiveEntry.setTrainingDeliveryMethod(mandatoryGrantDetail.getTrainingDeliveryMethod());
		}
		if (mandatoryGrantDetail.getInterventionTitle() != null) {
			archiveEntry.setInterventionTitle(mandatoryGrantDetail.getInterventionTitle());
		}
		if (mandatoryGrantDetail.getEstimatedCost() != null) {
			archiveEntry.setEstimatedCost(mandatoryGrantDetail.getEstimatedCost());
		}
		if (mandatoryGrantDetail.getStartDate() != null) {
			archiveEntry.setStartDate(mandatoryGrantDetail.getStartDate());
		}
		if (mandatoryGrantDetail.getEndDate() != null) {
			archiveEntry.setEndDate(mandatoryGrantDetail.getEndDate());
		}
		if (mandatoryGrantDetail.getDateOfBirth() != null) {
			archiveEntry.setDateOfBirth(mandatoryGrantDetail.getDateOfBirth());
		}
		if (mandatoryGrantDetail.getWspReport() != null) {
			archiveEntry.setWspReport(mandatoryGrantDetail.getWspReport());
		}
		if (mandatoryGrantDetail.getPivotNonPivot() != null) {
			archiveEntry.setPivotNonPivot(mandatoryGrantDetail.getPivotNonPivot());
		}
		if (mandatoryGrantDetail.getOfoCode() != null && !mandatoryGrantDetail.getOfoCode().isEmpty()) {
			archiveEntry.setOfoCode(mandatoryGrantDetail.getOfoCode());
		}
		if (mandatoryGrantDetail.getSpecialisationCode() != null && !mandatoryGrantDetail.getSpecialisationCode().isEmpty()) {
			archiveEntry.setSpecialisationCode(mandatoryGrantDetail.getSpecialisationCode());
		}
		if (mandatoryGrantDetail.getGender() != null) {
			archiveEntry.setGender(mandatoryGrantDetail.getGender());
		}
		if (mandatoryGrantDetail.getGenderCode() != null && !mandatoryGrantDetail.getGenderCode().isEmpty()) {
			archiveEntry.setGenderCode(mandatoryGrantDetail.getGenderCode());
		}
		if (mandatoryGrantDetail.getEquity() != null) {
			archiveEntry.setEquity(mandatoryGrantDetail.getEquity());
		}
		if (mandatoryGrantDetail.getEquityCode() != null && !mandatoryGrantDetail.getEquityCode().isEmpty()) {
			archiveEntry.setEquityCode(mandatoryGrantDetail.getEquityCode());
		}
		if (mandatoryGrantDetail.getNationality() != null) {
			archiveEntry.setNationality(mandatoryGrantDetail.getNationality());
		}
		if (mandatoryGrantDetail.getNationalityCode() != null && !mandatoryGrantDetail.getNationalityCode().isEmpty()) {
			archiveEntry.setNationalityCode(mandatoryGrantDetail.getNationalityCode());
		}
		if (mandatoryGrantDetail.getEnrolmentStatusCode() != null && !mandatoryGrantDetail.getEnrolmentStatusCode().isEmpty()) {
			archiveEntry.setEnrolmentStatusCode(mandatoryGrantDetail.getEnrolmentStatusCode());
		}
		if (mandatoryGrantDetail.getEnrolmentStatus() != null) {
			archiveEntry.setEnrolmentStatus(mandatoryGrantDetail.getEnrolmentStatus());
		}
		if (mandatoryGrantDetail.getFundingCode() != null && !mandatoryGrantDetail.getFundingCode().isEmpty()) {
			archiveEntry.setFundingCode(mandatoryGrantDetail.getFundingCode());
		}
		if (mandatoryGrantDetail.getInterventionTypeCode() != null && !mandatoryGrantDetail.getInterventionTypeCode().isEmpty()) {
			archiveEntry.setInterventionTypeCode(mandatoryGrantDetail.getInterventionTypeCode());
		}
		if (mandatoryGrantDetail.getQualificationCode() != null) {
			archiveEntry.setQualificationCode(mandatoryGrantDetail.getQualificationCode());
		}
		if (mandatoryGrantDetail.getProviderTypeCode() != null && !mandatoryGrantDetail.getProviderTypeCode().isEmpty()) {
			archiveEntry.setProviderTypeCode(mandatoryGrantDetail.getProviderTypeCode());
		}
		if (mandatoryGrantDetail.getTrainingDeliveryMethodCode() != null && !mandatoryGrantDetail.getTrainingDeliveryMethodCode().isEmpty()) {
			archiveEntry.setTrainingDeliveryMethodCode(mandatoryGrantDetail.getTrainingDeliveryMethodCode());
		}
		if (mandatoryGrantDetail.getEmploymentType() != null) {
			archiveEntry.setEmploymentType(mandatoryGrantDetail.getEmploymentType());
		}
		if (mandatoryGrantDetail.getDisability() != null) {
			archiveEntry.setDisability(mandatoryGrantDetail.getDisability());
		}
		if (mandatoryGrantDetail.getDisabilityCode() != null && !mandatoryGrantDetail.getDisabilityCode().isEmpty()) {
			archiveEntry.setDisabilityCode(mandatoryGrantDetail.getDisabilityCode());
		}
		if (mandatoryGrantDetail.getEmploymentStatus() != null) {
			archiveEntry.setEmploymentStatus(mandatoryGrantDetail.getEmploymentStatus());
		}
		if (mandatoryGrantDetail.getEmploymentTypeCode() != null && !mandatoryGrantDetail.getEmploymentTypeCode().isEmpty()) {
			archiveEntry.setEmploymentTypeCode(mandatoryGrantDetail.getEmploymentTypeCode());
		}
		if (mandatoryGrantDetail.getSkillsProgramCode() != null && !mandatoryGrantDetail.getSkillsProgramCode().isEmpty()) {
			archiveEntry.setSkillsProgramCode(mandatoryGrantDetail.getSkillsProgramCode());
		}
		if (mandatoryGrantDetail.getSkillsSetCode() != null && !mandatoryGrantDetail.getSkillsSetCode().isEmpty()) {
			archiveEntry.setSkillsSetCode(mandatoryGrantDetail.getSkillsSetCode());
		}
		if (mandatoryGrantDetail.getImportErrors() != null && !mandatoryGrantDetail.getImportErrors().isEmpty()) {
			archiveEntry.setImportErrors(mandatoryGrantDetail.getImportErrors());
		}
		if (mandatoryGrantDetail.getNonCreditBearingIntervationTitle() != null) {
			archiveEntry.setNonCreditBearingIntervationTitle(mandatoryGrantDetail.getNonCreditBearingIntervationTitle());
		}
		if (mandatoryGrantDetail.getMunicipalityCode() != null) {
			archiveEntry.setMunicipalityCode(mandatoryGrantDetail.getMunicipalityCode());
		}
		if (mandatoryGrantDetail.getNonCreditBearingIntervationTitleCode() != null) {
			archiveEntry.setNonCreditBearingIntervationTitleCode(mandatoryGrantDetail.getNonCreditBearingIntervationTitleCode());
		}
		archiveEntry.setImported(mandatoryGrantDetail.isImported());
		archiveEntry.setImportError(mandatoryGrantDetail.isImportError());
		
		// creation of archive entry
		try {
			dao.create(archiveEntry);
			archiveEntry = null;
			createError = false;
		} catch (Exception e) {
			createError = true;
			logger.fatal(e);
		}

		// deletion of original entry
		try {
			if (!createError) {
				mandatoryGrantDetailService.delete(mandatoryGrantDetail);
			}
		} catch (Exception e) {
			deleteError = true;
			logger.fatal(e);
		}
	}
}
