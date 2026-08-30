package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyOrganisationSitesDAO;
import haj.com.entity.Company;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacyPerson;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.CompanyService;
import haj.com.service.SarsEmployerDetailService;
import haj.com.utils.GenericUtility;

public class LegacyOrganisationSitesService extends AbstractService {
	/** The dao. */
	private LegacyOrganisationSitesDAO dao = new LegacyOrganisationSitesDAO();
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();
	private CompanyService companyService = new CompanyService();

	/**
	 * Get all LegacyOrganisationSites
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyOrganisationSites> allLegacyOrganisationSites() throws Exception {
		return dao.allLegacyOrganisationSites();
	}

	/**
	 * Create or update LegacyOrganisationSites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationSites
	 */
	public void create(LegacyOrganisationSites entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyOrganisationSites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationSites
	 */
	public void update(LegacyOrganisationSites entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyOrganisationSites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationSites
	 */
	public void delete(LegacyOrganisationSites entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyOrganisationSites}
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationSites
	 */
	public LegacyOrganisationSites findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyOrganisationSites by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationSites
	 */
	public List<LegacyOrganisationSites> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyOrganisationSites
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyOrganisationSites> allLegacyOrganisationSites(int first, int pageSize) throws Exception {
		return dao.allLegacyOrganisationSites(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyOrganisationSites for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyOrganisationSites
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyOrganisationSites.class);
	}

	/**
	 * Lazy load LegacyOrganisationSites with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyOrganisationSites class
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
	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSites(Class<LegacyOrganisationSites> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyOrganisationSites>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of LegacyOrganisationSites for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyOrganisationSites class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyOrganisationSites entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyOrganisationSites> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allSdlNumberOnSarsEmployerFileEmpty(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.sdlNumberOnSarsEmployerFile is null";
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public int countAllSdlNumberOnSarsEmployerFileEmpty( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.sdlNumberOnSarsEmployerFile is null";
		return dao.countWhere(filters, hql);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.sdlNumberOnSarsEmployerFile = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public int countAllSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.sdlNumberOnSarsEmployerFile = :value";
		return dao.countWhere(filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allMainSdlNumberOnSarsEmployerFileEmpty(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile is null";
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public int countAllMainSdlNumberOnSarsEmployerFileEmpty( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile is null";
		return dao.countWhere(filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allMainSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public int countAllMainSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile = :value";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyOrganisationSites> allEntries = allLegacyOrganisationSites();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyOrganisationSites> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllLegacyOrganisationSitesNotProcessed() throws Exception {
		return dao.countAllLegacyOrganisationSitesNotProcessed();
	}
	
	public Integer countBySdlNumber(String sdlNumber) throws Exception {
		return dao.countBySdlNumber(sdlNumber);
	}
	
	public List<LegacyOrganisationSites> findBySdlNumberList(String sdlNumber) throws Exception {
		return dao.findBySdlNumberList(sdlNumber);
	}
	
	public LegacyOrganisationSites findBySdlNumber(String sdlNumber) throws Exception {
		return dao.findBySdlNumber(sdlNumber);
	}
	
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyOrganisationSitesNotProcessed(numberOfEntries);
	}
	
	public void checkSdlNumberAgaintSarsEmployerProfile() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				runValidiation();
			}
		}).start();
	}

	/**
	 * @throws Exception
	 */
	public void runValidiation() {
		try {
			Integer count = countAllLegacyOrganisationSitesNotProcessed();	
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyOrganisationSites> sites = allLegacyOrganisationSitesNotProcessed(1000);
				for (LegacyOrganisationSites legacyOrganisationSites : sites) {
					
					// Main SDL number on SARS
					try {
						if (legacyOrganisationSites.getMainSDL() != null && !legacyOrganisationSites.getMainSDL().isEmpty()) {
							int counterMainSDL = sarsEmployerDetailService.countByRefNumber(legacyOrganisationSites.getMainSDL());
							if (counterMainSDL != 0) {
								legacyOrganisationSites.setMainSdlNumberOnSarsEmployerFile(true);
							} else {
								legacyOrganisationSites.setMainSdlNumberOnSarsEmployerFile(false);
							}
						} else {
							legacyOrganisationSites.setMainSdlNumberOnSarsEmployerFile(false);
						}
					} catch (Exception e) {
						legacyOrganisationSites.setMainSdlNumberOnSarsEmployerFile(false);
						logger.fatal(e);
					}
					
					// linked SDL number on SARS
					try {
						if (legacyOrganisationSites.getLinkedSdl() != null && !legacyOrganisationSites.getLinkedSdl().isEmpty()) {
							int counterLinked = sarsEmployerDetailService.countByRefNumber(legacyOrganisationSites.getLinkedSdl().trim());
							if (counterLinked != 0) {
								legacyOrganisationSites.setLinkedSdlNumberOnSarsEmployerFile(true);
							} else {
								legacyOrganisationSites.setLinkedSdlNumberOnSarsEmployerFile(false);
							}
						} else {
							legacyOrganisationSites.setLinkedSdlNumberOnSarsEmployerFile(false);
						}
					} catch (Exception e) {
						legacyOrganisationSites.setLinkedSdlNumberOnSarsEmployerFile(false);
						logger.fatal(e);
					}
					
					// check if duplicate 
					try {
						int counterDuplicate = countBySdlNumber(legacyOrganisationSites.getSdlNumber().trim());
						if (counterDuplicate == 1) {
							legacyOrganisationSites.setDuplicateSiteNumber(false);
						} else {
							legacyOrganisationSites.setDuplicateSiteNumber(true);
						}
					} catch (Exception e) {
						// if errors dont processes against company
						legacyOrganisationSites.setDuplicateSiteNumber(true);
						logger.fatal(e);
					}
					
					// not duplicate
					try {
						if (legacyOrganisationSites.getDuplicateSiteNumber() != null && !legacyOrganisationSites.getDuplicateSiteNumber()) {
							if (legacyOrganisationSites.getMainSdlNumberOnSarsEmployerFile() && legacyOrganisationSites.getMainSDL() != null && !legacyOrganisationSites.getMainSDL().isEmpty()) {
								// main SDL number on SARS file
								Company company = companyService.findByLevyNumber(legacyOrganisationSites.getMainSDL().trim());
								if (company != null && company.getId() != null) {
									legacyOrganisationSites.setCompany(company);
								}
							} else if (legacyOrganisationSites.getLinkedSdlNumberOnSarsEmployerFile() && legacyOrganisationSites.getLinkedSdl() != null && !legacyOrganisationSites.getLinkedSdl().isEmpty()) {
								// Linked SDL number on SARS file
								Company company = companyService.findByLevyNumber(legacyOrganisationSites.getLinkedSdl().trim());
								if (company != null && company.getId() != null) {
									legacyOrganisationSites.setCompany(company);
								}
							}
						}
					} catch (Exception e) {
						legacyOrganisationSites.setCompany(null);
						logger.fatal(e);
					}
					legacyOrganisationSites.setProcessed(true);
					updateList.add(legacyOrganisationSites);
				}
			
				sites = null;
				
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyOrganisationSitesNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Organisation Sites", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Organisation Sites", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	/* 
	 * REPORTING START 
	 */
	
	/* PROCESSED COUNT START */
	// count all entries by processed value
	public Integer countAllLegacyOrganisationSitesByProcessedValue(Boolean value) throws Exception {
		return dao.countAllLegacyOrganisationSitesByProcessedValue(value);
	}
	// lazy load entries by processed value
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesByProcessedValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.processed = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	// count lazy load entries by processed value	
	public int countAllLegacyOrganisationSitesByProcessedValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.processed = :value";
		return dao.countWhere(filters, hql);
	}
	/* PROCESSED COUNT END */
	
	
	/* DUPLICATE SITE NUMBER PROCESSED START */
	// Counts all Entries by duplicate Site Number Processed
	public Integer countAllLegacyOrganisationSitesDuplicateSiteNumbers(Boolean value) throws Exception {
		return dao.countAllLegacyOrganisationSitesDuplicateSiteNumbers(value);
	}
	// lazy load Entries by duplicate Site Number Processed
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesDuplicateSiteNumbers(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.duplicateSiteNumber = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	// count lazy load Entries by duplicate Site Number Processed
	public int countAllLegacyOrganisationSitesDuplicateSiteNumbers(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.duplicateSiteNumber = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	/* DUPLICATE SITE NUMBER PROCESSED END */
	
	
	/* MainSdlNumberOnSarsEmployerFile PROCESSED START */
	// Counts all Entries by MainSdlNumberOnSarsEmployerFile Processed
	public Integer countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
		return dao.countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(value);
	}
	// lazy load Entries Entries by MainSdlNumberOnSarsEmployerFile Processed
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	// count lazy load Entries by MainSdlNumberOnSarsEmployerFile Processed
	public int countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	/* MainSdlNumberOnSarsEmployerFile PROCESSED END */
	
	
	/* MainSdlNumberOnSarsEmployerFile PROCESSED START */
	// Counts all Entries by LinkedSdlNumberOnSarsEmployerFile Processed
	public Integer countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
		return dao.countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(value);
	}
	// lazy load Entries by LinkedSdlNumberOnSarsEmployerFile Processed
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.linkedSdlNumberOnSarsEmployerFile = :value and o.processed = :processedValue";
		filters.put("value", value);
		filters.put("processedValue", true);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	// count lazy load Entries by LinkedSdlNumberOnSarsEmployerFile Processed
	public int countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.linkedSdlNumberOnSarsEmployerFile = :value and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	/* MainSdlNumberOnSarsEmployerFile PROCESSED END */
	
	
	/* NotLinkedToCompany PROCESSED START */
	// Counts all Entries NotLinkedToCompany Processed
	public Integer countAllLegacyOrganisationSitesNotLinkedToCompany() throws Exception {
		return dao.countAllLegacyOrganisationSitesNotLinkedToCompany();
	}
	// lazy load Entries by LinkedSdlNumberOnSarsEmployerFile Processed
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesNotLinkedToCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.company is null and o.processed = :processedValue";
		filters.put("processedValue", true);
		return (List<LegacyOrganisationSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	// count lazy load Entries by LinkedSdlNumberOnSarsEmployerFile Processed
	public int countAllLegacyOrganisationSitesNotLinkedToCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationSites o where o.company is null and o.processed = :processedValue";
		return dao.countWhere(filters, hql);
	}
	/* NotLinkedToCompany PROCESSED END */
	
	/* 
	 * REPORTING END 
	 */
}
