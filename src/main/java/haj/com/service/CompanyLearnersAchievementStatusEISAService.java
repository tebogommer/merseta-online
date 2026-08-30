package haj.com.service;

import java.util.List;

import haj.com.dao.CompanyLearnersAchievementStatusEISADAO;
import haj.com.entity.CompanyLearnersAchievementStatusEISA;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class CompanyLearnersAchievementStatusEISAService extends AbstractService {
	/** The dao. */
	private CompanyLearnersAchievementStatusEISADAO dao = new CompanyLearnersAchievementStatusEISADAO();

	/**
	 * Get all CompanyLearnersAchievementStatusEISA
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersAchievementStatusEISA
	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersAchievementStatusEISA> allCompanyLearnersAchievementStatusEISA() throws Exception {
		return dao.allCompanyLearnersAchievementStatusEISA();
	}

	/**
	 * Create or update CompanyLearnersAchievementStatusEISA.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersAchievementStatusEISA
	 */
	public void create(CompanyLearnersAchievementStatusEISA entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnersAchievementStatusEISA.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersAchievementStatusEISA
	 */
	public void update(CompanyLearnersAchievementStatusEISA entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersAchievementStatusEISA.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersAchievementStatusEISA
	 */
	public void delete(CompanyLearnersAchievementStatusEISA entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersAchievementStatusEISA
	 */
	public CompanyLearnersAchievementStatusEISA findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyLearnersAchievementStatusEISA by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersAchievementStatusEISA
	 */
	public List<CompanyLearnersAchievementStatusEISA> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyLearnersAchievementStatusEISA
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersAchievementStatusEISA> allCompanyLearnersAchievementStatusEISA(int first, int pageSize) throws Exception {
		return dao.allCompanyLearnersAchievementStatusEISA(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyLearnersAchievementStatusEISA for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersAchievementStatusEISA
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersAchievementStatusEISA.class);
	}

	/**
	 * Lazy load CompanyLearnersAchievementStatusEISA with pagination, filter,
	 * sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersAchievementStatusEISA class
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
	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersAchievementStatusEISA> allCompanyLearnersAchievementStatusEISA(Class<CompanyLearnersAchievementStatusEISA> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyLearnersAchievementStatusEISA>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CompanyLearnersAchievementStatusEISA for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersAchievementStatusEISA class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersAchievementStatusEISA entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersAchievementStatusEISA> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearnersAchievementStatusEISA> allCompanyLearnersAchievementStatusEISA(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearnersAchievementStatusEISA o where o.administrationOfAQPLearners.administrationOfAQP.id = :administrationOfAQPID";
		return (List<CompanyLearnersAchievementStatusEISA>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}

	/**
	 * Get count of CompanyLearnersAchievementStatusEISA for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersAchievementStatusEISA class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersAchievementStatusEISA entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersAchievementStatusEISA o where o.administrationOfAQPLearners.administrationOfAQP.id = :administrationOfAQPID";
		return dao.countWhere(filters, hql);
	}
}
