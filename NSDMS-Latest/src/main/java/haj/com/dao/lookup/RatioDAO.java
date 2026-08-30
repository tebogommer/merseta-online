package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Ratio;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class RatioDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Ratio
 	 * @author TechFinium 
 	 * @see    Ratio
 	 * @return a list of {@link haj.com.entity.Ratio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Ratio> allRatio() throws Exception {
		return (List<Ratio>)super.getList("select o from Ratio o");
	}

	/**
	 * Get all Ratio between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Ratio
 	 * @return a list of {@link haj.com.entity.Ratio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Ratio> allRatio(Long from, int noRows) throws Exception {
	 	String hql = "select o from Ratio o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Ratio>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Ratio
 	 * @return a {@link haj.com.entity.Ratio}
 	 * @throws Exception global exception
 	 */
	public Ratio findByKey(Long id) throws Exception {
	 	String hql = "select o from Ratio o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Ratio)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Ratio by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Ratio
  	 * @return a list of {@link haj.com.entity.Ratio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Ratio> findByName(String description) throws Exception {
	 	String hql = "select o from Ratio o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Ratio>)super.getList(hql, parameters);
	}
}

