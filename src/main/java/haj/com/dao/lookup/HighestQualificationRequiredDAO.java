package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class HighestQualificationRequiredDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HighestQualificationRequired
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 * @return a list of {@link haj.com.entity.HighestQualificationRequired}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<HighestQualificationRequired> allHighestQualificationRequired() throws Exception {
		return (List<HighestQualificationRequired>)super.getList("select o from HighestQualificationRequired o");
	}

	/**
	 * Get all HighestQualificationRequired between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    HighestQualificationRequired
 	 * @return a list of {@link haj.com.entity.HighestQualificationRequired}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<HighestQualificationRequired> allHighestQualificationRequired(Long from, int noRows) throws Exception {
	 	String hql = "select o from HighestQualificationRequired o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<HighestQualificationRequired>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    HighestQualificationRequired
 	 * @return a {@link haj.com.entity.HighestQualificationRequired}
 	 * @throws Exception global exception
 	 */
	public HighestQualificationRequired findByKey(Long id) throws Exception {
	 	String hql = "select o from HighestQualificationRequired o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HighestQualificationRequired)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HighestQualificationRequired by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    HighestQualificationRequired
  	 * @return a list of {@link haj.com.entity.HighestQualificationRequired}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<HighestQualificationRequired> findByName(String description) throws Exception {
	 	String hql = "select o from HighestQualificationRequired o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HighestQualificationRequired>)super.getList(hql, parameters);
	}
}

