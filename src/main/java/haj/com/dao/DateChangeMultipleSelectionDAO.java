package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DateChangeMultipleSelectionDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DateChangeMultipleSelection
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeMultipleSelection> allDateChangeMultipleSelection() throws Exception {
		return (List<DateChangeMultipleSelection>) super.getList("select o from DateChangeMultipleSelection o");
	}

	/**
	 * Get all DateChangeMultipleSelection between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see DateChangeMultipleSelection
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeMultipleSelection> allDateChangeMultipleSelection(Long from, int noRows) throws Exception {
		String hql = "select o from DateChangeMultipleSelection o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<DateChangeMultipleSelection>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see DateChangeMultipleSelection
	 * @return a {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	public DateChangeMultipleSelection findByKey(Long id) throws Exception {
		String hql = "select o from DateChangeMultipleSelection o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DateChangeMultipleSelection) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DateChangeMultipleSelection by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DateChangeMultipleSelection
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeMultipleSelection> findByName(String description) throws Exception {
		String hql = "select o from DateChangeMultipleSelection o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<DateChangeMultipleSelection>) super.getList(hql, parameters);
	}
	
	/**
	 * Find DateChangeMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DateChangeMultipleSelection
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className)
			throws Exception {
		String hql = "select o from DateChangeMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		return (List<DateChangeMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find DateChangeMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DateChangeMultipleSelection
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DateChangeMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className,
			ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from DateChangeMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		parameters.put("process", process);
		return (List<DateChangeMultipleSelection>) super.getList(hql, parameters);
	}
}
