package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WithdrawReasonMultipleSelection;
import haj.com.entity.WithdrawReasonMultipleSelectionHistory;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WithdrawReasonMultipleSelectionDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WithdrawReasonMultipleSelection
	 * 
	 * @author TechFinium
	 * @see WithdrawReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelection> allWithdrawReasonMultipleSelection() throws Exception {
		return (List<WithdrawReasonMultipleSelection>) super.getList("select o from WithdrawReasonMultipleSelection o");
	}

	/**
	 * Get all WithdrawReasonMultipleSelection between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WithdrawReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelection> allWithdrawReasonMultipleSelection(Long from, int noRows)
			throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelection o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<WithdrawReasonMultipleSelection>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WithdrawReasonMultipleSelection
	 * @return a {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	public WithdrawReasonMultipleSelection findByKey(Long id) throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelection o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WithdrawReasonMultipleSelection) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WithdrawReasonMultipleSelection by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WithdrawReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelection> findByName(String description) throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelection o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WithdrawReasonMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find WithdrawReasonMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WithdrawReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className)
			throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		return (List<WithdrawReasonMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find WithdrawReasonMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WithdrawReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		parameters.put("process", process);
		return (List<WithdrawReasonMultipleSelection>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelectionHistory> findHistoryByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from WithdrawReasonMultipleSelectionHistory o where o.targetClass = :className and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		parameters.put("process", process);
		return (List<WithdrawReasonMultipleSelectionHistory>) super.getList(hql, parameters);
	}
	

}
