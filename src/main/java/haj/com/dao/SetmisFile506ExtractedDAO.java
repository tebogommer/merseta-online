package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SetmisFile506Extracted;

public class SetmisFile506ExtractedDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SetmisFile506Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile506Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile506Extracted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile506Extracted> allSetmisFile506Extracted() throws Exception {
		return (List<SetmisFile506Extracted>)super.getList("select o from SetmisFile506Extracted o");
	}

	/**
	 * Get all SetmisFile506Extracted between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SetmisFile506Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile506Extracted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile506Extracted> allSetmisFile506Extracted(Long from, int noRows) throws Exception {
	 	String hql = "select o from SetmisFile506Extracted o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SetmisFile506Extracted>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SetmisFile506Extracted
 	 * @return a {@link haj.com.entity.SetmisFile506Extracted}
 	 * @throws Exception global exception
 	 */
	public SetmisFile506Extracted findByKey(Long id) throws Exception {
	 	String hql = "select o from SetmisFile506Extracted o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SetmisFile506Extracted)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SetmisFile506Extracted by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SetmisFile506Extracted
  	 * @return a list of {@link haj.com.entity.SetmisFile506Extracted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile506Extracted> findByName(String description) throws Exception {
	 	String hql = "select o from SetmisFile506Extracted o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SetmisFile506Extracted>)super.getList(hql, parameters);
	}
}

