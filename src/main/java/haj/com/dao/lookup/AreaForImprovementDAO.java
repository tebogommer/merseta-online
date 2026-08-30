package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.AreaForImprovement;

public class AreaForImprovementDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AreaForImprovement
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 * @return a list of {@link haj.com.entity.AreaForImprovement}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AreaForImprovement> allAreaForImprovement() throws Exception {
		return (List<AreaForImprovement>)super.getList("select o from AreaForImprovement o");
	}

	/**
	 * Get all AreaForImprovement between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AreaForImprovement
 	 * @return a list of {@link haj.com.entity.AreaForImprovement}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AreaForImprovement> allAreaForImprovement(Long from, int noRows) throws Exception {
	 	String hql = "select o from AreaForImprovement o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AreaForImprovement>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AreaForImprovement
 	 * @return a {@link haj.com.entity.AreaForImprovement}
 	 * @throws Exception global exception
 	 */
	public AreaForImprovement findByKey(Long id) throws Exception {
	 	String hql = "select o from AreaForImprovement o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AreaForImprovement)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AreaForImprovement by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AreaForImprovement
  	 * @return a list of {@link haj.com.entity.AreaForImprovement}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AreaForImprovement> findByName(String description) throws Exception {
	 	String hql = "select o from AreaForImprovement o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AreaForImprovement>)super.getList(hql, parameters);
	}
}

