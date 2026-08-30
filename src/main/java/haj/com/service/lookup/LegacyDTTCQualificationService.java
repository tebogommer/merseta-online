package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyDTTCQualificationDAO;
import haj.com.entity.lookup.LegacyDTTCQualification;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyDTTCQualificationService extends AbstractService {
	/** The dao. */
	private LegacyDTTCQualificationDAO dao = new LegacyDTTCQualificationDAO();

	private QualificationService qualificationService;
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	/**
	 * Get all LegacyDTTCQualification
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification}
	 * @throws Exception the exception
	 */
	public List<LegacyDTTCQualification> allLegacyDTTCQualification() throws Exception {
		return dao.allLegacyDTTCQualification();
	}

	/**
	 * Create or update LegacyDTTCQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyDTTCQualification
	 */
	public void create(LegacyDTTCQualification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyDTTCQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyDTTCQualification
	 */
	public void update(LegacyDTTCQualification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyDTTCQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyDTTCQualification
	 */
	public void delete(LegacyDTTCQualification entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyDTTCQualification}
	 * @throws Exception the exception
	 * @see LegacyDTTCQualification
	 */
	public LegacyDTTCQualification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyDTTCQualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification}
	 * @throws Exception the exception
	 * @see LegacyDTTCQualification
	 */
	public List<LegacyDTTCQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyDTTCQualification
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification}
	 * @throws Exception the exception
	 */
	public List<LegacyDTTCQualification> allLegacyDTTCQualification(int first, int pageSize) throws Exception {
		return dao.allLegacyDTTCQualification(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyDTTCQualification for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyDTTCQualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyDTTCQualification.class);
	}

	/**
	 * Lazy load LegacyDTTCQualification with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyDTTCQualification class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification} containing
	 *         data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCQualification> allLegacyDTTCQualification(Class<LegacyDTTCQualification> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyDTTCQualification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyDTTCQualification for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyDTTCQualification class
	 * @param filters the filters
	 * @return Number of rows in the LegacyDTTCQualification entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyDTTCQualification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyDTTCQualification> allEntries = allLegacyDTTCQualification();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyDTTCQualification> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyDTTCQualificationNotProcessed() throws Exception {
		return dao.countAllLegacyDTTCQualificationNotProcessed();
	}

	public List<LegacyDTTCQualification> allLegacyDTTCQualificationNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyDTTCQualificationNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyDTTCQualificationNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyDTTCQualification> li = allLegacyDTTCQualificationNotProcessed(1000);
				for (LegacyDTTCQualification legacyDTTCQualification : li) {
					if (qualificationService == null) {
						qualificationService = new QualificationService();
					}
					if (legacyDTTCQualification.getQualCode() != null
							&& !legacyDTTCQualification.getQualCode().isEmpty()) {
						try {
							Qualification qua = qualificationService
									.findByCodeString(legacyDTTCQualification.getQualCode().trim());
							if (qua != null && qua.getId() != null) {
								legacyDTTCQualification.setQualification(qua);
							}
						} catch (Exception e) {
						}
					}

					legacyDTTCQualification.setProcessed(true);

					updateList.add(legacyDTTCQualification);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyDTTCQualificationNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: DTTC Qualification Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: DTTC Qualification Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
