package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class NonSetaQualificationsCompletionDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NonSetaQualificationsCompletion
 	 * @author TechFinium 
 	 * @see    NonSetaQualificationsCompletion
 	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaQualificationsCompletion> allNonSetaQualificationsCompletion() throws Exception {
		return (List<NonSetaQualificationsCompletion>)super.getList("select o from NonSetaQualificationsCompletion o");
	}

	/**
	 * Get all NonSetaQualificationsCompletion between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NonSetaQualificationsCompletion
 	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaQualificationsCompletion> allNonSetaQualificationsCompletion(Long from, int noRows) throws Exception {
	 	String hql = "select o from NonSetaQualificationsCompletion o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NonSetaQualificationsCompletion>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NonSetaQualificationsCompletion
 	 * @return a {@link haj.com.entity.NonSetaQualificationsCompletion}
 	 * @throws Exception global exception
 	 */
	public NonSetaQualificationsCompletion findByKey(Long id) throws Exception {
	 	String hql = "select o from NonSetaQualificationsCompletion o left join fetch o.company left join fetch o.trainingProvider where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NonSetaQualificationsCompletion)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NonSetaQualificationsCompletion by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NonSetaQualificationsCompletion
  	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaQualificationsCompletion> findByName(String description) throws Exception {
	 	String hql = "select o from NonSetaQualificationsCompletion o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NonSetaQualificationsCompletion>)super.getList(hql, parameters);
	}
	
	public Company findUserByKey(Long id) throws Exception {
	 	String hql = "select c from NonSetaQualificationsCompletion o inner join Company c on c.id = o.trainingProvider.id where o.trainingProvider.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Company)super.getUniqueResult(hql, parameters);
	}
	
	public NonSetaQualificationsCompletion findByKeyCompanyLearners(Long id) throws Exception {
	 	String hql = "select o from NonSetaQualificationsCompletion o where o.companyLearners.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NonSetaQualificationsCompletion)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NonSetaQualificationsCompletion> findByKeyCompanyLearner(Long id) throws Exception {
	 	String hql = "select o from NonSetaQualificationsCompletion o where o.companyLearners.id = :id  " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<NonSetaQualificationsCompletion>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NonSetaQualificationsCompletion> findByCompanyLearner(Long companyLearnersID) {
		String hql = "select o from NonSetaQualificationsCompletion o where o.companyLearners.id = :companyLearnersID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyLearnersID", companyLearnersID);
		return (List<NonSetaQualificationsCompletion>)super.getList(hql, parameters);
	}	
	
}

