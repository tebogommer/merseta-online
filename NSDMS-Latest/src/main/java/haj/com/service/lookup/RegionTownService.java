package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RegionTownDAO;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.Town;
import haj.com.framework.AbstractService;

public class RegionTownService extends AbstractService {
	/** The dao. */
	private RegionTownDAO dao = new RegionTownDAO();
	
	private static RegionTownService regionTownService;

	public static synchronized RegionTownService instance() {
		if (regionTownService == null) {
			regionTownService = new RegionTownService();
		}
		return regionTownService;
	}

	/**
	 * Get all RegionTown
	 * 
	 * @author TechFinium
	 * @see RegionTown
	 * @return a list of {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             the exception
	 */
	public List<RegionTown> allRegionTown() throws Exception {
		return dao.allRegionTown();
	}

	/**
	 * Create or update RegionTown.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RegionTown
	 */
	public void create(RegionTown entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update RegionTown.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RegionTown
	 */
	public void update(RegionTown entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete RegionTown.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RegionTown
	 */
	public void delete(RegionTown entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             the exception
	 * @see RegionTown
	 */
	public RegionTown findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find RegionTown by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             the exception
	 * @see RegionTown
	 */
	public List<RegionTown> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load RegionTown
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             the exception
	 */
	public List<RegionTown> allRegionTown(int first, int pageSize) throws Exception {
		return dao.allRegionTown(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of RegionTown for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the RegionTown
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(RegionTown.class);
	}

	/**
	 * Lazy load RegionTown with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            RegionTown class
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
	 * @return a list of {@link haj.com.entity.RegionTown} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<RegionTown> allRegionTown(Class<RegionTown> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<RegionTown>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of RegionTown for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            RegionTown class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the RegionTown entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<RegionTown> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public RegionTown findByTown(Town town) throws Exception {
		if(town!=null && town.getId()!=null) {
			return dao.findByTown(town);
		}else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param hotingCompanyEmployeeId
	 * @param crmCheck true for CRM, false for CLO
	 * @return long
	 * @throws Exception
	 */
	public long findByCrmCloCount(Long hotingCompanyEmployeeId, Boolean crmCheck) throws Exception {
		return dao.findByCrmCloCount(hotingCompanyEmployeeId, crmCheck);
	}
	
	/**
	 * Check if hosting company employee is assigned as a clo or crm
	 * @param hce
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean checkIfCrmCloUser(HostingCompanyEmployees hce) throws Exception{
		// crm check
		long result = findByCrmCloCount(hce.getId(), true);
		if (result == (long) 0) {
			// clo check
			result = findByCrmCloCount(hce.getId(), false);
			if (result == (long) 0) {
				return false;
			}else {
				return true;
			}
		} else {
			return true;
		}
	}
}
