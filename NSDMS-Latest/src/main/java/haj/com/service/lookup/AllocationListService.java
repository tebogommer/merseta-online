package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.AllocationListDAO;
import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.AllocationList;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AllocationListService extends AbstractService {
	/** The dao. */
	private AllocationListDAO dao = new AllocationListDAO();

	/**
	 * Get all AllocationList
	 * 
	 * @author TechFinium
	 * @see AllocationList
	 * @return a list of {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             the exception
	 */
	public List<AllocationList> allAllocationList() throws Exception {
		return dao.allAllocationList();
	}

	/**
	 * Create or update AllocationList.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AllocationList
	 */
	public void create(AllocationList entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update AllocationList.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AllocationList
	 */
	public void update(AllocationList entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete AllocationList.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AllocationList
	 */
	public void delete(AllocationList entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             the exception
	 * @see AllocationList
	 */
	public AllocationList findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find AllocationList by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             the exception
	 * @see AllocationList
	 */
	public List<AllocationList> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load AllocationList
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             the exception
	 */
	public List<AllocationList> allAllocationList(int first, int pageSize) throws Exception {
		return dao.allAllocationList(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of AllocationList for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the AllocationList
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(AllocationList.class);
	}

	/**
	 * Lazy load AllocationList with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            AllocationList class
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
	 * @return a list of {@link haj.com.entity.AllocationList} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationList> allAllocationList(Class<AllocationList> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<AllocationList>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of AllocationList for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            AllocationList class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the AllocationList entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<AllocationList> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AllocationList> allAllocationListByFinYear( int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from AllocationList o where o.finYear = :finYear";
		filters.put("finYear", finYear);
		return (List<AllocationList>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllAllocationListByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AllocationList o where o.finYear = :finYear";
		return dao.countWhere(filters, hql);
	}

	public Long findTotals(OfoCodes ofoCodes) throws Exception {
		return dao.findTotals(ofoCodes);
	}
	
	public Long findTotalsByFinYear(OfoCodes ofoCodes, Integer finYear) throws Exception {
		return dao.findTotalsByFinYear(ofoCodes, finYear);
	}
}
