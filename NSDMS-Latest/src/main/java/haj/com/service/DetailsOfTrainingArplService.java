package haj.com.service;

import java.util.Hashtable;
import java.util.List;

import haj.com.dao.DetailsOfTrainingArplDAO;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DetailsOfTrainingArplService extends AbstractService {
	/** The dao. */
	private DetailsOfTrainingArplDAO dao = new DetailsOfTrainingArplDAO();

	/**
	 * Get all DetailsOfTrainingArpl
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl}
	 * @throws Exception
	 *             the exception
	 */
	public List<DetailsOfTrainingArpl> allDetailsOfTrainingArpl() throws Exception {
		return dao.allDetailsOfTrainingArpl();
	}

	/**
	 * Create or update DetailsOfTrainingArpl.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DetailsOfTrainingArpl
	 */
	public void create(DetailsOfTrainingArpl entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update DetailsOfTrainingArpl.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DetailsOfTrainingArpl
	 */
	public void update(DetailsOfTrainingArpl entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DetailsOfTrainingArpl.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DetailsOfTrainingArpl
	 */
	public void delete(DetailsOfTrainingArpl entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.DetailsOfTrainingArpl}
	 * @throws Exception
	 *             the exception
	 * @see DetailsOfTrainingArpl
	 */
	public DetailsOfTrainingArpl findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find DetailsOfTrainingArpl by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl}
	 * @throws Exception
	 *             the exception
	 * @see DetailsOfTrainingArpl
	 */
	public List<DetailsOfTrainingArpl> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load DetailsOfTrainingArpl
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl}
	 * @throws Exception
	 *             the exception
	 */
	public List<DetailsOfTrainingArpl> allDetailsOfTrainingArpl(int first, int pageSize) throws Exception {
		return dao.allDetailsOfTrainingArpl(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DetailsOfTrainingArpl for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the DetailsOfTrainingArpl
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(DetailsOfTrainingArpl.class);
	}

	/**
	 * Lazy load DetailsOfTrainingArpl with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            DetailsOfTrainingArpl class
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
	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfTrainingArpl> allDetailsOfTrainingArpl(Class<DetailsOfTrainingArpl> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DetailsOfTrainingArpl>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of DetailsOfTrainingArpl for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            DetailsOfTrainingArpl class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the DetailsOfTrainingArpl entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<DetailsOfTrainingArpl> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DetailsOfTrainingArpl> allDetailsOfTrainingArplByTradeTestId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		String hql = "select o from DetailsOfTrainingArpl o  left join fetch o.companyLearnersTradeTest cltt where o.companyLearnersTradeTest.id = :tradeTestId ";
		filters.put("tradeTestId", companyLearnersTradeTest.getId());
		return (List<DetailsOfTrainingArpl>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllDetailsOfTrainingArplByTradeTestId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DetailsOfTrainingArpl o where o.companyLearnersTradeTest.id = :tradeTestId ";
		return dao.countWhere(filters, hql);
	}
	
	public int countByTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		return dao.countByTradeTest(companyLearnersTradeTest.getId());
	}
	
	public List<DetailsOfTrainingArpl> findByCompanyLearnersTradeTest(Long  tradeTestID) throws Exception {
	 	return dao.findByCompanyLearnersTradeTest(tradeTestID);
	}
}