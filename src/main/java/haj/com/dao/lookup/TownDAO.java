package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Town;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TownDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Town
 	 * @author TechFinium 
 	 * @see    Town
 	 * @return a list of {@link haj.com.entity.Town}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Town> allTown() throws Exception {
		return (List<Town>)super.getList("select o from Town o");
	}

	/**
	 * Get all Town between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Town
 	 * @return a list of {@link haj.com.entity.Town}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Town> allTown(Long from, int noRows) throws Exception {
	 	String hql = "select o from Town o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Town>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Town
 	 * @return a {@link haj.com.entity.Town}
 	 * @throws Exception global exception
 	 */
	public Town findByKey(Long id) throws Exception {
	 	String hql = "select o from Town o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Town)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Town by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Town
  	 * @return a list of {@link haj.com.entity.Town}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Town> findByName(String description) throws Exception {
	 	String hql = "select o from Town o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Town>)super.getList(hql, parameters);
	}
}

