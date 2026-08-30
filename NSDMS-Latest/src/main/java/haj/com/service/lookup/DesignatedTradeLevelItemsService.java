package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.DesignatedTradeLevelItemsDAO;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeLevelItems;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DesignatedTradeLevelItemsService extends AbstractService {

	/** The dao. */
	private DesignatedTradeLevelItemsDAO dao = new DesignatedTradeLevelItemsDAO();

	private static DesignatedTradeLevelItemsService designatedTradeLevelItemsService = null;
	public static synchronized DesignatedTradeLevelItemsService instance() {
		if (designatedTradeLevelItemsService == null) {
			designatedTradeLevelItemsService = new DesignatedTradeLevelItemsService();
		}
		return designatedTradeLevelItemsService;
	}

	/**
	 * Get all DesignatedTradeLevelItems
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevelItems
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
	 * @throws Exception
	 *             the exception
	 */
	public List<DesignatedTradeLevelItems> allDesignatedTradeLevelItems() throws Exception {
		return dao.allDesignatedTradeLevelItems();
	}

	/**
	 * Create or update DesignatedTradeLevelItems.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevelItems
	 */
	public void create(DesignatedTradeLevelItems entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update DesignatedTradeLevelItems.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevelItems
	 */
	public void update(DesignatedTradeLevelItems entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DesignatedTradeLevelItems.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevelItems
	 */
	public void delete(DesignatedTradeLevelItems entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.DesignatedTradeLevelItems}
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevelItems
	 */
	public DesignatedTradeLevelItems findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find DesignatedTradeLevelItems by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevelItems
	 */
	public List<DesignatedTradeLevelItems> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load DesignatedTradeLevelItems
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
	 * @throws Exception
	 *             the exception
	 */
	public List<DesignatedTradeLevelItems> allDesignatedTradeLevelItems(int first, int pageSize) throws Exception {
		return dao.allDesignatedTradeLevelItems(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DesignatedTradeLevelItems for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the DesignatedTradeLevelItems
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(DesignatedTradeLevelItems.class);
	}

	/**
	 * Lazy load DesignatedTradeLevelItems with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            DesignatedTradeLevelItems class
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
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevelItems> allDesignatedTradeLevelItems(Class<DesignatedTradeLevelItems> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DesignatedTradeLevelItems>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of DesignatedTradeLevelItems for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            DesignatedTradeLevelItems class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the DesignatedTradeLevelItems entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<DesignatedTradeLevelItems> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevelItems> allDesignatedTradeLevelItemsByDesignatedTradeLevel(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, DesignatedTradeLevel designatedTradeLevel) throws Exception {
		String hql = "select o from DesignatedTradeLevelItems o where o.designatedTradeLevel.id = :designatedTradeLevelId";
		filters.put("designatedTradeLevelId", designatedTradeLevel.getId());
		return (List<DesignatedTradeLevelItems>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllDesignatedTradeLevelItemsByDesignatedTradeLevel(Class<DesignatedTradeLevelItems> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevelItems o where o.designatedTradeLevel.id = :designatedTradeLevelId";
		return dao.countWhere(filters, hql);
	}
	
	public List<DesignatedTradeLevelItems> findByDesignatedTradeLevel(Long designatedTradeLevelId) throws Exception {
		return dao.findByDesignatedTradeLevel(designatedTradeLevelId);
	}
}
