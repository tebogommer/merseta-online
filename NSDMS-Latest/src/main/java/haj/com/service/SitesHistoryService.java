package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.SitesHistoryDAO;
import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.entity.SitesHistory;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesService.
 */
/**
 * @author Christoph Sibiya
 *
 */
public class SitesHistoryService extends AbstractService {
	/** The dao. */
	private SitesHistoryDAO dao = new SitesHistoryDAO();
	private static SitesHistoryService sitesHistoryService = null;
	
	/**
	 * Instance.
	 *
	 * @return the SitesHistoryService
	 */
	public static synchronized SitesHistoryService instance() {
		if (sitesHistoryService == null) {
			sitesHistoryService = new SitesHistoryService();
		}
		return sitesHistoryService;
	}

	/**
	 * Get all SitesHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 *             the exception
	 * @see SitesHistory
	 */
	public List<SitesHistory> allHistorySites() throws Exception {
		return dao.allSitesHistory();
	}

	/**
	 * Create or update SitesHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void create(SitesHistory entity) throws Exception {
		if (entity.getId() == null) {
			// AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
			AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
			this.dao.create(entity);
			// entity.getRegisteredAddress().setSites(entity);
		} else {
			AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
			this.dao.update(entity);
		}
	
	}

	/**
	 * Update SitesHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SitesHistory
	 */
	public void update(SitesHistory entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SitesHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void delete(SitesHistory entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SitesHistory}
	 * @throws Exception
	 *             the exception
	 * @see SitesHistory
	 */
	public SitesHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SitesHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception
	 *             the exception
	 * @see SitesHistory
	 */
	public List<SitesHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SitesHistory.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 *             the exception
	 */
	public List<SitesHistory> allSitesHistory(int first, int pageSize) throws Exception {
		return dao.allSitesHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SitesHistory for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Sites
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SitesHistory.class);
	}

	/**
	 * Lazy load SitesHistory with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Sites class
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
	 * @return a list of {@link haj.com.entity.SitesHistory} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> allSitesHistory(Class<Sites> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SitesHistory>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SitesHistory for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Sites class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Sites entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SitesHistory> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<SitesHistory> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}
	
	public Integer countByCompany(Company company) throws Exception {
		return dao.countByCompany(company);
	}

	public Long findSumByCompany(Company company) throws Exception {
		return dao.findSumByCompany(company);
	}

	public String generateSiteNumber(SitesHistory sitesHistory) {
		return sitesHistory.getCompanyName().toUpperCase().replaceAll(" ", "").substring(0, 3);
	}

	public List<SitesHistory> findByNameAndCompany(String name, Company company) throws Exception {
		return dao.findByNameAndCompany(name, company);
	}
	
	public void createHistory(Long sitesId) throws Exception {
		if (sitesId != null)
		{
			Sites entity = SitesService.instance().findByKey(sitesId);
			SitesHistory sitesHistory = new SitesHistory(entity);
			BeanUtils.copyProperties(sitesHistory, entity);
			this.dao.create(sitesHistory);
		}
	}
	
	/**
	 * Find SitesHistory by Site.
	 *
	 * @author TechFinium
	 * @param Site the site
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> findBySite(Sites site) throws Exception {
	 	return dao.findBySite(site);
	}
	
	public void removeSiteHistory(Sites site) throws Exception{
		//Delete Site History
		ArrayList<SitesHistory>  list=(ArrayList<SitesHistory>) findBySite(site);
		for(SitesHistory theSite:list)
		{
			theSite.setForSites(null);
			theSite.setForSiteFlatId(site.getId());
			update(theSite);
//			delete(theSite);
		}
		
	}
	
	

}
