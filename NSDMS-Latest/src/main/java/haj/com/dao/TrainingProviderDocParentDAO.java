package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.TrainingProviderDocParent;

public class TrainingProviderDocParentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderDocParent
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 * @return a list of {@link haj.com.entity.TrainingProviderDocParent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderDocParent> allTrainingProviderDocParent() throws Exception {
		return (List<TrainingProviderDocParent>)super.getList("select o from TrainingProviderDocParent o");
	}

	/**
	 * Get all TrainingProviderDocParent between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingProviderDocParent
 	 * @return a list of {@link haj.com.entity.TrainingProviderDocParent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderDocParent> allTrainingProviderDocParent(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingProviderDocParent o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingProviderDocParent>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingProviderDocParent
 	 * @return a {@link haj.com.entity.TrainingProviderDocParent}
 	 * @throws Exception global exception
 	 */
	public TrainingProviderDocParent findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingProviderDocParent o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingProviderDocParent)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingProviderDocParent by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingProviderDocParent
  	 * @return a list of {@link haj.com.entity.TrainingProviderDocParent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderDocParent> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingProviderDocParent o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingProviderDocParent>)super.getList(hql, parameters);
	}
}

