package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingDeliveryMethodDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingDeliveryMethod
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 * @return a list of {@link haj.com.entity.TrainingDeliveryMethod}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingDeliveryMethod> allTrainingDeliveryMethod() throws Exception {
		return (List<TrainingDeliveryMethod>)super.getList("select o from TrainingDeliveryMethod o");
	}

	/**
	 * Get all TrainingDeliveryMethod between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingDeliveryMethod
 	 * @return a list of {@link haj.com.entity.TrainingDeliveryMethod}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingDeliveryMethod> allTrainingDeliveryMethod(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingDeliveryMethod o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingDeliveryMethod>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingDeliveryMethod
 	 * @return a {@link haj.com.entity.TrainingDeliveryMethod}
 	 * @throws Exception global exception
 	 */
	public TrainingDeliveryMethod findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingDeliveryMethod o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingDeliveryMethod)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingDeliveryMethod by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingDeliveryMethod
  	 * @return a list of {@link haj.com.entity.TrainingDeliveryMethod}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingDeliveryMethod> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingDeliveryMethod o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingDeliveryMethod>)super.getList(hql, parameters);
	}
}

