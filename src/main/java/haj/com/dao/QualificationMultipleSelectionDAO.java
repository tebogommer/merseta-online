package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.QualificationMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QualificationMultipleSelectionDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationMultipleSelection
	 * 
	 * @author TechFinium
	 * @see QualificationMultipleSelection
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationMultipleSelection> allQualificationMultipleSelection() throws Exception {
		return (List<QualificationMultipleSelection>) super.getList("select o from QualificationMultipleSelection o");
	}

	/**
	 * Get all QualificationMultipleSelection between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see QualificationMultipleSelection
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationMultipleSelection> allQualificationMultipleSelection(Long from, int noRows)
			throws Exception {
		String hql = "select o from QualificationMultipleSelection o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<QualificationMultipleSelection>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see QualificationMultipleSelection
	 * @return a {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	public QualificationMultipleSelection findByKey(Long id) throws Exception {
		String hql = "select o from QualificationMultipleSelection o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (QualificationMultipleSelection) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationMultipleSelection by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see QualificationMultipleSelection
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationMultipleSelection> findByName(String description) throws Exception {
		String hql = "select o from QualificationMultipleSelection o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<QualificationMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find DateChangeMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param targetKey
	 * @param className
	 * @see QualificationMultipleSelection
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className)
			throws Exception {
		String hql = "select o from QualificationMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		return (List<QualificationMultipleSelection>) super.getList(hql, parameters);
	}

	/**
	 * Find QualificationMultipleSelection by target key and class name
	 * 
	 * @author TechFinium
	 * @param targetKey
	 * @param className
	 * @param ConfigDocProcessEnum
	 * @see QualificationMultipleSelection
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className,
			ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from QualificationMultipleSelection o where o.targetClass = :className and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("className", className.trim());
		parameters.put("targetKey", targetKey);
		parameters.put("process", process);
		return (List<QualificationMultipleSelection>) super.getList(hql, parameters);
	}
}
