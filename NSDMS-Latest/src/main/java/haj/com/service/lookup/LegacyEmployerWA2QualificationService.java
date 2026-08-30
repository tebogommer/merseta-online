package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyEmployerWA2QualificationDAO;
import haj.com.entity.lookup.LegacyEmployerWA2Qualification;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyEmployerWA2QualificationService extends AbstractService {
	/** The dao. */
	private LegacyEmployerWA2QualificationDAO dao = new LegacyEmployerWA2QualificationDAO();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();


	/**
	 * Get all LegacyEmployerWA2Qualification
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
	 * @throws Exception the exception
	 */
	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2Qualification() throws Exception {
		return dao.allLegacyEmployerWA2Qualification();
	}

	/**
	 * Create or update LegacyEmployerWA2Qualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyEmployerWA2Qualification
	 */
	public void create(LegacyEmployerWA2Qualification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyEmployerWA2Qualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyEmployerWA2Qualification
	 */
	public void update(LegacyEmployerWA2Qualification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyEmployerWA2Qualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyEmployerWA2Qualification
	 */
	public void delete(LegacyEmployerWA2Qualification entity) throws Exception {
		this.dao.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyEmployerWA2Qualification> allEntries = allLegacyEmployerWA2Qualification();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyEmployerWA2Qualification> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyEmployerWA2Qualification}
	 * @throws Exception the exception
	 * @see LegacyEmployerWA2Qualification
	 */
	public LegacyEmployerWA2Qualification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyEmployerWA2Qualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
	 * @throws Exception the exception
	 * @see LegacyEmployerWA2Qualification
	 */
	public List<LegacyEmployerWA2Qualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyEmployerWA2Qualification
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
	 * @throws Exception the exception
	 */
	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2Qualification(int first, int pageSize)
			throws Exception {
		return dao.allLegacyEmployerWA2Qualification(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyEmployerWA2Qualification for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyEmployerWA2Qualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyEmployerWA2Qualification.class);
	}

	/**
	 * Lazy load LegacyEmployerWA2Qualification with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyEmployerWA2Qualification class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2Qualification(
			Class<LegacyEmployerWA2Qualification> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyEmployerWA2Qualification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);
	}

	/**
	 * Get count of LegacyEmployerWA2Qualification for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyEmployerWA2Qualification class
	 * @param filters the filters
	 * @return Number of rows in the LegacyEmployerWA2Qualification entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyEmployerWA2Qualification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyEmployerWA2QualificationNotProcessed() throws Exception {
		return dao.countAllLegacyEmployerWA2QualificationNotProcessed();
	}

	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2QualificationNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyEmployerWA2QualificationNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyEmployerWA2QualificationNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyEmployerWA2Qualification> li = allLegacyEmployerWA2QualificationNotProcessed(1000);
				for (LegacyEmployerWA2Qualification entry : li) {


					
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
				count = countAllLegacyEmployerWA2QualificationNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy WA2Qualification Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy WA2Qualification Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyEmployerWA2Qualification> findByLevy(String linkedSdl) throws Exception {
		return dao.findByLevy(linkedSdl);
	}
	
	public List<LegacyEmployerWA2Qualification> findDistinctByLevy(String linkedSdl) throws Exception {
		return dao.findDistinctByLevy(linkedSdl);
	}
	
	public List<LegacyEmployerWA2Qualification> findDistinctByLevy(String linkedSdl, String sdlNo) throws Exception {
		return dao.findDistinctByLevy(linkedSdl, sdlNo);
	}
}
