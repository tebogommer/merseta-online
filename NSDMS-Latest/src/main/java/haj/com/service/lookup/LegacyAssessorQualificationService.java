package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyAssessorQualificationDAO;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LegacyAssessorQualificationService extends AbstractService {
	/** The dao. */
	private LegacyAssessorQualificationDAO dao = new LegacyAssessorQualificationDAO();

	/* Service Levels */
	private QualificationService qualificationService = new QualificationService();

	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	/**
	 * Get all LegacyAssessorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyAssessorQualification> allLegacyAssessorQualification() throws Exception {
		return dao.allLegacyAssessorQualification();
	}

	/**
	 * Create or update LegacyAssessorQualification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorQualification
	 */
	public void create(LegacyAssessorQualification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyAssessorQualification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorQualification
	 */
	public void update(LegacyAssessorQualification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyAssessorQualification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorQualification
	 */
	public void delete(LegacyAssessorQualification entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorQualification
	 */
	public LegacyAssessorQualification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyAssessorQualification by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception
	 *             the exception
	 * @see LegacyAssessorQualification
	 */
	public List<LegacyAssessorQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyAssessorQualification
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyAssessorQualification> allLegacyAssessorQualification(int first, int pageSize) throws Exception {
		return dao.allLegacyAssessorQualification(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyAssessorQualification for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyAssessorQualification
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyAssessorQualification.class);
	}

	/**
	 * Lazy load LegacyAssessorQualification with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyAssessorQualification class
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
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> allLegacyAssessorQualification(Class<LegacyAssessorQualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyAssessorQualification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyAssessorQualification for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyAssessorQualification class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyAssessorQualification entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyAssessorQualification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyAssessorQualification> allEntries = allLegacyAssessorQualification();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyAssessorQualification> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData, 1000);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public List<LegacyAssessorQualification> findByAssessorIdNumber(String idNumber) throws Exception {
		return dao.findByAssessorIdNumber(idNumber);
	}

	public List<LegacyAssessorQualification> findByAssessorRegNo(String assessorRegNo) throws Exception {
		return resolveQualExpiryDate(dao.findByAssessorRegNo(assessorRegNo));
	}

	public List<LegacyAssessorQualification> resolveQualExpiryDate(List<LegacyAssessorQualification> legacyAssessorQualificationList) {
		if (legacyAssessorQualificationList != null && legacyAssessorQualificationList.size() > 0) {
			for (LegacyAssessorQualification qual : legacyAssessorQualificationList) {
				if (qual.getQualification() != null && qual.getQualification().getLastDateForEnrolment() != null) {
					if (qual.getQualification().getLastDateForEnrolment().before(new Date())) {
						qual.setQualificationExpired(true);
					} else {
						qual.setQualificationExpired(false);
					}
				}
			}
		}

		return legacyAssessorQualificationList;
	}

	public List<LegacyAssessorQualification> findByAssessorIdNumberRegNo(String entry) throws Exception {
		return dao.findByAssessorIdNumberRegNo(entry);
	}

	public Integer countAllLegacyAssessorQualificationNotProcessed() throws Exception {
		return dao.countAllLegacyAssessorQualificationNotProcessed();
	}

	public List<LegacyAssessorQualification> allLegacyAssessorQualificationNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyAssessorQualificationNotProcessed(numberOfEntries);
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
			if (qualificationService == null) {
				qualificationService = new QualificationService();
			}
			logger.info("validiationRun() Started");
			Integer count = countAllLegacyAssessorQualificationNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyAssessorQualification> entityList = allLegacyAssessorQualificationNotProcessed(1000);
				for (LegacyAssessorQualification entry : entityList) {
					if (entry.getAssessorId() != null && !entry.getAssessorId().trim().isEmpty()) {
						try {
							entry.setValidRsaIdNumber(GenericUtility.checkRsaId(entry.getAssessorId().trim()));
						} catch (Exception e) {
							entry.setValidRsaIdNumber(false);
						}
					}
					if (entry.getQualCode() != null && !entry.getQualCode().isEmpty()) {
						try {
							Qualification qua = qualificationService.findByCodeString(entry.getQualCode().trim());
							if (qua != null && qua.getId() != null) {
								entry.setQualification(qua);
							}
						} catch (Exception e) {
						}
					}
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(entry.getAssessorId().trim());
						if (entriesFound > 0) {
							entry.setAppearsOnHomeAffairsData(true);
						} else {
							entry.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						entry.setAppearsOnHomeAffairsData(false);
					}
					updateList.add(entry);
				}
				entityList = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList, 1000);
				}
				count = countAllLegacyAssessorQualificationNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Experiential Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Experiential Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

}
