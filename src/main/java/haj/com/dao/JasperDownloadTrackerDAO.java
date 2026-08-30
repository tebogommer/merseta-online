package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.JasperDownloadTracker;

public class JasperDownloadTrackerDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all JasperDownloadTracker
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 * @return a list of {@link haj.com.entity.JasperDownloadTracker}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<JasperDownloadTracker> allJasperDownloadTracker() throws Exception {
		return (List<JasperDownloadTracker>)super.getList("select o from JasperDownloadTracker o");
	}

	/**
	 * Get all JasperDownloadTracker between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    JasperDownloadTracker
 	 * @return a list of {@link haj.com.entity.JasperDownloadTracker}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<JasperDownloadTracker> allJasperDownloadTracker(Long from, int noRows) throws Exception {
	 	String hql = "select o from JasperDownloadTracker o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<JasperDownloadTracker>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    JasperDownloadTracker
 	 * @return a {@link haj.com.entity.JasperDownloadTracker}
 	 * @throws Exception global exception
 	 */
	public JasperDownloadTracker findByKey(Long id) throws Exception {
	 	String hql = "select o from JasperDownloadTracker o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (JasperDownloadTracker)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find JasperDownloadTracker by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    JasperDownloadTracker
  	 * @return a list of {@link haj.com.entity.JasperDownloadTracker}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<JasperDownloadTracker> findByName(String description) throws Exception {
	 	String hql = "select o from JasperDownloadTracker o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<JasperDownloadTracker>)super.getList(hql, parameters);
	}
}

