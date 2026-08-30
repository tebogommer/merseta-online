package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SetmisFile502Extracted;

public class SetmisFile502ExtractedDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SetmisFile502Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile502Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile502Extracted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile502Extracted> allSetmisFile502Extracted() throws Exception {
		return (List<SetmisFile502Extracted>)super.getList("select o from SetmisFile502Extracted o");
	}

	/**
	 * Get all SetmisFile502Extracted between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SetmisFile502Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile502Extracted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile502Extracted> allSetmisFile502Extracted(Long from, int noRows) throws Exception {
	 	String hql = "select o from SetmisFile502Extracted o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SetmisFile502Extracted>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SetmisFile502Extracted
 	 * @return a {@link haj.com.entity.SetmisFile502Extracted}
 	 * @throws Exception global exception
 	 */
	public SetmisFile502Extracted findByKey(Long id) throws Exception {
	 	String hql = "select o from SetmisFile502Extracted o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SetmisFile502Extracted)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SetmisFile502Extracted by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SetmisFile502Extracted
  	 * @return a list of {@link haj.com.entity.SetmisFile502Extracted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile502Extracted> findByName(String description) throws Exception {
	 	String hql = "select o from SetmisFile502Extracted o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SetmisFile502Extracted>)super.getList(hql, parameters);
	}
}

