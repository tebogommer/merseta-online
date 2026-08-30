package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyAssessorAccreditationDAO;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyAssessorAccreditationService extends AbstractService {
	/** The dao. */
	private LegacyAssessorAccreditationDAO dao = new LegacyAssessorAccreditationDAO();

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();
	private LegacyPersonService legacyPersonService = new LegacyPersonService();

	/**
	 * Get all LegacyAssessorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditation() throws Exception {
		return dao.allLegacyAssessorAccreditation();
	}

	/**
	 * Create or update LegacyAssessorAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorAccreditation
	 */
	public void create(LegacyAssessorAccreditation entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyAssessorAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorAccreditation
	 */
	public void update(LegacyAssessorAccreditation entity) throws Exception {
		this.dao.update(entity);
	}

	public List<LegacyAssessorAccreditation> findByIdNo(String idNo) throws Exception {
		return this.dao.findByIdNo(idNo);
	}
	
	public LegacyAssessorAccreditation findByIdNumber(String idNo) throws Exception {
		List<LegacyAssessorAccreditation>list = dao.findByIdNo(idNo);
		if(list.size() > 0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public LegacyAssessorAccreditation findByIdRegistrationNumber(String idNo) throws Exception {
		List<LegacyAssessorAccreditation>list = dao.findByIdRegistrationNumber(idNo);
		if(list.size() > 0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	public boolean checkExistingIdNo(String idNo) throws Exception {
		return !this.dao.findByIdNo(idNo).isEmpty();
	}

	public List<LegacyAssessorAccreditation> findByIdNoStatusAndProcessed(String idNo, String status, Boolean processed) throws Exception {
		return this.dao.findByIdNoStatusAndProcessed(idNo, status, processed);
	}

	public List<LegacyAssessorAccreditation> findRegisteredOrExpiredLegacyAccreditation(String idNo, Boolean processed) throws Exception {
		return this.dao.findRegisteredOrExpiredLegacyAccreditation(idNo, processed);
	}
	
	public List<LegacyAssessorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwo(String idNo,Boolean processed) throws Exception {
		return dao.findRegisteredOrExpiredLegacyAccreditationVersionTwo(idNo, processed);
	}
	
	public List<LegacyAssessorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(String idNo, Boolean processed) throws Exception {
		return dao.findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(idNo, processed);
	}

	/**
	 * Delete LegacyAssessorAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorAccreditation
	 */
	public void delete(LegacyAssessorAccreditation entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorAccreditation
	 */
	public LegacyAssessorAccreditation findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyAssessorAccreditation by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorAccreditation
	 */
	public List<LegacyAssessorAccreditation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyAssessorAccreditation
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditation(int first, int pageSize) throws Exception {
		return dao.allLegacyAssessorAccreditation(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyAssessorAccreditation for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyAssessorAccreditation
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyAssessorAccreditation.class);
	}

	/**
	 * Lazy load LegacyAssessorAccreditation with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyAssessorAccreditation class
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
	 * @return a list of {@link haj.com.entity.LegacyAssessorAccreditation}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditation(Class<LegacyAssessorAccreditation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyAssessorAccreditation for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyAssessorAccreditation class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyAssessorAccreditation entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyAssessorAccreditation> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyAssessorAccreditation> allEntries = allLegacyAssessorAccreditation();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyAssessorAccreditation> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyAssessorAccreditationNotProcessed() throws Exception {
		return dao.countAllLegacyAssessorAccreditationNotProcessed();
	}

	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyAssessorAccreditationNotProcessed(numberOfEntries);
	}

	public void validateRsaIdNumbers() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				validiationRun();
			}
		}).start();
	}

	public void validiationRun() {
		try {
			logger.info("validiationRun() Started");
			List<IDataEntity> updateList = new ArrayList<>();
			updateList = new ArrayList<>();
			List<LegacyAssessorAccreditation> li = allLegacyAssessorAccreditation();
			for (LegacyAssessorAccreditation assessorAccreditation : li) {	
				try {
					assessorAccreditation.setValidRsaIdNumber(GenericUtility.checkRsaId(assessorAccreditation.getIdNo().trim()));
				} catch (Exception e) {
					assessorAccreditation.setValidRsaIdNumber(false);
				}

				// on home affairs file
				try {
					Integer entriesFound = homeAffairsService.findByDhaIdNumber(assessorAccreditation.getIdNo().trim());
					if (entriesFound > 0) {
						assessorAccreditation.setAppearsOnHomeAffairsData(true);
					} else {
						assessorAccreditation.setAppearsOnHomeAffairsData(false);
					}
				} catch (Exception e) {
					assessorAccreditation.setAppearsOnHomeAffairsData(false);
				}
				
				if (assessorAccreditation.getAppearsOnHomeAffairsData() != null && assessorAccreditation.getAppearsOnHomeAffairsData()) {
					// if on home affairs file check if alive
					try {
						int aliveCount = homeAffairsService.findByDhaIdNumberAndDeathStatusAlive(assessorAccreditation.getIdNo().trim());
						int deathCount = homeAffairsService.findByDhaIdNumberAndDeathStatusAlive(assessorAccreditation.getIdNo().trim());
						
						if (aliveCount == 0 && deathCount == 0) {
							assessorAccreditation.setAliveOnHomeAffairsData(true);
						}else if (aliveCount > 0) {
							assessorAccreditation.setAliveOnHomeAffairsData(true);
						}else if (deathCount > 0) {
							assessorAccreditation.setAliveOnHomeAffairsData(false);
						}
//						assessorAccreditation.setAliveOnHomeAffairsData(homeAffairsService.idNoDeceasedCheckTwo(legacyModeratorAccreditation.getIdNo().trim()));
					} catch (Exception e) {
						logger.fatal(e);
						assessorAccreditation.setAliveOnHomeAffairsData(null);
					}
				}
				
				try {
					// check if on persons file
					assessorAccreditation.setOnPersonsFile(legacyPersonService.idNumberOnPersonFile(assessorAccreditation.getIdNo().trim()));
				} catch (Exception e) {
					assessorAccreditation.setOnPersonsFile(false);
				}
				
				try {
					// check if on persons file for alternative ID
					assessorAccreditation.setOnPersonsFileAlternativeId(legacyPersonService.alternativeIdNumberOnPersonFile(assessorAccreditation.getIdNo().trim()));
				} catch (Exception e) {
					logger.fatal(e);
					assessorAccreditation.setOnPersonsFileAlternativeId(false);
				}
				
				try {
					// Determine valid status
					if ((assessorAccreditation.getAssessorStatusDesc() != null && !assessorAccreditation.getAssessorStatusDesc().isEmpty()) 
							&& 
						(assessorAccreditation.getAssessorRegEndDate() != null && !assessorAccreditation.getAssessorRegEndDate().isEmpty() && !assessorAccreditation.getAssessorRegEndDate().trim().toUpperCase().equals("NULL") )) {
						
						Date modRegEndDate = GenericUtility.getStartOfDay(HAJConstants.sdfYYYYMMDD.parse(assessorAccreditation.getAssessorRegEndDate().trim()));
						
						/*
						 * Application
						 * Declined
						 * DeRegistered
						 * Expired
						 * Extended
						 * Registered
						 * ReRegistered
						 */
						
						switch (assessorAccreditation.getAssessorStatusDesc().trim().toUpperCase()) {
						case "APPLICATION": // -- not allowed to select
							// after today
							if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						case "DECLINED": // -- not allowed to select
							// before today
							if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						case "DEREGISTERED": // -- not allowed to select
							// before today
							if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						case "EXPIRED":
							// before today
							if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						case "EXTENDED":
							// after today
							if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						case "REGISTERED":
							// after today
							if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						case "REREGISTERED":
							// after today
							if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
								assessorAccreditation.setValidStatus(true);
							} else {
								assessorAccreditation.setValidStatus(false);
							}
							break;
						default:
							break;
						}
					} else {
						assessorAccreditation.setValidStatus(false);
					}
				} catch (Exception e) {
					logger.fatal(e);
					assessorAccreditation.setValidStatus(false);
				}
				
//				assessorAccreditation.setProcessed(true);

				updateList.add(assessorAccreditation);
			
			}
			
			li = null;
			if (updateList.size() != 0) {
				dao.updateBatch(updateList);
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Assessor Accreditation Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Assessor Accreditation Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public Integer countAllLegacyAssessorAccreditationByProcessedValue(Boolean value) throws Exception {
		return dao.countAllLegacyAssessorAccreditationByProcessedValue(value);
	}

	public Integer countAllLegacyAssessorAccreditationByAppearsOnHomeAffairsDataValue(Boolean value) throws Exception {
		return dao.countAllLegacyAssessorAccreditationByAppearsOnHomeAffairsDataValue(value);
	}

	public Integer countAllLegacyAssessorAccreditationByValidRSAIDValue(Boolean value) throws Exception {
		return dao.countAllLegacyAssessorAccreditationByValidRSAIDValue(value);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationOnHomeAffairs(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationOnHomeAffairs(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationUnitStandards(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.idNo in (select x.assessorid from LegacyAssessorUnitStandard x where x.unitStandard.id = :unitStandardId)";
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationUnitStandards(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.idNo in (select x.assessorid from LegacyAssessorUnitStandard x where x.unitStandard.id = :unitStandardId)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationSkillsProgram(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		System.out.println("$$$$$$$$$:: skillsProgramID:: "+filters.get("skillsProgramID"));
		String hql = "select o from LegacyAssessorAccreditation o where o.idNo in (select x.assessorid from LegacyAssessorSkillsProgramme x where x.skillsProgram.id = :skillsProgrammeID)";
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationSkillsProgram(Map<String, Object> filters) throws Exception {
		System.out.println("##########:: skillsProgramID:: "+filters.get("skillsProgramID"));
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.idNo in (select x.assessorid from LegacyAssessorUnitStandard x where x.skillsProgram.id = :skillsProgrammeID)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationQualification(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.assessorRegNo in(select x.assessorRegNo from LegacyAssessorQualification x where x.qualification.id = :qualificationID)";
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationQualification(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.assessorRegNo in(select x.assessorRegNo from LegacyAssessorQualification x where x.qualification.id = :qualificationID)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationQualification123(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.idNo in(select x.assessorid from LegacyAssessorQualification x where x.qualification.id = :qualificationID)";
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationQualification123(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.idNo in(select x.assessorid from LegacyAssessorQualification x where x.qualification.id = :qualificationID)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationNotOnHomeAffairs(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public int countallLegacyAssessorAccreditationNotOnHomeAffairs(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationValidRSAID(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.validRsaIdNumber = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationValidRSAID(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.validRsaIdNumber = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorAccreditation> allLegacyAssessorAccreditationInvalidRSAID(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyAssessorAccreditation o where o.validRsaIdNumber = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyAssessorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countallLegacyAssessorAccreditationInvalidRSAID(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyAssessorAccreditation o where o.validRsaIdNumber = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	
	public void fixStatusOnLegacyAssessorAccreditation() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				fixStatus();
			}
		}).start();
	}
	
	private void fixStatus() {
		try {
			String registeredStatus = "Registered";
			String expiredStatus = "Expired";
			Date today = GenericUtility.getStartOfDay(getSynchronizedDate());
			
			List<IDataEntity> updateList = new ArrayList<>();
			updateList = new ArrayList<>();
			List<LegacyAssessorAccreditation> li = allLegacyAssessorAccreditation();
			
			for (LegacyAssessorAccreditation assessorAccreditation : li) {	
				try {
					// check if end date provided and reg number is not empty
					if (
							(assessorAccreditation.getAssessorRegEndDate() != null && !assessorAccreditation.getAssessorRegEndDate().isEmpty() && !assessorAccreditation.getAssessorRegEndDate().trim().toUpperCase().equals("NULL")) 
							&& (assessorAccreditation.getAssessorStatusDesc() != null && !assessorAccreditation.getAssessorStatusDesc().isEmpty())
							&& (assessorAccreditation.getAssessorRegNo() != null && !assessorAccreditation.getAssessorRegNo().isEmpty() && !assessorAccreditation.getAssessorRegNo().trim().toUpperCase().equals("NULL")) 
						) {
						// convert end date to java date
						Date endDate = GenericUtility.getStartOfDay(HAJConstants.sdfYYYYMMDD.parse(assessorAccreditation.getAssessorRegEndDate().trim()));
						// Validation that today before the end date
						if (today.before(endDate)) {
							// check if status is EXPIRED
							if (assessorAccreditation.getAssessorStatusDesc().trim().toUpperCase().equals(expiredStatus.toUpperCase())) {
								
								// copy old status to new filed
								assessorAccreditation.setAssessorStatusBeforeAlteration(assessorAccreditation.getAssessorStatusDesc());
								
								// update original status
								assessorAccreditation.setAssessorStatusDesc(registeredStatus);
								
								// set to valid status
								assessorAccreditation.setValidStatus(true);
								updateList.add(assessorAccreditation);
							}
						}
					}
				} catch (Exception e) {
				}
			}
			
			li = null;
			if (updateList.size() != 0) {
				dao.updateBatch(updateList);
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Assessor Accreditation Status Alteration", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Assessor Accreditation Status Alteration Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
