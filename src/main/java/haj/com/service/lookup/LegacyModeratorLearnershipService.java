package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyModeratorLearnershipDAO;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyModeratorLearnershipService extends AbstractService {
	/** The dao. */
	private LegacyModeratorLearnershipDAO dao = new LegacyModeratorLearnershipDAO();

	private QualificationService qualificationService = new QualificationService();

	/**
	 * Get all LegacyModeratorLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
	 * @throws Exception the exception
	 */
	public List<LegacyModeratorLearnership> allLegacyModeratorLearnership() throws Exception {
		return dao.allLegacyModeratorLearnership();
	}

	/**
	 * Create or update LegacyModeratorLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorLearnership
	 */
	public void create(LegacyModeratorLearnership entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyModeratorLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorLearnership
	 */
	public void update(LegacyModeratorLearnership entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyModeratorLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorLearnership
	 */
	public void delete(LegacyModeratorLearnership entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyModeratorLearnership}
	 * @throws Exception the exception
	 * @see LegacyModeratorLearnership
	 */
	public LegacyModeratorLearnership findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyModeratorLearnership by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
	 * @throws Exception the exception
	 * @see LegacyModeratorLearnership
	 */
	public List<LegacyModeratorLearnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyModeratorLearnership
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
	 * @throws Exception the exception
	 */
	public List<LegacyModeratorLearnership> allLegacyModeratorLearnership(int first, int pageSize) throws Exception {
		return dao.allLegacyModeratorLearnership(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyModeratorLearnership for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyModeratorLearnership
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyModeratorLearnership.class);
	}

	/**
	 * Lazy load LegacyModeratorLearnership with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyModeratorLearnership class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorLearnership> allLegacyModeratorLearnership(Class<LegacyModeratorLearnership> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyModeratorLearnership>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyModeratorLearnership for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyModeratorLearnership class
	 * @param filters the filters
	 * @return Number of rows in the LegacyModeratorLearnership entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyModeratorLearnership> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyModeratorLearnership> allEntries = allLegacyModeratorLearnership();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyModeratorLearnership> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyModeratorLearnershipNotProcessed() throws Exception {
		return dao.countAllLegacyModeratorLearnershipNotProcessed();
	}

	public List<LegacyModeratorLearnership> allLegacyModeratorLearnershipNotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyModeratorLearnershipNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyModeratorLearnershipNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyModeratorLearnership> li = allLegacyModeratorLearnershipNotProcessed(1000);
				for (LegacyModeratorLearnership legacyModeratorLearnership : li) {
					// Valid ID Number
					if (legacyModeratorLearnership.getAssessorId() != null
							&& !legacyModeratorLearnership.getAssessorId().trim().isEmpty()) {
						try {
							legacyModeratorLearnership.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyModeratorLearnership.getAssessorId().trim()));
						} catch (Exception e) {
							legacyModeratorLearnership.setValidRsaIdNumber(false);
						}
					}
					// On SAQA Table
					if (legacyModeratorLearnership.getSaqaUnitStandardId() != null
							&& !legacyModeratorLearnership.getSaqaUnitStandardId().isEmpty()) {
						try {
							Qualification qua = qualificationService
									.findByCodeString(legacyModeratorLearnership.getSaqaUnitStandardId().trim());
							if (qua != null && qua.getId() != null) {
								legacyModeratorLearnership.setQualification(qua);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					legacyModeratorLearnership.setProcessed(true);

					updateList.add(legacyModeratorLearnership);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyModeratorLearnershipNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Moderator Learnership Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Moderator Learnership Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyModeratorLearnership> findByModeratorRegNo(String certificateNumber) throws Exception {
		return resolveExpiryDate(dao.findByModeratorRegNo(certificateNumber));
	}

	public List<LegacyModeratorLearnership> resolveExpiryDate(List<LegacyModeratorLearnership> list) {
		if (list != null && list.size() > 0) {
			for (LegacyModeratorLearnership llp : list) {
				if (llp.getLearnership() != null && llp.getLearnership().getQualification() != null
						&& llp.getLearnership().getQualification().getLastDateForEnrolment() != null) {
					if (llp.getLearnership().getQualification().getLastDateForEnrolment().before(new Date())) {
						llp.setQualificationExpired(true);
					} else {
						llp.setQualificationExpired(false);
					}
				}
			}
		}

		return list;
	}
}
