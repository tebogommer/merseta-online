package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WorkplaceMonitoring;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkplaceMonitoringDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoring
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoring> allWorkplaceMonitoring() throws Exception {
		return (List<WorkplaceMonitoring>) super.getList("select o from WorkplaceMonitoring o");
	}

	/**
	 * Get all WorkplaceMonitoring between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WorkplaceMonitoring
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoring> allWorkplaceMonitoring(Long from, int noRows) throws Exception {
		String hql = "select o from WorkplaceMonitoring o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WorkplaceMonitoring>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WorkplaceMonitoring
	 * @return a {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             global exception
	 */
	public WorkplaceMonitoring findByKey(Long id) throws Exception {
		String hql = "select o from WorkplaceMonitoring o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WorkplaceMonitoring) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoring by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WorkplaceMonitoring
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoring> findByName(String description) throws Exception {
		String hql = "select o from WorkplaceMonitoring o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WorkplaceMonitoring>) super.getList(hql, parameters);
	}

	public Long findByCompany(Long companyID) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoring o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (Long) super.getUniqueResult(hql, parameters);
	}
}
