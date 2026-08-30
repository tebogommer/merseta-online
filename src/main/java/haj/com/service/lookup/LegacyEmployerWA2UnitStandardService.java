package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyEmployerWA2UnitStandardDAO;
import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyEmployerWA2UnitStandardService extends AbstractService {
	/** The dao. */
	private LegacyEmployerWA2UnitStandardDAO dao = new LegacyEmployerWA2UnitStandardDAO();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();


	/**
	 * Get all LegacyEmployerWA2UnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandard() throws Exception {
		return dao.allLegacyEmployerWA2UnitStandard();
	}

	/**
	 * Create or update LegacyEmployerWA2UnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void create(LegacyEmployerWA2UnitStandard entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyEmployerWA2UnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void update(LegacyEmployerWA2UnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyEmployerWA2UnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void delete(LegacyEmployerWA2UnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public LegacyEmployerWA2UnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyEmployerWA2UnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public List<LegacyEmployerWA2UnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyEmployerWA2UnitStandard
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandard(int first, int pageSize)
			throws Exception {
		return dao.allLegacyEmployerWA2UnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyEmployerWA2UnitStandard for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyEmployerWA2UnitStandard
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyEmployerWA2UnitStandard.class);
	}

	/**
	 * Lazy load LegacyEmployerWA2UnitStandard with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyEmployerWA2UnitStandard class
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
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandard(
			Class<LegacyEmployerWA2UnitStandard> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyEmployerWA2UnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyEmployerWA2UnitStandard for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyEmployerWA2UnitStandard class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyEmployerWA2UnitStandard entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyEmployerWA2UnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyEmployerWA2UnitStandard> allEntries = allLegacyEmployerWA2UnitStandard();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyEmployerWA2UnitStandard> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyEmployerWA2UnitStandardNotProcessed() throws Exception {
		return dao.countAllLegacyEmployerWA2UnitStandardNotProcessed();
	}

	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandardNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyEmployerWA2UnitStandardNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyEmployerWA2UnitStandardNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyEmployerWA2UnitStandard> li = allLegacyEmployerWA2UnitStandardNotProcessed(1000);
				for (LegacyEmployerWA2UnitStandard entry : li) {


					
					if (entry.getSdlNo() != null && !entry.getSdlNo().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(entry.getSdlNo().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(entry.getSdlNo().trim());
								if (los != null && los.getId() != null) {
									entry.setLegacyOrganisationSites(los);
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
				count = countAllLegacyEmployerWA2UnitStandardNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy WA2 Unit Standard Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy WA2 Unit Standard Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyEmployerWA2UnitStandard> findByLevy(String linkedSdl) throws Exception {
		return dao.findByLevy(linkedSdl);
	}
	
	public List<LegacyEmployerWA2UnitStandard> findDistinctByLevy(String linkedSdl) throws Exception {
		return dao.findDistinctByLevy(linkedSdl);
	}
	
	public List<LegacyEmployerWA2UnitStandard> findDistinctByLevy(String linkedSdl, String sdlNo) throws Exception {
		return dao.findDistinctByLevy(linkedSdl, sdlNo);
	}
}
