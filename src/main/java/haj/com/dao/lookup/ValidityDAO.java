package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.Validity;

public class ValidityDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Validity
 	 * @author TechFinium 
 	 * @see    Validity
 	 * @return a list of {@link haj.com.entity.Validity}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Validity> allValidity() throws Exception {
		return (List<Validity>)super.getList("select o from Validity o");
	}

	/**
	 * Get all Validity between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Validity
 	 * @return a list of {@link haj.com.entity.Validity}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Validity> allValidity(Long from, int noRows) throws Exception {
	 	String hql = "select o from Validity o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Validity>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Validity
 	 * @return a {@link haj.com.entity.Validity}
 	 * @throws Exception global exception
 	 */
	public Validity findByKey(Long id) throws Exception {
	 	String hql = "select o from Validity o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Validity)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Validity by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Validity
  	 * @return a list of {@link haj.com.entity.Validity}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Validity> findByName(String description) throws Exception {
	 	String hql = "select o from Validity o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Validity>)super.getList(hql, parameters);
	}
}

