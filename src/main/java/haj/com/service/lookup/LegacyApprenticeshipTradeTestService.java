package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyApprenticeshipTradeTestDAO;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.utils.GenericUtility;

public class LegacyApprenticeshipTradeTestService extends AbstractService {
	/** The dao. */
	private LegacyApprenticeshipTradeTestDAO dao = new LegacyApprenticeshipTradeTestDAO();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();

	/**
	 * Get all LegacyApprenticeshipTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTest() throws Exception {
		return dao.allLegacyApprenticeshipTradeTest();
	}

	/**
	 * Create or update LegacyApprenticeshipTradeTest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void create(LegacyApprenticeshipTradeTest entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyApprenticeshipTradeTest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void update(LegacyApprenticeshipTradeTest entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyApprenticeshipTradeTest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void delete(LegacyApprenticeshipTradeTest entity) throws Exception {
		this.dao.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyApprenticeshipTradeTest> allEntries = allLegacyApprenticeshipTradeTest();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyApprenticeshipTradeTest> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyApprenticeshipTradeTest}
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeshipTradeTest
	 */
	public LegacyApprenticeshipTradeTest findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyApprenticeshipTradeTest by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeshipTradeTest
	 */
	public List<LegacyApprenticeshipTradeTest> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyApprenticeshipTradeTest
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTest(int first, int pageSize) throws Exception {
		return dao.allLegacyApprenticeshipTradeTest(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyApprenticeshipTradeTest for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyApprenticeshipTradeTest
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyApprenticeshipTradeTest.class);
	}

	/**
	 * Lazy load LegacyApprenticeshipTradeTest with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyApprenticeshipTradeTest class
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
	 * @return a list of {@link haj.com.entity.LegacyApprenticeshipTradeTest}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTest(Class<LegacyApprenticeshipTradeTest> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyApprenticeshipTradeTest>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyApprenticeshipTradeTest for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyApprenticeshipTradeTest class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyApprenticeshipTradeTest entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyApprenticeshipTradeTest> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countByRsaIdNumber(String idNumber) throws Exception {
		return dao.countByRsaIdNumber(idNumber);
	}
	
	public Integer countByLegacyApprenticeship(LegacyApprenticeship legacyApprenticeship) throws Exception {
		return dao.countByLegacyApprenticeship(legacyApprenticeship.getIdNo(), legacyApprenticeship.getQualification().getId());
	}

	public Integer countAllLegacyApprenticeshipTradeTestNotProcessed() throws Exception {
		return dao.countAllLegacyApprenticeshipTradeTestNotProcessed();
	}

	public List<LegacyApprenticeshipTradeTest> allLegacyApprenticeshipTradeTestNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyApprenticeshipTradeTestNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyApprenticeshipTradeTestNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyApprenticeshipTradeTest> li = allLegacyApprenticeshipTradeTestNotProcessed(1000);
				for (LegacyApprenticeshipTradeTest entry : li) {

					// ID Validation
					if (entry.getIdNo() != null && !entry.getIdNo().trim().isEmpty()) {
						try {
							entry.setValidRsaIdNumber(GenericUtility.checkRsaId(entry.getIdNo().trim()));
						} catch (Exception e) {
							entry.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(entry.getIdNo().trim());
						if (entriesFound > 0) {
							entry.setAppearsOnHomeAffairsData(true);
						} else {
							entry.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						entry.setAppearsOnHomeAffairsData(false);
					}

					if (entry.getSdlNo() != null && !entry.getSdlNo().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(entry.getSdlNo().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(entry.getSdlNo().trim());
								if (los != null && los.getId() != null) {
									// entry.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					// entry.setProcessed(true);
					updateList.add(entry);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyApprenticeshipTradeTestNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Apprenticeship Trade Test Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Apprenticeship Trade Test Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyApprenticeshipTradeTest> findByLegacyApprenticeship(LegacyApprenticeship legacyApprenticeship) throws Exception {
		List<LegacyApprenticeshipTradeTest> list = new  ArrayList<>();
		if(legacyApprenticeship.getQualification() != null) {
			list = dao.findByIdNo(legacyApprenticeship.getIdNo(), legacyApprenticeship.getQualification().getId());
		}
		return list;
	}
}
