package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.UploadTrackerAtrWsp;

public class UploadTrackerAtrWspDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UploadTrackerAtrWsp
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 * @return a list of {@link haj.com.entity.UploadTrackerAtrWsp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UploadTrackerAtrWsp> allUploadTrackerAtrWsp() throws Exception {
		return (List<UploadTrackerAtrWsp>)super.getList("select o from UploadTrackerAtrWsp o");
	}

	/**
	 * Get all UploadTrackerAtrWsp between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UploadTrackerAtrWsp
 	 * @return a list of {@link haj.com.entity.UploadTrackerAtrWsp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UploadTrackerAtrWsp> allUploadTrackerAtrWsp(Long from, int noRows) throws Exception {
	 	String hql = "select o from UploadTrackerAtrWsp o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UploadTrackerAtrWsp>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UploadTrackerAtrWsp
 	 * @return a {@link haj.com.entity.UploadTrackerAtrWsp}
 	 * @throws Exception global exception
 	 */
	public UploadTrackerAtrWsp findByKey(Long id) throws Exception {
	 	String hql = "select o from UploadTrackerAtrWsp o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UploadTrackerAtrWsp)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UploadTrackerAtrWsp by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UploadTrackerAtrWsp
  	 * @return a list of {@link haj.com.entity.UploadTrackerAtrWsp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UploadTrackerAtrWsp> findByName(String description) throws Exception {
	 	String hql = "select o from UploadTrackerAtrWsp o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UploadTrackerAtrWsp>)super.getList(hql, parameters);
	}
}

