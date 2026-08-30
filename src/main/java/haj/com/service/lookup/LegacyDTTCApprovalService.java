package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyDTTCApprovalDAO;
import haj.com.entity.lookup.LegacyDTTCApproval;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.CompanyService;
import haj.com.service.SarsEmployerDetailService;
import haj.com.utils.GenericUtility;

public class LegacyDTTCApprovalService extends AbstractService {
	/** The dao. */
	private LegacyDTTCApprovalDAO dao = new LegacyDTTCApprovalDAO();

	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();
	private CompanyService companyService = new CompanyService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();

	/**
	 * Get all LegacyDTTCApproval
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCApproval> allLegacyDTTCApproval() throws Exception {
		return dao.allLegacyDTTCApproval();
	}

	/**
	 * Create or update LegacyDTTCApproval.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCApproval
	 */
	public void create(LegacyDTTCApproval entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyDTTCApproval.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCApproval
	 */
	public void update(LegacyDTTCApproval entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyDTTCApproval.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCApproval
	 */
	public void delete(LegacyDTTCApproval entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyDTTCApproval}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCApproval
	 */
	public LegacyDTTCApproval findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyDTTCApproval by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCApproval
	 */
	public List<LegacyDTTCApproval> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyDTTCApproval
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCApproval> allLegacyDTTCApproval(int first, int pageSize) throws Exception {
		return dao.allLegacyDTTCApproval(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyDTTCApproval for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyDTTCApproval
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyDTTCApproval.class);
	}

	/**
	 * Lazy load LegacyDTTCApproval with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyDTTCApproval class
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
	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCApproval> allLegacyDTTCApproval(Class<LegacyDTTCApproval> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyDTTCApproval>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyDTTCApproval for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyDTTCApproval class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyDTTCApproval entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyDTTCApproval> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyDTTCApproval> allEntries = allLegacyDTTCApproval();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyDTTCApproval> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyDTTCApprovalNotProcessed() throws Exception {
		return dao.countAllLegacyDTTCApprovalNotProcessed();
	}

	public List<LegacyDTTCApproval> allLegacyDTTCApprovalNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyDTTCApprovalNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyDTTCApprovalNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyDTTCApproval> li = allLegacyDTTCApprovalNotProcessed(1000);
				for (LegacyDTTCApproval legacyDTTCApproval : li) {
					// On Sites Table
					if (legacyDTTCApproval.getLinkedSdl() != null && !legacyDTTCApproval.getLinkedSdl().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacyDTTCApproval.getLinkedSdl().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacyDTTCApproval.getLinkedSdl().trim());
								if (los != null && los.getId() != null) {
									// legacyDTTCApproval.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					legacyDTTCApproval.setProcessed(true);

					updateList.add(legacyDTTCApproval);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyDTTCApprovalNotProcessed();
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
