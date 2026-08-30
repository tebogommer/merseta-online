package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.PlannedImplementedEnum;
import haj.com.entity.lookup.Training;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Training
 	 * @author TechFinium 
 	 * @see    Training
 	 * @return a list of {@link haj.com.entity.Training}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Training> allTraining() throws Exception {
		return (List<Training>)super.getList("select o from Training o");
	}
	
	@SuppressWarnings("unchecked")
	public List<Training> allTraining(PlannedImplementedEnum plannedImplemented) throws Exception {
	 	String hql = "select o from Training o where o.plannedImplemented = :plannedImplemented" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("plannedImplemented", plannedImplemented);
		return (List<Training>)super.getList(hql, parameters);
	}

	/**
	 * Get all Training between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Training
 	 * @return a list of {@link haj.com.entity.Training}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Training> allTraining(Long from, int noRows) throws Exception {
	 	String hql = "select o from Training o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Training>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Training
 	 * @return a {@link haj.com.entity.Training}
 	 * @throws Exception global exception
 	 */
	public Training findByKey(Long id) throws Exception {
	 	String hql = "select o from Training o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Training)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Training by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Training
  	 * @return a list of {@link haj.com.entity.Training}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Training> findByName(String description) throws Exception {
	 	String hql = "select o from Training o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Training>)super.getList(hql, parameters);
	}
}

