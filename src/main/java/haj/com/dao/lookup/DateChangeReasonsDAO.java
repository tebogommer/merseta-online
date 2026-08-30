package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.RejectReasons;

public class DateChangeReasonsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DateChangeReasons
 	 * @author TechFinium 
 	 * @see    DateChangeReasons
 	 * @return a list of {@link haj.com.entity.DateChangeReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> allDateChangeReasons() throws Exception {
		return (List<DateChangeReasons>)super.getList("select o from DateChangeReasons o");
	}

	/**
	 * Get all DateChangeReasons between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DateChangeReasons
 	 * @return a list of {@link haj.com.entity.DateChangeReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> allDateChangeReasons(Long from, int noRows) throws Exception {
	 	String hql = "select o from DateChangeReasons o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();  
		return (List<DateChangeReasons>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DateChangeReasons
 	 * @return a {@link haj.com.entity.DateChangeReasons}
 	 * @throws Exception global exception
 	 */
	public DateChangeReasons findByKey(Long id) throws Exception {
	 	String hql = "select o from DateChangeReasons o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DateChangeReasons)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DateChangeReasons by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DateChangeReasons
  	 * @return a list of {@link haj.com.entity.DateChangeReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> findByName(String description) throws Exception {
	 	String hql = "select o from DateChangeReasons o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DateChangeReasons>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by process.
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> findByProcess(ConfigDocProcessEnum process) {
		String hql = "select o from DateChangeReasons o where o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("process", process);
		return (List<DateChangeReasons>) super.getList(hql, parameters);
	}

	/**
	 * Find by process and boolean value
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> findByProcessAndBooleanSelection(ConfigDocProcessEnum process, Boolean booleanValue)
			throws Exception {
		String hql = "select o from DateChangeReasons o where o.forProcess = :process and o.forCrm = :booleanValue";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		return (List<DateChangeReasons>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> findLinkedByMultipleSelection(Long targetKey, String targetClass) throws Exception {
		String hql = "select o from DateChangeReasons o where o.id in "
				+ "(select x.rejectReason from DateChangeMultipleSelection x where x.targetClass = :targetClass and x.targetKey = :targetKey) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		return (List<DateChangeReasons>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> locateReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from DateChangeReasons o where o.id in "
				+ "(select x.dateChangeReason from DateChangeMultipleSelection x where "
				+ "x.targetClass = :targetClass and " + "x.targetKey = :targetKey and " + "x.forProcess = :process) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		return (List<DateChangeReasons>) super.getList(hql, parameters);
	}

	public long countReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass, ConfigDocProcessEnum process) throws Exception {
		String hql = "select count(o) from DateChangeReasons o where o.id in " + "(select x.rejectReason from DateChangeMultipleSelection x where "
				+ "x.targetClass = :targetClass and x.targetKey = :targetKey and x.forProcess = :process) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		return (long) super.getUniqueResult(hql, parameters);
	}
}

