package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.TrainingComitteeHistoryDAO;
import haj.com.entity.Company;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingComitteeHistory;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingComitteeHistoryService.
 */
public class TrainingComitteeHistoryService extends AbstractService {
	/** The dao. */
	private TrainingComitteeHistoryDAO dao = new TrainingComitteeHistoryDAO();
	
	private static TrainingComitteeHistoryService trainingComitteeHistoryService = null;

	/**
	 * Instance.
	 *
	 * @return the SitesService
	 */
	public static synchronized TrainingComitteeHistoryService instance() {
		if (trainingComitteeHistoryService == null) {
			trainingComitteeHistoryService = new TrainingComitteeHistoryService();
		}
		return trainingComitteeHistoryService;
	}
	/**
	 * Get all TrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception
	 *             the exception
	 * @see TrainingComitteeHistory
	 */
	public List<TrainingComitteeHistory> allTrainingComitteeHistory() throws Exception {
		return dao.allTrainingComitteeHistory();
	}

	/**
	 * Create or update TrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingComitteeHistory
	 */
	public void create(TrainingComitteeHistory entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}



	/**
	 * Update TrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingComitteeHistory
	 */
	public void update(TrainingComitteeHistory entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete TrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingComitteeHistory
	 */
	public void delete(TrainingComitteeHistory entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception
	 *             the exception
	 * @see TrainingComitteeHistory
	 */
	public TrainingComitteeHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find TrainingComitteeHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception
	 *             the exception
	 * @see TrainingComitteeHistory
	 */
	public List<TrainingComitteeHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TrainingComitteeHistory.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingComitteeHistory> allTrainingComitteeHistory(int first, int pageSize) throws Exception {
		return dao.allTrainingComitteeHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TrainingComitteeHistory for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TrainingComitteeHistory
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(TrainingComitteeHistory.class);
	}

	/**
	 * Lazy load TrainingComitteeHistory with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComitteeHistory> allTrainingComitteeHistory(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingComitteeHistory o left join fetch o.company c left join fetch o.union u left join fetch o.title t left join fetch o.gender g left join fetch o.equity e where o.company.id = :companyID";
		return (List<TrainingComitteeHistory>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);

	}

	/**
	 * Get count of TrainingComitteeHistory for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            TrainingComitteeHistory class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the TrainingComitteeHistory entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<TrainingComitteeHistory> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingComitteeHistory o where o.company.id = :companyID";
		return dao.countWhere(filters, hql);
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
	public List<TrainingComitteeHistory> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}

	/**
	 * Find by company count.
	 *
	 * @param company
	 *            the company
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findByCompanyCount(Company company) throws Exception {
		return dao.findByCompanyCount(company);
	}
	
	
	public void createHistory(Long id) throws Exception {
		if (id != null)
		{
			TrainingComittee entity=TrainingComitteeService.instance().findByKey(id);
			TrainingComitteeHistory history = new TrainingComitteeHistory(entity);
			BeanUtils.copyProperties(history, entity);
			history.setId(null);
			this.dao.create(history);
		}
	}
	
	/**
	 * Find by for TrainingCommittee.
	 *
	 * @param TrainingComittee the trainingComittee
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComitteeHistory> findByForTrainingComittee(TrainingComittee trainingComittee) throws Exception {
		return dao.findByForTrainingComittee(trainingComittee);
	}
	
	public void removeTrainingCommitteeHistory(TrainingComittee trainingComittee) throws Exception{
		//Delete TC History
		ArrayList<TrainingComitteeHistory>  list=(ArrayList<TrainingComitteeHistory>) findByForTrainingComittee(trainingComittee);
		for(TrainingComitteeHistory tch:list)
		{
			tch.setForTrainingComittee(null);
			delete(tch);
		}
		
	}
}
