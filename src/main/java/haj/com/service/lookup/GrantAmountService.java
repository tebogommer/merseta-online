package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.GrantAmountDAO;
import haj.com.entity.enums.GrantTypeEnum;
import haj.com.entity.lookup.GrantAmount;
import haj.com.framework.AbstractService;

public class GrantAmountService extends AbstractService {
	/** The dao. */
	private GrantAmountDAO dao = new GrantAmountDAO();

	/**
	 * Get all GrantAmount
	 * 
	 * @author TechFinium
	 * @see GrantAmount
	 * @return a list of {@link haj.com.entity.GrantAmount}
	 * @throws Exception
	 *             the exception
	 */
	public List<GrantAmount> allGrantAmount() throws Exception {
		return dao.allGrantAmount();
	}

	public List<GrantAmount> allGrantAmount(GrantTypeEnum grantType) throws Exception {
		return dao.allGrantAmount(grantType);
	}

	/**
	 * Create or update GrantAmount.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see GrantAmount
	 */
	public void create(GrantAmount entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update GrantAmount.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see GrantAmount
	 */
	public void update(GrantAmount entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete GrantAmount.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see GrantAmount
	 */
	public void delete(GrantAmount entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.GrantAmount}
	 * @throws Exception
	 *             the exception
	 * @see GrantAmount
	 */
	public GrantAmount findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find GrantAmount by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.GrantAmount}
	 * @throws Exception
	 *             the exception
	 * @see GrantAmount
	 */
	public List<GrantAmount> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load GrantAmount
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.GrantAmount}
	 * @throws Exception
	 *             the exception
	 */
	public List<GrantAmount> allGrantAmount(int first, int pageSize) throws Exception {
		return dao.allGrantAmount(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of GrantAmount for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the GrantAmount
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(GrantAmount.class);
	}

	/**
	 * Lazy load GrantAmount with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            GrantAmount class
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
	 * @return a list of {@link haj.com.entity.GrantAmount} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<GrantAmount> allGrantAmount(Class<GrantAmount> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<GrantAmount>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of GrantAmount for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            GrantAmount class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the GrantAmount entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<GrantAmount> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
}
