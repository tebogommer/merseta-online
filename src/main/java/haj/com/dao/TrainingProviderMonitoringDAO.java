package haj.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.utils.GenericUtility;

public class TrainingProviderMonitoringDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderMonitoring
 	 * @author TechFinium 
 	 * @see    TrainingProviderMonitoring
 	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allTrainingProviderMonitoring() throws Exception {
		return (List<TrainingProviderMonitoring>)super.getList("select o from TrainingProviderMonitoring o");
	}

	/**
	 * Get all TrainingProviderMonitoring between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingProviderMonitoring
 	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allTrainingProviderMonitoring(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingProviderMonitoring o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingProviderMonitoring>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingProviderMonitoring
 	 * @return a {@link haj.com.entity.TrainingProviderMonitoring}
 	 * @throws Exception global exception
 	 */
	public TrainingProviderMonitoring findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingProviderMonitoring o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingProviderMonitoring)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingProviderMonitoring by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingProviderMonitoring
  	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingProviderMonitoring o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingProviderMonitoring>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public TrainingProviderMonitoring findMonitoringDateByCompany(Company company) throws Exception {
		List<TrainingProviderMonitoring> l = new ArrayList<>();
	 	String hql = "select tpmt  from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id " 
	 			+ 	" inner join TrainingProviderApplication tpat on ot.id = tpat.company.id"
	 			+ " where tpat.company.id = :company  order by tpmt.monitoringDate asc" ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("company", company.getId());
		l = (List<TrainingProviderMonitoring>) super.getList(hql, parameters);
		if (l != null && l.size() > 0 ) return l.get(0);
		else return null;
	}
	
	public int countOpenMonitoringReport(Long companyId, Boolean audit) throws Exception {
	 	String hql = "select count(o) from TrainingProviderMonitoring o where o.company.id = :companyId and o.audit = :audit "
	 			+ "and (o.status <> :appStatus and o.status <> :rejStatus and o.status <> :wdnStatus)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("audit", audit);
	    parameters.put("appStatus", ApprovalEnum.Approved);
	    parameters.put("rejStatus", ApprovalEnum.Rejected);
	    parameters.put("wdnStatus", ApprovalEnum.Withdrawn);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}

