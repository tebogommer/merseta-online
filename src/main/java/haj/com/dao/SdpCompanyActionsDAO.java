package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.enums.ApprovalEnum;

public class SdpCompanyActionsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SdpCompanyActions
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 * @return a list of {@link haj.com.entity.SdpCompanyActions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> allSdpCompanyActions() throws Exception {
		return (List<SdpCompanyActions>)super.getList("select o from SdpCompanyActions o");
	}

	/**
	 * Get all SdpCompanyActions between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SdpCompanyActions
 	 * @return a list of {@link haj.com.entity.SdpCompanyActions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> allSdpCompanyActions(Long from, int noRows) throws Exception {
	 	String hql = "select o from SdpCompanyActions o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SdpCompanyActions>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SdpCompanyActions
 	 * @return a {@link haj.com.entity.SdpCompanyActions}
 	 * @throws Exception global exception
 	 */
	public SdpCompanyActions findByKey(Long id) throws Exception {
	 	String hql = "select o from SdpCompanyActions o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SdpCompanyActions)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SdpCompanyActions by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SdpCompanyActions
  	 * @return a list of {@link haj.com.entity.SdpCompanyActions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> findByName(String description) throws Exception {
	 	String hql = "select o from SdpCompanyActions o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SdpCompanyActions>)super.getList(hql, parameters);
	}
	
	public Integer countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(Long newDesId, Long companyId, Long siteId, List<ApprovalEnum> approvalList) throws Exception {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parameters = new Hashtable<String, Object>();
		hql.append("select count(o) from SdpCompanyActions o where o.newDesignation.id = :newDesId ");
	    parameters.put("newDesId", newDesId);
	    if (companyId != null) {
	    	hql.append("and o.company.id = :companyId ");
		    parameters.put("companyId", companyId);
		}
	    if (siteId != null) {
	    	hql.append("and o.trainingSite.id = :siteId ");
		    parameters.put("siteId", siteId);
		}
	    if (approvalList != null && !approvalList.isEmpty()) {
	    	int counter = 1;
	    	hql.append("and o.approvalStatus in ( ");
	    	for (ApprovalEnum approvalEnum : approvalList) {
	    		hql.append(":approvalEnum"+counter);
			    parameters.put("approvalEnum"+counter, approvalEnum);
	    		if (counter == approvalList.size()) {
	    			hql.append(" ) ");
				} else {
					hql.append(" , ");
				}
	    		counter++;
			}
		}
		return ((Long)super.getUniqueResult(hql.toString(), parameters)).intValue();
	}
	
	public Integer countByUserIdCompanyTrainingSiteAndOpenStatus(Long userId, Long companyId, Long siteId, List<ApprovalEnum> approvalList) throws Exception {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parameters = new Hashtable<String, Object>();
		hql.append("select count(o) from SdpCompanyActions o where o.sdpUser.id = :userId ");
	    parameters.put("userId", userId);
	    if (companyId != null) {
	    	hql.append(" and o.company.id = :companyId ");
		    parameters.put("companyId", companyId);
		}
	    if (siteId != null) {
	    	hql.append(" and o.trainingSite.id = :siteId ");
		    parameters.put("siteId", siteId);
		} else {
			hql.append(" and o.trainingSite is null ");
		}
	    if (approvalList != null && !approvalList.isEmpty()) {
	    	int counter = 1;
	    	hql.append(" and o.approvalStatus in ( ");
	    	for (ApprovalEnum approvalEnum : approvalList) {
	    		hql.append(" :approvalEnum"+counter);
			    parameters.put("approvalEnum"+counter, approvalEnum);
	    		if (counter == approvalList.size()) {
	    			hql.append(" ) ");
				} else {
					hql.append(" , ");
				}
	    		counter++;
			}
		}
		return ((Long)super.getUniqueResult(hql.toString(), parameters)).intValue();
	}
}

