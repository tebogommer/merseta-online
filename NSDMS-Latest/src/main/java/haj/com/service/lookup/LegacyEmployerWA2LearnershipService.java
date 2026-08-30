package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyEmployerWA2LearnershipDAO;
import haj.com.entity.lookup.LegacyEmployerWA2Learnership;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyEmployerWA2LearnershipService extends AbstractService {
	/** The dao. */
	private LegacyEmployerWA2LearnershipDAO dao = new LegacyEmployerWA2LearnershipDAO();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();


	/**
	 * Get all LegacyEmployerWA2Learnership
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Learnership}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyEmployerWA2Learnership> allLegacyEmployerWA2Learnership() throws Exception {
		return dao.allLegacyEmployerWA2Learnership();
	}

	/**
	 * Create or update LegacyEmployerWA2Learnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2Learnership
	 */
	public void create(LegacyEmployerWA2Learnership entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyEmployerWA2Learnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2Learnership
	 */
	public void update(LegacyEmployerWA2Learnership entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyEmployerWA2Learnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2Learnership
	 */
	public void delete(LegacyEmployerWA2Learnership entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyEmployerWA2Learnership}
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2Learnership
	 */
	public LegacyEmployerWA2Learnership findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyEmployerWA2Learnership by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Learnership}
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2Learnership
	 */
	public List<LegacyEmployerWA2Learnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyEmployerWA2Learnership
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Learnership}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyEmployerWA2Learnership> allLegacyEmployerWA2Learnership(int first, int pageSize)
			throws Exception {
		return dao.allLegacyEmployerWA2Learnership(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyEmployerWA2Learnership for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyEmployerWA2Learnership
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyEmployerWA2Learnership.class);
	}

	/**
	 * Lazy load LegacyEmployerWA2Learnership with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyEmployerWA2Learnership class
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
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Learnership}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Learnership> allLegacyEmployerWA2Learnership(
			Class<LegacyEmployerWA2Learnership> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyEmployerWA2Learnership>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyEmployerWA2Learnership for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyEmployerWA2Learnership class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyEmployerWA2Learnership entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyEmployerWA2Learnership> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyEmployerWA2Learnership> allEntries = allLegacyEmployerWA2Learnership();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyEmployerWA2Learnership> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyEmployerWA2LearnershipNotProcessed() throws Exception {
		return dao.countAllLegacyEmployerWA2LearnershipNotProcessed();
	}

	public List<LegacyEmployerWA2Learnership> allLegacyEmployerWA2LearnershipNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyEmployerWA2LearnershipNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyEmployerWA2LearnershipNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyEmployerWA2Learnership> li = allLegacyEmployerWA2LearnershipNotProcessed(1000);
				for (LegacyEmployerWA2Learnership entry : li) {


					
					if (entry.getSdlNo() != null && !entry.getSdlNo().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(entry.getSdlNo().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(entry.getSdlNo().trim());
								if (los != null && los.getId() != null) {
//									entry.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					entry.setProcessed(true);

					updateList.add(entry);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyEmployerWA2LearnershipNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership  Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public List<LegacyEmployerWA2Learnership> findByLevy(String levyNumber) throws Exception {
		return dao.findByLevy(levyNumber);
	}
	
	public List<LegacyEmployerWA2Learnership> findDistinctByLevy(String levyNumber) throws Exception {
		return dao.findDistinctByLevy(levyNumber);
	}
	
	public List<LegacyEmployerWA2Learnership> findDistinctByLevy(String levyNumber, String sdlNo) throws Exception {
		return dao.findDistinctByLevy(levyNumber, sdlNo);
	}
}
