package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.FailedArchiveEntries;

public class FailedArchiveEntriesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FailedArchiveEntries
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 * @return a list of {@link haj.com.entity.FailedArchiveEntries}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FailedArchiveEntries> allFailedArchiveEntries() throws Exception {
		return (List<FailedArchiveEntries>)super.getList("select o from FailedArchiveEntries o");
	}

	/**
	 * Get all FailedArchiveEntries between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    FailedArchiveEntries
 	 * @return a list of {@link haj.com.entity.FailedArchiveEntries}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FailedArchiveEntries> allFailedArchiveEntries(Long from, int noRows) throws Exception {
	 	String hql = "select o from FailedArchiveEntries o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<FailedArchiveEntries>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    FailedArchiveEntries
 	 * @return a {@link haj.com.entity.FailedArchiveEntries}
 	 * @throws Exception global exception
 	 */
	public FailedArchiveEntries findByKey(Long id) throws Exception {
	 	String hql = "select o from FailedArchiveEntries o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (FailedArchiveEntries)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FailedArchiveEntries by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    FailedArchiveEntries
  	 * @return a list of {@link haj.com.entity.FailedArchiveEntries}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FailedArchiveEntries> findByName(String description) throws Exception {
	 	String hql = "select o from FailedArchiveEntries o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FailedArchiveEntries>)super.getList(hql, parameters);
	}
}

