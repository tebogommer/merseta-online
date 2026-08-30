package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringPurposeOfSiteVisit;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class PurposeOfSiteVisitDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PurposeOfSiteVisit
	 * 
	 * @author TechFinium
	 * @see PurposeOfSiteVisit
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisit() throws Exception {
		return (List<PurposeOfSiteVisit>) super.getList("select o from PurposeOfSiteVisit o where o.purposeOfSiteVisitParent is null");
	}

	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisitByParent(PurposeOfSiteVisit purposeOfSiteVisit) throws Exception {
		String hql = "select o from PurposeOfSiteVisit o where o.purposeOfSiteVisitParent.id = :purposeOfSiteVisitID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("purposeOfSiteVisitID", purposeOfSiteVisit.getId());
		return (List<PurposeOfSiteVisit>) super.getList(hql, parameters);
	}
	


	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisitByParent(PurposeOfSiteVisit purposeOfSiteVisit, String instatement) throws Exception {
		String hql = "select o from PurposeOfSiteVisit o where o.purposeOfSiteVisitParent.id = :purposeOfSiteVisitID and o.id not in " + instatement;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("purposeOfSiteVisitID", purposeOfSiteVisit.getId());
		return (List<PurposeOfSiteVisit>) super.getList(hql, parameters);
	}

	/**
	 * Get all PurposeOfSiteVisit between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see PurposeOfSiteVisit
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisit(Long from, int noRows) throws Exception {
		String hql = "select o from PurposeOfSiteVisit o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<PurposeOfSiteVisit>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see PurposeOfSiteVisit
	 * @return a {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	public PurposeOfSiteVisit findByKey(Long id) throws Exception {
		String hql = "select o from PurposeOfSiteVisit o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (PurposeOfSiteVisit) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PurposeOfSiteVisit by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see PurposeOfSiteVisit
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> findByName(String description) throws Exception {
		String hql = "select o from PurposeOfSiteVisit o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<PurposeOfSiteVisit>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> findByWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring) throws Exception {
		String hql = "select o.purposeOfSiteVisit from WorkplaceMonitoringPurposeOfSiteVisit o where o.workplaceMonitoring.id = :workplaceMonitoringID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workplaceMonitoringID", workplaceMonitoring.getId());
		return (List<PurposeOfSiteVisit>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringPurposeOfSiteVisit> findWorkplaceMonitoringPurposeOfSiteVisit(WorkplaceMonitoring workplaceMonitoring) throws Exception {
		String hql = "select o from WorkplaceMonitoringPurposeOfSiteVisit o where o.workplaceMonitoring.id = :workplaceMonitoringID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workplaceMonitoringID", workplaceMonitoring.getId());
		return (List<WorkplaceMonitoringPurposeOfSiteVisit>) super.getList(hql, parameters);
	}
}
