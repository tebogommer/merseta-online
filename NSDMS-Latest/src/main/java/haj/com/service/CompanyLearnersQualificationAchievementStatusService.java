package haj.com.service;

import java.util.List;

import haj.com.dao.CompanyLearnersQualificationAchievementStatusDAO;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class CompanyLearnersQualificationAchievementStatusService extends AbstractService {
	/** The dao. */
	private CompanyLearnersQualificationAchievementStatusDAO dao = new CompanyLearnersQualificationAchievementStatusDAO();

	/**
	 * Get all CompanyLearnersQualificationAchievementStatus
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersQualificationAchievementStatus
	 * @return a list of
	 *         {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersQualificationAchievementStatus> allCompanyLearnersQualificationAchievementStatus() throws Exception {
		return dao.allCompanyLearnersQualificationAchievementStatus();
	}

	/**
	 * Create or update CompanyLearnersQualificationAchievementStatus.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersQualificationAchievementStatus
	 */
	public void create(CompanyLearnersQualificationAchievementStatus entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnersQualificationAchievementStatus.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersQualificationAchievementStatus
	 */
	public void update(CompanyLearnersQualificationAchievementStatus entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersQualificationAchievementStatus.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersQualificationAchievementStatus
	 */
	public void delete(CompanyLearnersQualificationAchievementStatus entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a
	 *         {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersQualificationAchievementStatus
	 */
	public CompanyLearnersQualificationAchievementStatus findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyLearnersQualificationAchievementStatus by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of
	 *         {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersQualificationAchievementStatus
	 */
	public List<CompanyLearnersQualificationAchievementStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyLearnersQualificationAchievementStatus
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of
	 *         {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersQualificationAchievementStatus> allCompanyLearnersQualificationAchievementStatus(int first, int pageSize) throws Exception {
		return dao.allCompanyLearnersQualificationAchievementStatus(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyLearnersQualificationAchievementStatus for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersQualificationAchievementStatus
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersQualificationAchievementStatus.class);
	}

	/**
	 * Lazy load CompanyLearnersQualificationAchievementStatus with pagination,
	 * filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersQualificationAchievementStatus class
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
	 * @return a list of
	 *         {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersQualificationAchievementStatus> allCompanyLearnersQualificationAchievementStatus(Class<CompanyLearnersQualificationAchievementStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyLearnersQualificationAchievementStatus>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CompanyLearnersQualificationAchievementStatus for lazy load with
	 * filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersQualificationAchievementStatus class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersQualificationAchievementStatus
	 *         entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersQualificationAchievementStatus> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearnersQualificationAchievementStatus> allCompanyLearnersQualificationAchievementStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearnersQualificationAchievementStatus o where o.administrationOfAQPLearners.administrationOfAQP.id = :administrationOfAQPID";
		return (List<CompanyLearnersQualificationAchievementStatus>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersQualificationAchievementStatus o where o.administrationOfAQPLearners.administrationOfAQP.id = :administrationOfAQPID";
		return dao.countWhere(filters, hql);
	}
}
