package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.LearnerAchievementType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementTypeDAO.
 */
public class LearnerAchievementTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnerAchievementType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LearnerAchievementType}
	 * @throws Exception global exception
	 * @see    LearnerAchievementType
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementType> allLearnerAchievementType() throws Exception {
		return (List<LearnerAchievementType>)super.getList("select o from LearnerAchievementType o");
	}

	/**
	 * Get all LearnerAchievementType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.LearnerAchievementType}
	 * @throws Exception global exception
	 * @see    LearnerAchievementType
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementType> allLearnerAchievementType(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnerAchievementType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LearnerAchievementType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerAchievementType}
	 * @throws Exception global exception
	 * @see    LearnerAchievementType
	 */
	public LearnerAchievementType findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnerAchievementType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LearnerAchievementType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnerAchievementType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.LearnerAchievementType}
	 * @throws Exception global exception
	 * @see    LearnerAchievementType
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementType> findByName(String description) throws Exception {
	 	String hql = "select o from LearnerAchievementType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnerAchievementType>)super.getList(hql, parameters);
	}
	
	/**
	 * *
	 * Find LearnerAchievementType by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param learnerAchievementType the learner achievement type
	 * @return the learner achievement type
	 * @throws Exception the exception
	 */
	public LearnerAchievementType findUniqueCode(LearnerAchievementType learnerAchievementType) throws Exception {
	 	String hql = "select o from LearnerAchievementType o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (learnerAchievementType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", learnerAchievementType.getId());
	 	}
	   
	    parameters.put("code", learnerAchievementType.getCode());
		return (LearnerAchievementType)super.getUniqueResult(hql, parameters);
	}
}

