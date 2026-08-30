package haj.com.dao;



import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.ChangeReason;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ChangeReasonDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ChangeReason
 	 * @author TechFinium 
 	 * @see    ChangeReason
 	 * @return a list of {@link haj.com.entity.ChangeReason}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChangeReason> allChangeReason() throws Exception {
		return (List<ChangeReason>)super.getList("select o from ChangeReason o");
	}

	/**
	 * Get all ChangeReason between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ChangeReason
 	 * @return a list of {@link haj.com.entity.ChangeReason}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChangeReason> allChangeReason(Long from, int noRows) throws Exception {
	 	String hql = "select o from ChangeReason o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ChangeReason>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ChangeReason
 	 * @return a {@link haj.com.entity.ChangeReason}
 	 * @throws Exception global exception
 	 */
	public ChangeReason findByKey(Long id) throws Exception {
	 	String hql = "select o from ChangeReason o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ChangeReason)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find ChangeReason by target Class and Target Key
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @param description the description
 	 * @see    ChangeReason
  	 * @return a list of {@link haj.com.entity.ChangeReason}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChangeReason> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception {
	 	String hql = "select o from ChangeReason o where o.targetClass = :targetClass and o.targetKey = :targetKey order by o.createDate desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return (List<ChangeReason>)super.getList(hql, parameters);
	}

	/**
	 * Find ChangeReason by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ChangeReason
  	 * @return a list of {@link haj.com.entity.ChangeReason}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChangeReason> findByName(String description) throws Exception {
	 	String hql = "select o from ChangeReason o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ChangeReason>)super.getList(hql, parameters);
	}
}

