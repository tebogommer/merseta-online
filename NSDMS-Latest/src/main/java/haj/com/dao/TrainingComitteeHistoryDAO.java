package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingComitteeHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingComitteeHistoryDAO.
 */
public class TrainingComitteeHistoryDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " 
			+ "left join fetch o.union u " 
			+ "left join fetch o.title t " 
			+ "left join fetch o.equity e " 
			+ "left join fetch o.gender g " 
			+ "left join fetch o.company comp "
			+ " ";
	
	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception             global exception
	 * @see TrainingComitteeHistory
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComitteeHistory> allTrainingComitteeHistory() throws Exception {
		return (List<TrainingComitteeHistory>) super.getList("select o from TrainingComitteeHistory o");
	}

	/**
	 * Get all TrainingComitteeHistory between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception             global exception
	 * @see TrainingComitteeHistory
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComitteeHistory> allTrainingComitteeHistory(Long from, int noRows) throws Exception {
		String hql = "select o from TrainingComitteeHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<TrainingComitteeHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception             global exception
	 * @see TrainingComitteeHistory
	 */
	public TrainingComitteeHistory findByKey(Long id) throws Exception {
		String hql = "select o from TrainingComitteeHistory o" +leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (TrainingComitteeHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingComitteeHistory by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.TrainingComitteeHistory}
	 * @throws Exception             global exception
	 * @see TrainingComitteeHistory
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComitteeHistory> findByName(String description) throws Exception {
		String hql = "select o from TrainingComitteeHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<TrainingComitteeHistory>) super.getList(hql, parameters);
	}

	
	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComitteeHistory> findByCompany(Company company) throws Exception {
		String hql = "select o from TrainingComitteeHistory o "+leftJoins+" where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<TrainingComitteeHistory>) super.getList(hql, parameters);
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
		String hql = "select o from TrainingComitteeHistory o "+leftJoins+" where o.forTrainingComittee.id = :forTrainingComittee order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forTrainingComittee", trainingComittee.getId());
		return (List<TrainingComitteeHistory>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by company count.
	 *
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findByCompanyCount(Company company) throws Exception {
		String hql = "select count(o) from TrainingComitteeHistory o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}

}
