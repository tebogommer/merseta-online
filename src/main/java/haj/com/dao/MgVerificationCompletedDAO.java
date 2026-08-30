package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.MgVerificationCompleted;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MgVerificationCompletedDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MgVerificationCompleted
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 * @return a list of {@link haj.com.entity.MgVerificationCompleted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationCompleted> allMgVerificationCompleted() throws Exception {
		return (List<MgVerificationCompleted>)super.getList("select o from MgVerificationCompleted o");
	}

	/**
	 * Get all MgVerificationCompleted between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MgVerificationCompleted
 	 * @return a list of {@link haj.com.entity.MgVerificationCompleted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationCompleted> allMgVerificationCompleted(Long from, int noRows) throws Exception {
	 	String hql = "select o from MgVerificationCompleted o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MgVerificationCompleted>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MgVerificationCompleted
 	 * @return a {@link haj.com.entity.MgVerificationCompleted}
 	 * @throws Exception global exception
 	 */
	public MgVerificationCompleted findByKey(Long id) throws Exception {
	 	String hql = "select o from MgVerificationCompleted o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MgVerificationCompleted)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MgVerificationCompleted by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MgVerificationCompleted
  	 * @return a list of {@link haj.com.entity.MgVerificationCompleted}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationCompleted> findByName(String description) throws Exception {
	 	String hql = "select o from MgVerificationCompleted o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MgVerificationCompleted>)super.getList(hql, parameters);
	}
}

