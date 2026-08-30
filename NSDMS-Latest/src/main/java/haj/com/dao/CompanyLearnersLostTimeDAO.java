package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CompanyLearnersLostTimeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersLostTime
 	 * @author TechFinium 
 	 * @see    CompanyLearnersLostTime
 	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersLostTime> allCompanyLearnersLostTime() throws Exception {
		return (List<CompanyLearnersLostTime>)super.getList("select o from CompanyLearnersLostTime o");
	}

	/**
	 * Get all CompanyLearnersLostTime between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersLostTime
 	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersLostTime> allCompanyLearnersLostTime(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersLostTime o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersLostTime>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersLostTime
 	 * @return a {@link haj.com.entity.CompanyLearnersLostTime}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersLostTime findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersLostTime o left join fetch o.companyLearners cl left join fetch cl.company where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersLostTime)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersLostTime by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersLostTime
  	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersLostTime> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersLostTime o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersLostTime>)super.getList(hql, parameters);
	}
		
	public int countOpenLostTimeByCompanyLearnersId(Long companyLearnerId) throws Exception {
	 	String hql = "select count(o) from CompanyLearnersLostTime o where o.companyLearners.id = :companyLearnerId " + "and o.status <> :approvedStatus and o.status <> :rejectedStatus" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyLearnerId", companyLearnerId);
	    parameters.put("approvedStatus", ApprovalEnum.Approved);
	    parameters.put("rejectedStatus", ApprovalEnum.Rejected);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countLostTimeByCompanyLearnerTypeAndStatus(Long companyLearnerId, LostTimeReason lostTimeReason, ApprovalEnum status) throws Exception {
	 	String hql = "select count(o) from CompanyLearnersLostTime o where o.companyLearners.id = :companyLearnerId " + "and o.status =:status and o.lostTimeReason = :lostTimeReason" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyLearnerId", companyLearnerId);
	    parameters.put("lostTimeReason", lostTimeReason);
	    parameters.put("status", status);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}