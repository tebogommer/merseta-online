package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.ApprovalEnum;

public class WorkplaceMonitoringSiteVisitDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringSiteVisit
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisit() throws Exception {
		return (List<WorkplaceMonitoringSiteVisit>)super.getList("select o from WorkplaceMonitoringSiteVisit o");
	}

	/**
	 * Get all WorkplaceMonitoringSiteVisit between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringSiteVisit
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisit(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringSiteVisit o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<WorkplaceMonitoringSiteVisit>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringSiteVisit
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringSiteVisit findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringSiteVisit o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringSiteVisit)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringSiteVisit by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringSiteVisit
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringSiteVisit o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringSiteVisit>)super.getList(hql, parameters);
	}
	
	public Integer countSiteVisitByCompanyId(Long companyId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("companyId", companyId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countOpenSiteVisitsByComapnyId(Long companyId, List<ApprovalEnum> statusList) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("companyId", companyId);
	    if (!statusList.isEmpty()) {
	    	hql += " and o.status in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		parameters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}