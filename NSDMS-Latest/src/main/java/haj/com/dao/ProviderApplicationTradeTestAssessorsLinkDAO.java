package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ProviderApplicationTradeTestAssessorsLink;

public class ProviderApplicationTradeTestAssessorsLinkDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderApplicationTradeTestAssessorsLink
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 * @return a list of {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> allProviderApplicationTradeTestAssessorsLink() throws Exception {
		return (List<ProviderApplicationTradeTestAssessorsLink>)super.getList("select o from ProviderApplicationTradeTestAssessorsLink o");
	}

	/**
	 * Get all ProviderApplicationTradeTestAssessorsLink between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 * @return a list of {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> allProviderApplicationTradeTestAssessorsLink(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderApplicationTradeTestAssessorsLink o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<ProviderApplicationTradeTestAssessorsLink>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 * @return a {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
 	 * @throws Exception global exception
 	 */
	public ProviderApplicationTradeTestAssessorsLink findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderApplicationTradeTestAssessorsLink o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (ProviderApplicationTradeTestAssessorsLink)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderApplicationTradeTestAssessorsLink by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
  	 * @return a list of {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderApplicationTradeTestAssessorsLink o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderApplicationTradeTestAssessorsLink>)super.getList(hql, parameters);
	}
	
	public ProviderApplicationTradeTestAssessorsLink findByProviderApplicationAndAssessorModApp(Long trainingProviderApplicationId, Long assessorModeratorApplicationId) throws Exception {
		String hql = "select o from ProviderApplicationTradeTestAssessorsLink o where o.trainingProviderApplication.id = :trainingProviderApplicationId and o.assessorModeratorApplication.id = :assessorModeratorApplicationId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplicationId);
	    parameters.put("assessorModeratorApplicationId", assessorModeratorApplicationId);
		return (ProviderApplicationTradeTestAssessorsLink)super.getUniqueResult(hql, parameters);
	}
	
	public int countByProviderApplicationAndAssessorModApp(Long trainingProviderApplicationId, Long assessorModeratorApplicationId) throws Exception {
	 	String hql = "select count(o) from ProviderApplicationTradeTestAssessorsLink o where o.trainingProviderApplication.id = :trainingProviderApplicationId and o.assessorModeratorApplication.id = :assessorModeratorApplicationId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplicationId);
	    parameters.put("assessorModeratorApplicationId", assessorModeratorApplicationId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

