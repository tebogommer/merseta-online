package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyLearnershipAssessmentDAO;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyLearnershipAssessment;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyLearnershipAssessmentService extends AbstractService {
	/** The dao. */
	private LegacyLearnershipAssessmentDAO dao = new LegacyLearnershipAssessmentDAO();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	/**
	 * Get all LegacyLearnershipAssessment
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessment() throws Exception {
		return dao.allLegacyLearnershipAssessment();
	}

	/**
	 * Create or update LegacyLearnershipAssessment.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment
	 */
	public void create(LegacyLearnershipAssessment entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyLearnershipAssessment.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment
	 */
	public void update(LegacyLearnershipAssessment entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyLearnershipAssessment.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment
	 */
	public void delete(LegacyLearnershipAssessment entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyLearnershipAssessment}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment
	 */
	public LegacyLearnershipAssessment findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyLearnershipAssessment by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment
	 */
	public List<LegacyLearnershipAssessment> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyLearnershipAssessment
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessment(int first, int pageSize) throws Exception {
		return dao.allLegacyLearnershipAssessment(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyLearnershipAssessment for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyLearnershipAssessment
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyLearnershipAssessment.class);
	}

	/**
	 * Lazy load LegacyLearnershipAssessment with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyLearnershipAssessment class
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
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessment(Class<LegacyLearnershipAssessment> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyLearnershipAssessment>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyLearnershipAssessment for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyLearnershipAssessment class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyLearnershipAssessment entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyLearnershipAssessment> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyLearnershipAssessment> allEntries = allLegacyLearnershipAssessment();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyLearnershipAssessment> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyLearnershipAssessmentNotProcessed() throws Exception {
		return dao.countAllLegacyLearnershipAssessmentNotProcessed();
	}

	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessmentNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyLearnershipAssessmentNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyLearnershipAssessmentNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyLearnershipAssessment> li = allLegacyLearnershipAssessmentNotProcessed(1000);
				for (LegacyLearnershipAssessment entry : li) {

					// ID Validation

					if (entry.getLearnerId() != null && !entry.getLearnerId().trim().isEmpty()) {
						try {
							entry.setValidRsaIdNumber(GenericUtility.checkRsaId(entry.getLearnerId().trim()));
						} catch (Exception e) {
							entry.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file

					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(entry.getLearnerId().trim());
						if (entriesFound > 0) {
							entry.setAppearsOnHomeAffairsData(true);
						} else {
							entry.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						entry.setAppearsOnHomeAffairsData(false);
					}

					// entry.setProcessed(true);

					updateList.add(entry);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyLearnershipAssessmentNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Assessments Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Assessments Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyLearnershipAssessment> findByLegacyLegacyLearnership(LegacyLearnership legacyLearnership) {
		List<LegacyLearnershipAssessment> list = new  ArrayList<>();
		if(legacyLearnership.getLearnership() != null) {
			list = dao.findByIdNo(legacyLearnership.getIdNo(), legacyLearnership.getLearnership().getId());
		}
		return list;
	}

}
