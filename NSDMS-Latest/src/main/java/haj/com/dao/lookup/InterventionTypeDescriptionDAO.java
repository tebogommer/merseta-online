package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.InterventionTypeDescription;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class InterventionTypeDescriptionDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all InterventionTypeDescription
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 * @return a list of {@link haj.com.entity.InterventionTypeDescription}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<InterventionTypeDescription> allInterventionTypeDescription() throws Exception {
		return (List<InterventionTypeDescription>)super.getList("select o from InterventionTypeDescription o");
	}

	/**
	 * Get all InterventionTypeDescription between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    InterventionTypeDescription
 	 * @return a list of {@link haj.com.entity.InterventionTypeDescription}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<InterventionTypeDescription> allInterventionTypeDescription(Long from, int noRows) throws Exception {
	 	String hql = "select o from InterventionTypeDescription o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<InterventionTypeDescription>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    InterventionTypeDescription
 	 * @return a {@link haj.com.entity.InterventionTypeDescription}
 	 * @throws Exception global exception
 	 */
	public InterventionTypeDescription findByKey(Long id) throws Exception {
	 	String hql = "select o from InterventionTypeDescription o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (InterventionTypeDescription)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InterventionTypeDescription by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    InterventionTypeDescription
  	 * @return a list of {@link haj.com.entity.InterventionTypeDescription}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<InterventionTypeDescription> findByName(String description) throws Exception {
	 	String hql = "select o from InterventionTypeDescription o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<InterventionTypeDescription>)super.getList(hql, parameters);
	}
}

