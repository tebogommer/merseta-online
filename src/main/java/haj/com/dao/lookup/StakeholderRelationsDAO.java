package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.RelationTypeEnum;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class StakeholderRelationsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all StakeholderRelations
 	 * @author TechFinium 
 	 * @see    StakeholderRelations
 	 * @return a list of {@link haj.com.entity.StakeholderRelations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allStakeholderRelations() throws Exception {
		return (List<StakeholderRelations>)super.getList("select o from StakeholderRelations o");
	}

	/**
	 * Get all StakeholderRelations between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    StakeholderRelations
 	 * @return a list of {@link haj.com.entity.StakeholderRelations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allStakeholderRelations(Long from, int noRows) throws Exception {
	 	String hql = "select o from StakeholderRelations o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<StakeholderRelations>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    StakeholderRelations
 	 * @return a {@link haj.com.entity.StakeholderRelations}
 	 * @throws Exception global exception
 	 */
	public StakeholderRelations findByKey(Long id) throws Exception {
	 	String hql = "select o from StakeholderRelations o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (StakeholderRelations)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find StakeholderRelations by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    StakeholderRelations
  	 * @return a list of {@link haj.com.entity.StakeholderRelations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> findByName(String description) throws Exception {
	 	String hql = "select o from StakeholderRelations o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<StakeholderRelations>)super.getList(hql, parameters);
	}
	
	/**
	 * Find StakeholderRelations by RelationTypeEnum and where user is not assigned
 	 * @author TechFinium 
 	 * @param enumPassed
 	 * @see    StakeholderRelations
  	 * @return a list of {@link haj.com.entity.StakeholderRelations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> findByRelationTypeEnum(RelationTypeEnum enumPassed) throws Exception {
	 	String hql = "select o from StakeholderRelations o where o.relationTypeEnum = :enumPassed and o.user is null order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("enumPassed", enumPassed);
		return (List<StakeholderRelations>)super.getList(hql, parameters);
	}
	
	public Long countTotalSurveysTakenByUsers() throws Exception {
	 	String hql = "select count(o) from StakeholderRelations o where o.relationTypeEnum = :surveyEnum and o.user is not null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("surveyEnum", RelationTypeEnum.Survey);
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
	public Long countTotalSurveysAvalible() throws Exception {
	 	String hql = "select count(o) from StakeholderRelations o where o.relationTypeEnum = :surveyEnum and o.user is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("surveyEnum", RelationTypeEnum.Survey);
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
}

