package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.TempSignoff;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TempSignoffDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TempSignoff
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 * @return a list of {@link haj.com.entity.TempSignoff}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TempSignoff> allTempSignoff() throws Exception {
		return (List<TempSignoff>)super.getList("select o from TempSignoff o");
	}

	/**
	 * Get all TempSignoff between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TempSignoff
 	 * @return a list of {@link haj.com.entity.TempSignoff}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TempSignoff> allTempSignoff(Long from, int noRows) throws Exception {
	 	String hql = "select o from TempSignoff o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TempSignoff>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TempSignoff
 	 * @return a {@link haj.com.entity.TempSignoff}
 	 * @throws Exception global exception
 	 */
	public TempSignoff findByKey(Long id) throws Exception {
	 	String hql = "select o from TempSignoff o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TempSignoff)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TempSignoff by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TempSignoff
  	 * @return a list of {@link haj.com.entity.TempSignoff}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TempSignoff> findByName(String description) throws Exception {
	 	String hql = "select o from TempSignoff o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TempSignoff>)super.getList(hql, parameters);
	}
}

