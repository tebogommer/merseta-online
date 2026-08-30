package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyExperientialDAO;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyExperientialService extends AbstractService {
	/** The dao. */
	private LegacyExperientialDAO dao = new LegacyExperientialDAO();

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();
	
	private static LegacyExperientialService legacyExperientialService = null;
	public static synchronized LegacyExperientialService instance() {
		if (legacyExperientialService == null) {
			legacyExperientialService = new LegacyExperientialService();
		}
		return legacyExperientialService;
	}

	/**
	 * Get all LegacyExperiential
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 * @return a list of {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyExperiential> allLegacyExperiential() throws Exception {
		return dao.allLegacyExperiential();
	}

	/**
	 * Create or update LegacyExperiential.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyExperiential
	 */
	public void create(LegacyExperiential entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyExperiential.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyExperiential
	 */
	public void update(LegacyExperiential entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyExperiential.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyExperiential
	 */
	public void delete(LegacyExperiential entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception
	 *             the exception
	 * @see LegacyExperiential
	 */
	public LegacyExperiential findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyExperiential by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception
	 *             the exception
	 * @see LegacyExperiential
	 */
	public List<LegacyExperiential> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyExperiential
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyExperiential> allLegacyExperiential(int first, int pageSize) throws Exception {
		return dao.allLegacyExperiential(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyExperiential for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyExperiential
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyExperiential.class);
	}

	/**
	 * Lazy load LegacyExperiential with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyExperiential class
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
	 * @return a list of {@link haj.com.entity.LegacyExperiential} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyExperiential> allLegacyExperiential(Class<LegacyExperiential> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyExperiential>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyExperiential for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyExperiential class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyExperiential entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyExperiential> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyExperiential> allEntries = allLegacyExperiential();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyExperiential> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyExperientialNotProcessed() throws Exception {
		return dao.countAllLegacyExperientialNotProcessed();
	}

	public List<LegacyExperiential> allLegacyExperientialNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyExperientialNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyExperientialNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyExperiential> li = allLegacyExperientialNotProcessed(1000);
				for (LegacyExperiential legacyExperiential : li) {
					// ID Validation
					if (legacyExperiential.getIdNo() != null && !legacyExperiential.getIdNo().trim().isEmpty()) {
						try {
							legacyExperiential.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyExperiential.getIdNo().trim()));
						} catch (Exception e) {
							legacyExperiential.setValidRsaIdNumber(false);
						}
					}
					// On SAQA Table
					if (legacyExperiential.getSaqaID() != null && !legacyExperiential.getSaqaID().isEmpty()) {
						try {
							Qualification qua = qualificationService.findByCodeString(legacyExperiential.getSaqaID().trim());
							if (qua != null && qua.getId() != null) {
								legacyExperiential.setQualification(qua);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyExperiential.getIdNo().trim());
						if (entriesFound > 0) {
							legacyExperiential.setAppearsOnHomeAffairsData(true);
						} else {
							legacyExperiential.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyExperiential.setAppearsOnHomeAffairsData(false);
					}
					// On Sites Table
					if (legacyExperiential.getEmployerSdl() != null && !legacyExperiential.getEmployerSdl().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacyExperiential.getEmployerSdl().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacyExperiential.getEmployerSdl().trim());
								if (los != null && los.getId() != null) {
									// legacyExperiential.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					legacyExperiential.setProcessed(true);

					updateList.add(legacyExperiential);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyExperientialNotProcessed();
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
