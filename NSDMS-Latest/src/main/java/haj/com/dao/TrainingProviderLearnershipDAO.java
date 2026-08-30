package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.TrainingProviderLearnership;
import haj.com.entity.TrainingProviderSkillsSet;

public class TrainingProviderLearnershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderLearnership
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 * @return a list of {@link haj.com.entity.TrainingProviderLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> allTrainingProviderLearnership() throws Exception {
		return (List<TrainingProviderLearnership>)super.getList("select o from TrainingProviderLearnership o");
	}

	/**
	 * Get all TrainingProviderLearnership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingProviderLearnership
 	 * @return a list of {@link haj.com.entity.TrainingProviderLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> allTrainingProviderLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingProviderLearnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingProviderLearnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingProviderLearnership
 	 * @return a {@link haj.com.entity.TrainingProviderLearnership}
 	 * @throws Exception global exception
 	 */
	public TrainingProviderLearnership findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingProviderLearnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingProviderLearnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingProviderLearnership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingProviderLearnership
  	 * @return a list of {@link haj.com.entity.TrainingProviderLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingProviderLearnership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingProviderLearnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> findByTrainingProvider(Long id) throws Exception {
		String hql = "select o from TrainingProviderLearnership o where o.trainingProviderApplication.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<TrainingProviderLearnership>)super.getList(hql, parameters);
	}
}

