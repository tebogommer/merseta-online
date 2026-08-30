package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.LearnerAchievementStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementStatusDAO.
 */
public class LearnerAchievementStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception global exception
	 * @see    LearnerAchievementStatus
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementStatus> allLearnerAchievementStatus() throws Exception {
		return (List<LearnerAchievementStatus>)super.getList("select o from LearnerAchievementStatus o");
	}

	/**
	 * Get all LearnerAchievementStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception global exception
	 * @see    LearnerAchievementStatus
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementStatus> allLearnerAchievementStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnerAchievementStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LearnerAchievementStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception global exception
	 * @see    LearnerAchievementStatus
	 */
	public LearnerAchievementStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnerAchievementStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LearnerAchievementStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnerAchievementStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception global exception
	 * @see    LearnerAchievementStatus
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementStatus> findByName(String description) throws Exception {
	 	String hql = "select o from LearnerAchievementStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnerAchievementStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by primary code.
	 *
	 * @author TechFinium
	 * @param learnerAchievementStatus the learner achievement status
	 * @return a {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception global exception
	 * @see    LearnerAchievementStatus
	 */
	public LearnerAchievementStatus findUniqueCode(LearnerAchievementStatus learnerAchievementStatus) throws Exception {
	 	String hql = "select o from LearnerAchievementStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (learnerAchievementStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", learnerAchievementStatus.getId());
	 	}
	   
	    parameters.put("code", learnerAchievementStatus.getCode());
		return (LearnerAchievementStatus)super.getUniqueResult(hql, parameters);
	}
}

