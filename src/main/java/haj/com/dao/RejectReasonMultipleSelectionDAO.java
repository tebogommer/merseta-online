package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.RejectReasonMultipleSelectionHistory;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class RejectReasonMultipleSelectionDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all RejectReasonMultipleSelection
	 * 
	 * @author TechFinium
	 * @see RejectReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelection> allRejectReasonMultipleSelection() throws Exception {
		return (List<RejectReasonMultipleSelection>) super.getList("select o from RejectReasonMultipleSelection o");
	}

	/**
	 * Get all RejectReasonMultipleSelection between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see RejectReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelection> allRejectReasonMultipleSelection(Long from, int noRows)
			throws Exception {
		String hql = "select o from RejectReasonMultipleSelection o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<RejectReasonMultipleSelection>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see RejectReasonMultipleSelection
	 * @return a {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	public RejectReasonMultipleSelection findByKey(Long id) throws Exception {
		String hql = "select o from RejectReasonMultipleSelection o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (RejectReasonMultipleSelection) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find RejectReasonMultipleSelection by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see RejectReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelection> findByName(String description) throws Exception {
		String hql = "select o from RejectReasonMultipleSelection o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<RejectReasonMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find RejectReasonMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see RejectReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className)
			throws Exception {
		String hql = "select o from RejectReasonMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		return (List<RejectReasonMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find RejectReasonMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see RejectReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from RejectReasonMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		parameters.put("process", process);
		return (List<RejectReasonMultipleSelection>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelectionHistory> findHistoryByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from RejectReasonMultipleSelectionHistory o where o.targetClass = :className and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		parameters.put("process", process);
		return (List<RejectReasonMultipleSelectionHistory>) super.getList(hql, parameters);
	}
	

}
