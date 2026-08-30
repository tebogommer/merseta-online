package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyLearners;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringMitigation;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkplaceMonitoringMitigationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringMitigation
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigation> allWorkplaceMonitoringMitigation() throws Exception {
		return (List<WorkplaceMonitoringMitigation>) super.getList("select o from WorkplaceMonitoringMitigation o");
	}

	/**
	 * Get all WorkplaceMonitoringMitigation between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WorkplaceMonitoringMitigation
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigation> allWorkplaceMonitoringMitigation(Long from, int noRows) throws Exception {
		String hql = "select o from WorkplaceMonitoringMitigation o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WorkplaceMonitoringMitigation>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WorkplaceMonitoringMitigation
	 * @return a {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception
	 *             global exception
	 */
	public WorkplaceMonitoringMitigation findByKey(Long id) throws Exception {
		String hql = "select o from WorkplaceMonitoringMitigation o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WorkplaceMonitoringMitigation) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringMitigation by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WorkplaceMonitoringMitigation
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigation> findByName(String description) throws Exception {
		String hql = "select o from WorkplaceMonitoringMitigation o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WorkplaceMonitoringMitigation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigation> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WorkplaceMonitoring workplaceMonitoring) {
		String hql = "select o from WorkplaceMonitoringMitigation o where o.workplaceMonitoring.id = :workplaceMonitoringID";
		filters.put("workplaceMonitoringID", workplaceMonitoring.getId());
		return (List<WorkplaceMonitoringMitigation>) sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Map<String, Object> filters, WorkplaceMonitoring workplaceMonitoring) {
		String hql = "select count(o) from WorkplaceMonitoringMitigation o where o.workplaceMonitoring.id = :workplaceMonitoringID";
		filters.put("workplaceMonitoringID", workplaceMonitoring.getId());
		return countWhere(filters, hql);
	}
}
