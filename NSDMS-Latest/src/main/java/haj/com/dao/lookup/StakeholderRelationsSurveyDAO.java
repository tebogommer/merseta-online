package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.StakeholderRelationsSurvey;

public class StakeholderRelationsSurveyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all StakeholderRelationsSurvey
 	 * @author TechFinium 
 	 * @see    StakeholderRelationsSurvey
 	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> allStakeholderRelationsSurvey() throws Exception {
		return (List<StakeholderRelationsSurvey>)super.getList("select o from StakeholderRelationsSurvey o");
	}

	/**
	 * Get all StakeholderRelationsSurvey between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    StakeholderRelationsSurvey
 	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> allStakeholderRelationsSurvey(Long from, int noRows) throws Exception {
	 	String hql = "select o from StakeholderRelationsSurvey o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<StakeholderRelationsSurvey>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    StakeholderRelationsSurvey
 	 * @return a {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	public StakeholderRelationsSurvey findByKey(Long id) throws Exception {
	 	String hql = "select o from StakeholderRelationsSurvey o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (StakeholderRelationsSurvey)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find StakeholderRelationsSurvey by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    StakeholderRelationsSurvey
  	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> findByName(String description) throws Exception {
	 	String hql = "select o from StakeholderRelationsSurvey o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<StakeholderRelationsSurvey>)super.getList(hql, parameters);
	}

	/**
	 * Locates list of StakeholderRelationsSurvey by StakeholderRelations id
 	 * @param stakeholderRelationsId 
 	 * @see StakeholderRelations
 	 * @see    StakeholderRelationsSurvey
  	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> findByStakeholderRelations(Long stakeholderRelationsId) {
		String hql = "select o from StakeholderRelationsSurvey o where o.stakeholderRelations.id =  :stakeholderRelationsId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("stakeholderRelationsId", stakeholderRelationsId);
		return (List<StakeholderRelationsSurvey>)super.getList(hql, parameters);
	}
	
	/**
	 * Locates list of StakeholderRelationsSurvey by StakeholderRelations id and user is not assigned
 	 * @param stakeholderRelationsId 
 	 * @see StakeholderRelations
 	 * @see    StakeholderRelationsSurvey
  	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> findByStakeholderRelationsUserNotAssigned(Long stakeholderRelationsId) {
		String hql = "select o from StakeholderRelationsSurvey o where o.stakeholderRelations.id = :stakeholderRelationsId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("stakeholderRelationsId", stakeholderRelationsId);
		return (List<StakeholderRelationsSurvey>)super.getList(hql, parameters);
	}
	
	/**
	 * Locates list of StakeholderRelationsSurvey by StakeholderRelations id and user id
 	 * @param stakeholderRelationsId 
 	 * @param userId
 	 * @see StakeholderRelations
 	 * @see StakeholderRelationsSurvey
 	 * @see Users
  	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> findByStakeholderRelationsAndUser(Long stakeholderRelationsId, Long userId) {
		String hql = "select o from StakeholderRelationsSurvey o where o.stakeholderRelations.id = :stakeholderRelationsId and o.user.id =:userId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("stakeholderRelationsId", stakeholderRelationsId);
	    parameters.put("userId", userId);
		return (List<StakeholderRelationsSurvey>)super.getList(hql, parameters);
	}
}