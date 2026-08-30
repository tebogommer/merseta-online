package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoringDgMonitoring;

public class WorkplaceMonitoringDgMonitoringDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringDgMonitoring
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDgMonitoring> allWorkplaceMonitoringDgMonitoring() throws Exception {
		return (List<WorkplaceMonitoringDgMonitoring>)super.getList("select o from WorkplaceMonitoringDgMonitoring o");
	}

	/**
	 * Get all WorkplaceMonitoringDgMonitoring between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDgMonitoring> allWorkplaceMonitoringDgMonitoring(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDgMonitoring o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkplaceMonitoringDgMonitoring>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringDgMonitoring findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDgMonitoring o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringDgMonitoring)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringDgMonitoring by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringDgMonitoring
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDgMonitoring> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDgMonitoring o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringDgMonitoring>)super.getList(hql, parameters);
	}
}

