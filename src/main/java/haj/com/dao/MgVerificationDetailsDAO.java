package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MgVerificationDetailsDAO extends AbstractDAO  {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + " left join fetch o.wsp mgwsp " + " ";
	
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MgVerificationDetails
 	 * @author TechFinium 
 	 * @see    MgVerificationDetails
 	 * @return a list of {@link haj.com.entity.MgVerificationDetails}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> allMgVerificationDetails() throws Exception {
		return (List<MgVerificationDetails>)super.getList("select o from MgVerificationDetails o "+leftJoins);
	}

	/**
	 * Get all MgVerificationDetails between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MgVerificationDetails
 	 * @return a list of {@link haj.com.entity.MgVerificationDetails}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> allMgVerificationDetails(Long from, int noRows) throws Exception {
	 	String hql = "select o from MgVerificationDetails o "+leftJoins ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MgVerificationDetails>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MgVerificationDetails
 	 * @return a {@link haj.com.entity.MgVerificationDetails}
 	 * @throws Exception global exception
 	 */
	public MgVerificationDetails findByKey(Long id) throws Exception {
	 	String hql = "select o from MgVerificationDetails o "+leftJoins+" where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MgVerificationDetails)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MgVerificationDetails by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MgVerificationDetails
  	 * @return a list of {@link haj.com.entity.MgVerificationDetails}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> findByName(String description) throws Exception {
	 	String hql = "select o from MgVerificationDetails o "+leftJoins+" where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MgVerificationDetails>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by wsp Id
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MgVerificationDetails
 	 * @return a {@link haj.com.entity.MgVerificationDetails}
 	 * @throws Exception global exception
 	 */
	public MgVerificationDetails findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from MgVerificationDetails o "+leftJoins+" where o.wsp.id = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (MgVerificationDetails)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> rejectedMgVerificationDetails(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerificationDetails o where o.wsp is not null and o.status = :status";
		filters.put("status", ApprovalEnum.PendingCommitteeApproval);
		return (List<MgVerificationDetails>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countRejectedSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from MgVerificationDetails o where o.wsp is not null and o.status = :status";
		filters.put("status", ApprovalEnum.PendingCommitteeApproval);
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> MgVerificationDetailsByStatus(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		String hql = "select o from MgVerificationDetails o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
		return (List<MgVerificationDetails>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countByStatus(Class<?> entity, Map<String, Object> filters, ApprovalEnum status) {
		String hql = "select count(o) from MgVerificationDetails o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o where o.wsp is not null";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
						
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o.wsp." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o.wsp." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp is not null";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterSearchStatus(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) {
		String hql = "select o from " + entity.getName() + " o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
						
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o.wsp." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o.wsp." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSearchStatus(Class<?> entity, Map<String, Object> filters, ApprovalEnum status) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> mgVerificationDetailsInfoWsp(Long wspId) {
		String hql = "select o from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<MgVerificationDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> mgVerificationDetailsInfoByWsp(Long wspId) throws Exception{
		String hql = "select o from MgVerificationDetails o where o.wsp.id = :wspId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<MgVerificationDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> mgVerificationDetailsByWspIdAndEvidanceRequired(Long wspId) throws Exception{
		String hql = "select o from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspId and o.evidanceRequired = :evidanceRequired";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
	    parameters.put("evidanceRequired", true);
		return (List<MgVerificationDetails>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> findMgVerificationDetailsByWspIdAndEvidanceRequired(Long wspId) throws Exception{
		String hql = "select o from MgVerificationDetails o where o.wsp.id = :wspId and o.evidanceRequired = :evidanceRequired";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
	    parameters.put("evidanceRequired", true);
		return (List<MgVerificationDetails>)super.getList(hql, parameters);
	}

	public int findCountByWspId(Long wspId) {		
		String hql = "select count(o) from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspId and (o.noLearnersStarted is null or o.noLearnersWithdrawn is null or o.noLearnersCompleted is null or o.deviationReason is null or o.actionPlan is null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countEntriesNotPopulatedByWspId(Long wspId) throws Exception{		
		String hql = "select count(o) from MgVerificationDetails o where o.wsp.id = :wspId and (o.noLearnersStarted is null or o.noLearnersWithdrawn is null or o.noLearnersCompleted is null or o.deviationReason is null or o.actionPlan is null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int findCountByWspIdWhereEvidanceRequired(Long wspId) throws Exception{		
		String hql = "select count(o) from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspId and o.evidanceRequired = :evidanceRequired";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
	    parameters.put("evidanceRequired", true);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countByWspIdWhereEvidanceRequired(Long wspId) throws Exception{		
		String hql = "select count(o) from MgVerificationDetails o where o.wsp.id = :wspId and o.evidanceRequired = :evidanceRequired";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
	    parameters.put("evidanceRequired", true);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}

