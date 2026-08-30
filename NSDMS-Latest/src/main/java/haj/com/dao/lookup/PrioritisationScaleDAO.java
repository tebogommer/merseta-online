package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.PrioritisationScale;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class PrioritisationScaleDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PrioritisationScale
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 * @return a list of {@link haj.com.entity.PrioritisationScale}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PrioritisationScale> allPrioritisationScale() throws Exception {
		return (List<PrioritisationScale>)super.getList("select o from PrioritisationScale o");
	}

	/**
	 * Get all PrioritisationScale between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    PrioritisationScale
 	 * @return a list of {@link haj.com.entity.PrioritisationScale}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PrioritisationScale> allPrioritisationScale(Long from, int noRows) throws Exception {
	 	String hql = "select o from PrioritisationScale o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PrioritisationScale>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    PrioritisationScale
 	 * @return a {@link haj.com.entity.PrioritisationScale}
 	 * @throws Exception global exception
 	 */
	public PrioritisationScale findByKey(Long id) throws Exception {
	 	String hql = "select o from PrioritisationScale o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PrioritisationScale)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PrioritisationScale by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    PrioritisationScale
  	 * @return a list of {@link haj.com.entity.PrioritisationScale}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PrioritisationScale> findByName(String description) throws Exception {
	 	String hql = "select o from PrioritisationScale o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PrioritisationScale>)super.getList(hql, parameters);
	}
}

