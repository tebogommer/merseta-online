package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Aqp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AqpDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Aqp
 	 * @author TechFinium 
 	 * @see    Aqp
 	 * @return a list of {@link haj.com.entity.Aqp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Aqp> allAqp() throws Exception {
		return (List<Aqp>)super.getList("select o from Aqp o");
	}

	/**
	 * Get all Aqp between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Aqp
 	 * @return a list of {@link haj.com.entity.Aqp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Aqp> allAqp(Long from, int noRows) throws Exception {
	 	String hql = "select o from Aqp o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Aqp>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Aqp
 	 * @return a {@link haj.com.entity.Aqp}
 	 * @throws Exception global exception
 	 */
	public Aqp findByKey(Long id) throws Exception {
	 	String hql = "select o from Aqp o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Aqp)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Aqp by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Aqp
  	 * @return a list of {@link haj.com.entity.Aqp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Aqp> findByName(String description) throws Exception {
	 	String hql = "select o from Aqp o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Aqp>)super.getList(hql, parameters);
	}
}

